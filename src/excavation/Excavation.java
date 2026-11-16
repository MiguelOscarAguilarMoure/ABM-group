package excavation;

import helpers.InputHelper;
import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.Collections; // CAMBIO 1: para mezclar la capa de terreno
import java.util.HashMap;
import java.util.List;
import java.util.Map; // !CAMBIO 1: proporciones de terreno de la región
import java.util.Random;
import logs.Registro; // !CAMBIO 1: registro de errores
import people.Archeologist;
import regions.IRegionDificil; // CAMBIO 1: saber si la región tiene terrenos difíciles
import regions.Region;
import treasures.ETreasureRarity;
import treasures.Treasure;

/**
 * Representa una excavación dentro del juego.
 * <p>
 * La clase genera el tablero de trabajo, distribuye los tesoros ocultos y
 * controla el estado general de la partida durante la exploración. También
 * gestiona la información visible para el jugador, el número de acciones
 * disponibles, los tesoros restantes y la puntuación acumulada.
 * <p>
 * Cada excavación puede tratarse como una instancia
 * independiente con sus propias dimensiones, región, arqueólogo y progreso.
 * <p>
 * !CAMBIO 15: ahora cada casilla tiene además un terreno (tierra, piedra, arena o
 * agua) que decide cuántas acciones cuesta excavarla y si hay que excavarla más de
 * una vez (la piedra pasa a tierra).
 *
 * @author Miguel Aguilar
 * @author Borja Abal
 * @version 1.0
 * @since 2026-10-18
 */
public class Excavation {

    private static final char[] LETRAS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final Random RANDOM = new Random();
    // ! Elementos esteticos
    private static final String ADORNO = "::::::::::::::::::::";
    private static final String SEPARADOR_TURNO = "----------------------------------------";
    private static final String SEPARADOR_FIN = "::::::::::::::::::::::::::::::::::::::::";

    // !CAMBIO 2: eliminada la constante SIN_EXCAVAR ("·"). Una casilla sin excavar
    // se ve ahora con el símbolo de su terreno ('.', '^', '~' o '-').
    private static final String VACIO = "x";
    private static final String SUPOSICION = "?";

    // Valores de ETerrainSize.getTamanho()
    private static final String PEQUENO = "Pequeño";
    private static final String MEDIANO = "Mediano";
    private static final String GRANDE = "Grande";

    private static final double ACCIONES_MIN = 0.25; // 25 % de la rejilla
    private static final double ACCIONES_MAX = 0.75; // 75 % de la rejilla
    private static final double REVELACION_MAX = 0.25; // 25 % de la rejilla como mucho

    private final ETerrainSize terreno;
    private final Region region;
    private final Archeologist arqueologo;
    private final int numColumnas;
    private final int numFilas;

    private final ArrayList<String> columnas = new ArrayList<>(); // A, B... AA, AB...
    private final ArrayList<String> casillas = new ArrayList<>(); // lo que ve el jugador
    // !CAMBIO 3: capa de terreno, una por casilla y aparte de lo que ve el jugador
    private final ArrayList<ETerreno> terrenos = new ArrayList<>();
    private final HashMap<Integer, Treasure> tesoros = new HashMap<>(); // casilla -> tesoro enterrado
    private final ArrayList<String> codigosEncontrados = new ArrayList<>();

    // !CAMBIO 3: true si la casilla ya está resuelta. Ya no se puede deducir del
    // símbolo, porque una casilla sin excavar muestra su terreno y una piedra rota
    // muestra tierra pero sigue pendiente.
    private boolean[] excavadas;
    private String cabecera;
    private int anchoEtiqueta;
    private int anchoNumFila;
    private int acciones;
    private int tesorosRestantes;
    private int cavarRestantes;
    private int puntos;

    /**
     * Crea una excavación para el terreno, la región y el arqueólogo indicados.
     * Inicializa la rejilla, coloca los tesoros y calcula las acciones y
     * revelaciones iniciales.
     *
     * @param terreno    configuración y dimensiones del terreno.
     * @param region     región cuyos tesoros pueden aparecer en la excavación.
     * @param arqueologo arqueólogo que realizará la excavación.
     * @throws IllegalArgumentException si algún parámetro es null o el arqueólogo
     *                                  no puede excavar el terreno.
     */
    public Excavation(ETerrainSize terreno, Region region, Archeologist arqueologo) {
        if (terreno == null || region == null || arqueologo == null) {
            throw new IllegalArgumentException("El terreno, la región y el arqueólogo no pueden ser nulos.");
        }
        if (!arqueologo.puedeExcavar(terreno)) {
            throw new IllegalArgumentException(arqueologo.getNombre() + " no puede excavar el terreno "
                    + terreno.getTamanho() + " (" + terreno.getNombre() + ").");
        }
        this.terreno = terreno;
        this.region = region;
        this.arqueologo = arqueologo;
        // En ETerrainSize "AxB" es ancho x alto: A = columnas (letras), B = filas
        // (números).
        this.numColumnas = terreno.getFil();
        this.numFilas = terreno.getCol();

        generarRejilla();
        generarTerrenos(); // CAMBIO 4: rellena la capa de terreno antes de colocar tesoros
        generarTesoros();
        this.acciones = calcularAcciones();
        aplicarRevelacion();

    }

    // ===== Generación =====================================================

    /**
     * Inicializa las casillas de la rejilla y prepara las etiquetas de filas y
     * columnas que se mostrarán al jugador.
     */
    private void generarRejilla() {
        // dimensiond de la rejilla
        int total = numColumnas * numFilas;
        // CAMBIO 5: las casillas ya no se rellenan aquí con "·": lo hace
        // generarTerrenos() con el símbolo de cada terreno.
        excavadas = new boolean[total];
        cavarRestantes = total;

        // Letras por etiqueta: 1 si caben en el abecedario, 2 si no...
        anchoEtiqueta = 1;
        int capacidad = LETRAS.length;
        while (capacidad < numColumnas) {
            anchoEtiqueta++;
            capacidad *= LETRAS.length;
        }
        generarEtiquetas("");
        anchoNumFila = String.valueOf(numFilas).length();
        // String builder en vez de concatenar strings (mas eficiente)
        StringBuilder sb = new StringBuilder(espacios(anchoNumFila));
        for (String etiqueta : columnas) {
            sb.append(' ').append(etiqueta);
        }
        cabecera = sb.toString();
    }

    /**
     * !CAMBIO 6 (método nuevo). Rellena la capa de terreno y pone en cada casilla el
     * símbolo de su terreno, que es lo que ve el jugador mientras no la excava.
     * <p>
     * Las regiones difíciles reparten los terrenos según sus proporciones; las
     * básicas (o una región con proporciones no válidas) son todo tierra.
     * </p>
     */
    private void generarTerrenos() {
        int total = numColumnas * numFilas;
        List<ETerreno> capa = new ArrayList<>();
        if (region instanceof IRegionDificil) {
            capa = repartirTerrenos(((IRegionDificil) region).getProporciones(), total);
        }
        if (capa.size() != total) {
            capa = new ArrayList<>();
            for (int i = 0; i < total; i++) {
                capa.add(ETerreno.TIERRA);
            }
        }
        Collections.shuffle(capa, RANDOM);

        for (ETerreno tipo : capa) { // "tipo" y no "terreno", que ya es un atributo
            terrenos.add(tipo);
            casillas.add(tipo.getSimbolo());
        }
    }

    /**
     * !CAMBIO 6 (método nuevo). Calcula cuántas casillas de cada terreno hay según
     * los porcentajes y las devuelve en una lista sin mezclar.
     * <p>
     * Cada terreno recibe la parte entera de su porcentaje y las casillas que sobran
     * por el redondeo se dan, una a una, a los terrenos a los que más se les ha
     * recortado. Así la suma siempre es el total de casillas.
     * </p>
     *
     * @param proporciones porcentaje de cada terreno (si no suman 100 se reparte en proporción).
     * @param total        número de casillas de la rejilla.
     * @return lista con {@code total} terrenos, o vacía si las proporciones no son válidas.
     */
    private List<ETerreno> repartirTerrenos(Map<ETerreno, Integer> proporciones, int total) {
        List<ETerreno> capa = new ArrayList<>();
        int suma = sumarProporciones(proporciones);
        if (suma > 0) {
            ETerreno[] tipos = ETerreno.values();
            int[] cantidades = new int[tipos.length];
            int[] sobrante = new int[tipos.length]; // lo recortado al redondear; -1 si el terreno no está
            int asignadas = 0;
            for (int i = 0; i < tipos.length; i++) {
                int porcentaje = getPorcentaje(proporciones, tipos[i]);
                if (porcentaje > 0) {
                    cantidades[i] = total * porcentaje / suma;
                    sobrante[i] = total * porcentaje % suma;
                    asignadas += cantidades[i];
                } else {
                    sobrante[i] = -1;
                }
            }
            while (asignadas < total) {
                int mejor = indiceMayor(sobrante);
                cantidades[mejor]++;
                sobrante[mejor] = -1; // cada terreno recibe como mucho una casilla extra
                asignadas++;
            }
            for (int i = 0; i < tipos.length; i++) {
                for (int j = 0; j < cantidades[i]; j++) {
                    capa.add(tipos[i]);
                }
            }
        }
        return capa;
    }

    /**
     * !CAMBIO 6 (método nuevo). Porcentaje de un terreno, tratando como 0 lo que falta,
     * es null o es negativo.
     *
     * @param proporciones porcentajes de la región (no puede ser null).
     * @param tipo         terreno que se consulta.
     * @return el porcentaje, 0 o más.
     */
    private int getPorcentaje(Map<ETerreno, Integer> proporciones, ETerreno tipo) {
        Integer porcentaje = proporciones.get(tipo);
        return (porcentaje == null || porcentaje < 0) ? 0 : porcentaje;
    }

    /**
     *! CAMBIO 6 (método nuevo). Suma los porcentajes válidos de las proporciones.
     *
     * @param proporciones porcentajes de la región (puede ser null).
     * @return la suma, o 0 si no hay proporciones válidas.
     */
    private int sumarProporciones(Map<ETerreno, Integer> proporciones) {
        int suma = 0;
        if (proporciones != null) {
            for (ETerreno tipo : ETerreno.values()) {
                suma += getPorcentaje(proporciones, tipo);
            }
        }
        return suma;
    }

    /**
     * CAMBIO 6 (método nuevo). Posición del mayor valor de un array (la primera si hay empate).
     *
     * @param valores array no vacío.
     * @return índice del mayor valor.
     */
    private int indiceMayor(int[] valores) {
        int mejor = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > valores[mejor]) {
                mejor = i;
            }
        }
        return mejor;
    }

    /**
     * Genera las etiquetas de las columnas hasta alcanzar la cantidad necesaria.
     *
     * @param actual prefijo de la etiqueta que se está formando.
     */
    private void generarEtiquetas(String actual) {
        if (actual.length() == anchoEtiqueta) {
            columnas.add(actual);
            return;
        }
        for (int i = 0; i < LETRAS.length && columnas.size() < numColumnas; i++) {
            generarEtiquetas(actual + LETRAS[i]);
        }
    }

    /**
     * Determina las rarezas de tesoros que habrá en la excavación, selecciona los
     * tesoros
     * correspondientes y los coloca en posiciones aleatorias.
     *
     *  @throws IllegalStateException si el tamaño del terreno no está reconocido.
     */
    private void generarTesoros() {
        int total = numColumnas * numFilas;
        int cantidad = Math.max(1, (int) Math.ceil(total / 10.0)); // 10 %, redondeando hacia arriba

        // Primero los mínimos del terreno (están incluidos en la cantidad total)
        ArrayList<ETreasureRarity> rarezas = new ArrayList<>();
        switch (terreno.getTamanho()) {
            case PEQUENO:
                anadir(rarezas, ETreasureRarity.COMMON, 1);
                break;
            case MEDIANO:
                anadir(rarezas, ETreasureRarity.COMMON, 3);
                anadir(rarezas, ETreasureRarity.UNCOMMON, 1);
                break;
            case GRANDE:
                anadir(rarezas, ETreasureRarity.COMMON, 5);
                anadir(rarezas, ETreasureRarity.UNCOMMON, 3);
                anadir(rarezas, ETreasureRarity.RARE, 1);
                break;
            default:
                throw new IllegalStateException("Tamaño de terreno desconocido: " + terreno.getTamanho());
        }

        // Después, hasta completar la cantidad, con rareza aleatoria
        boolean permiteRaros = !terreno.getTamanho().equals(PEQUENO);
        while (rarezas.size() < cantidad) {
            rarezas.add(tirarRareza(permiteRaros));
        }
        // TODO Extras del arqueólogo (raros, infrecuentes, comunes y aleatorios) sin
        // pasar del 50 % de casillas.
        // Pendiente de que Archeologist ofrezca sus bonos.
        int maximo = total / 2;
        if (permiteRaros) {
            anadirHasta(rarezas, ETreasureRarity.RARE, arqueologo.getTesorosExtra(ETreasureRarity.RARE, region),
                    maximo);
        }
        anadirHasta(rarezas, ETreasureRarity.UNCOMMON, arqueologo.getTesorosExtra(ETreasureRarity.UNCOMMON, region),
                maximo);
        anadirHasta(rarezas, ETreasureRarity.COMMON, arqueologo.getTesorosExtra(ETreasureRarity.COMMON, region),
                maximo);
        for (int i = 0; i < arqueologo.getTesorosExtra(region) && rarezas.size() < maximo; i++) {
            rarezas.add(tirarRareza(permiteRaros));
        }
        // Por último, se colocan en casillas distintas al azar
        List<Integer> posiciones = UtilsHelper.genRandomIdxs(rarezas.size(), total);
        for (int i = 0; i < rarezas.size(); i++) {
            Treasure tesoro = region.getTesoroAleatorioPorRareza(rarezas.get(i));
            // CAMBIO 7: era "if (tesoro == null) continue;". Se invierte la condición:
            // si la región no tiene tesoros de esa rareza, la casilla queda vacía.
            if (tesoro != null) {
                tesoros.put(posiciones.get(i), tesoro);
            }
        }
        tesorosRestantes = tesoros.size();
    }

    /**
     * Añade varias veces una rareza a la lista indicada.
     *
     * @param lista  lista de rarezas que se va a completar.
     * @param rareza rareza que se añadirá.
     * @param veces  número de veces que se añadirá.
     */
    private void anadir(ArrayList<ETreasureRarity> lista, ETreasureRarity rareza, int veces) {
        for (int i = 0; i < veces; i++) {
            lista.add(rareza);
        }
    }

    /**
     * Añade una rareza a la lista sin superar el tamaño máximo permitido.
     *
     * @param lista  lista de rarezas que se va a completar.
     * @param rareza rareza que se añadirá.
     * @param veces  número máximo de veces que se intentará añadir.
     * @param maximo cantidad máxima de elementos permitida en la lista.
     */
    private void anadirHasta(ArrayList<ETreasureRarity> lista, ETreasureRarity rareza, int veces, int maximo) {
        for (int i = 0; i < veces && lista.size() < maximo; i++) {
            lista.add(rareza);
        }
    }

    /**
     * Selecciona aleatoriamente una rareza teniendo en cuenta las probabilidades
     * base y los modificadores del arqueólogo.
     *
     * @param permiteRaros indica si se pueden seleccionar tesoros raros.
     * @return la rareza seleccionada.
     */
    private ETreasureRarity tirarRareza(boolean permiteRaros) {
        int raro = permiteRaros ? 10 + arqueologo.getProbabilidadExtra(ETreasureRarity.RARE) : 0;
        int infrecuente = 30 + arqueologo.getProbabilidadExtra(ETreasureRarity.UNCOMMON);

        int tirada = RANDOM.nextInt(100);
        if (tirada < raro) {
            return ETreasureRarity.RARE;
        }
        if (tirada < raro + infrecuente) {
            return ETreasureRarity.UNCOMMON;
        }
        return ETreasureRarity.COMMON;
    }

    /**
     * Calcula las acciones disponibles según el tamaño de la rejilla y las
     * bonificaciones del arqueólogo. El resultado queda limitado entre una acción
     * y el 75 % de las casillas.
     *
     * @return número de acciones disponibles al comenzar la excavación.
     */
    private int calcularAcciones() {
        int total = numColumnas * numFilas;
        int extra = arqueologo.getAccionesExtra(region); // habilidades + especialización
        double factor = arqueologo.getFactorAcciones(); // porcentajes multiplicados entre sí
        int calculadas = (int) Math.round((total * ACCIONES_MIN + extra) * factor);
        int maximo = (int) (total * ACCIONES_MAX);
        return Math.max(1, Math.min(calculadas, maximo));
    }

    /**
     * Aplica las habilidades iniciales del arqueólogo sin consumir acciones.
     * ! CAMBIO 8: primero cambia terrenos (demoliciones); después limpia casillas
     * vacías y por último marca posibles posiciones de tesoros, sin superar el
     * límite establecido para la rejilla.
     */
    private void aplicarRevelacion() {
        cambiarTerrenos(); // CAMBIO 8: antes de la limpieza y del escaneo
        int total = numColumnas * numFilas;
        int maximo = (int) (total * REVELACION_MAX);
        int reveladas = limpiar(arqueologo.getLimpieza(), maximo);
        suponer(arqueologo.getSuposicion() / 3, maximo - reveladas);
    }

    /**
     * ! CAMBIO 8 (método nuevo). Pide al arqueólogo cuántas casillas de cada terreno
     * cambia por tierra (por ejemplo, las demoliciones rompen piedras) y las cambia.
     * No gasta acciones y se hace antes de limpiar y escanear.
     */
    private void cambiarTerrenos() {
        for (ETerreno tipo : ETerreno.values()) {
            // TODO getCasillasCambiadas no declarado
            int cantidad = arqueologo.getCasillasCambiadas(tipo);
            if (tipo != ETerreno.TIERRA && cantidad > 0) {
                cambiarATierra(tipo, cantidad);
            }
        }
    }

    /**
     * ! CAMBIO 8 (método nuevo). Cambia por tierra, al azar, hasta {@code cantidad}
     * casillas de un terreno. Si hay menos casillas de ese terreno, se cambian todas.
     *
     * @param tipo     terreno que se cambia.
     * @param cantidad número máximo de casillas a cambiar.
     */
    private void cambiarATierra(ETerreno tipo, int cantidad) {
        List<Integer> candidatas = new ArrayList<>();
        for (int i = 0; i < terrenos.size(); i++) {
            if (terrenos.get(i) == tipo) {
                candidatas.add(i);
            }
        }
        Collections.shuffle(candidatas, RANDOM);

        int cambiar = Math.min(cantidad, candidatas.size());
        for (int i = 0; i < cambiar; i++) {
            int indice = candidatas.get(i);
            terrenos.set(indice, ETerreno.TIERRA);
            casillas.set(indice, ETerreno.TIERRA.getSimbolo());
        }
    }

    /**
     * Revela al azar casillas vacías, representándolas con el símbolo
     * correspondiente.
     *
     * @param cantidad número de casillas vacías que se desea revelar.
     * @param maximo   número máximo de casillas que se permite revelar.
     * @return cantidad de casillas que se han revelado.
     */
    private int limpiar(int cantidad, int maximo) {
        List<Integer> vacias = new ArrayList<>();
        for (int i = 0; i < casillas.size(); i++) {
            if (!tesoros.containsKey(i)) {
                vacias.add(i);
            }
        }
        int destapadas = 0;
        while (destapadas < cantidad && destapadas < maximo && !vacias.isEmpty()) {
            int indice = vacias.remove(RANDOM.nextInt(vacias.size()));
            casillas.set(indice, VACIO);
            excavadas[indice] = true; // CAMBIO 9: la casilla limpiada queda resuelta
            cavarRestantes--;
            destapadas++;
        }
        return destapadas;
    }

    /**
     * Marca posibles ubicaciones de tesoros y casillas vacías mediante el símbolo
     * de suposición.
     *
     * @param escaneos número de escaneos que se realizarán.
     * @param maximo   número máximo de casillas que se podrán marcar.
     */
    private void suponer(int escaneos, int maximo) {
        List<Integer> conTesoro = new ArrayList<>(tesoros.keySet());
        List<Integer> sinTesoro = new ArrayList<>();
        for (int i = 0; i < casillas.size(); i++) {
            if (!tesoros.containsKey(i)) {
                sinTesoro.add(i);
            }
        }

        int marcadas = 0;
        for (int e = 0; e < escaneos && marcadas < maximo && !conTesoro.isEmpty(); e++) {
            // Una casilla con tesoro
            int indice = conTesoro.remove(RANDOM.nextInt(conTesoro.size()));
            casillas.set(indice, SUPOSICION);
            marcadas++;

            // Dos casillas sin tesoro (si ya estaban destapadas, se quedan como 'x')
            for (int j = 0; j < 2 && marcadas < maximo && !sinTesoro.isEmpty(); j++) {
                indice = sinTesoro.remove(RANDOM.nextInt(sinTesoro.size()));
                if (!excavadas[indice]) { // CAMBIO 10: antes comparaba el símbolo con SIN_EXCAVAR
                    casillas.set(indice, SUPOSICION);
                    marcadas++;
                }
            }
        }
    }

    /**
     * Comprueba si una casilla todavía puede excavarse.
     *
     *! CAMBIO 11: ahora consulta {@code excavadas}. Una casilla con terreno de varios
     * niveles (la piedra) sigue sin excavar hasta llegar al último nivel.
     *
     * @param indice índice de la casilla en la rejilla.
     * @return true si no se ha resuelto todavía (aunque esté marcada como suposición).
     */
    private boolean sinExcavar(int indice) {
        return !excavadas[indice];
    }

    // !===== Excavar ========================================================

    /**
     * Excava la posición indicada. CAMBIO 12: consume las acciones que cueste su
     * terreno (se las pide al arqueólogo, que tiene en cuenta sus habilidades). Si
     * el terreno tiene un siguiente nivel (la piedra pasa a tierra), la casilla
     * cambia de terreno y hay que volver a excavarla para ver qué hay debajo.
     * Si encuentra un tesoro, actualiza los tesoros restantes, la puntuación y los
     * códigos encontrados.
     * <p>
     * Si quedan menos acciones que el coste, se puede excavar igualmente y las
     * acciones se quedan en 0, así una excavación nunca se queda bloqueada.
     * </p>
     *
     * @param posicion posición que se desea excavar.
     * @return true si la casilla contenía un tesoro; false si estaba vacía o solo
     *         se ha roto un nivel del terreno.
     * @throws IllegalArgumentException si la posición es null, no pertenece a la
     *                                  rejilla o ya se había excavado.
     * @throws IllegalStateException    si la excavación ya ha finalizado.
     */
    public boolean excavar(Position posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        if (isFinalizada()) {
            throw new IllegalStateException("La excavación ya ha terminado.");
        }
        int indice = getIndice(posicion);
        if (!sinExcavar(indice)) {
            throw new IllegalArgumentException("ya está excavada.");
        }

        // CAMBIO 12: antes era "acciones--;"
        ETerreno tipo = terrenos.get(indice); // "tipo" y no "terreno", que ya es un atributo
        // TODO getCosteExcar no delcarado
        acciones = Math.max(0, acciones - arqueologo.getCosteExcavar(tipo));

        if (tipo.tieneSiguiente()) {
            bajarNivel(indice, tipo.getSiguiente());
            return false; // todavía no se ve lo que hay debajo
        }

        excavadas[indice] = true;
        cavarRestantes--;
        casillas.set(indice, getSimbolo(indice)); // se intercambia por lo que hay enterrado

        Treasure tesoro = tesoros.get(indice);
        if (tesoro == null) {
            return false;
        }
        tesorosRestantes--;
        puntos += tesoro.getPuntos();
        codigosEncontrados.add(tesoro.getCodigoConParte());
        return true;
    }

    /**
     * CAMBIO 12 (método nuevo). Pasa una casilla al siguiente nivel de su terreno.
     * Si estaba marcada con suposición ('?') la marca se mantiene, porque el tesoro
     * sigue debajo.
     *
     * @param indice    índice de la casilla en la rejilla.
     * @param siguiente terreno al que pasa la casilla.
     */
    private void bajarNivel(int indice, ETerreno siguiente) {
        terrenos.set(indice, siguiente);
        if (!casillas.get(indice).equals(SUPOSICION)) {
            casillas.set(indice, siguiente.getSimbolo());
        }
    }

    /**
     * Convierte una posición de fila y columna en el índice correspondiente de
     * la rejilla.
     *
     * @param posicion posición que se desea convertir.
     * @return índice de la casilla dentro de las colecciones internas.
     * @throws IllegalArgumentException si la fila o la columna no existen.
     */
    private int getIndice(Position posicion) {
        int fila = posicion.getFil();
        int columna = columnas.indexOf(posicion.getCol());
        if (fila < 1 || fila > numFilas) {
            throw new IllegalArgumentException("La fila " + fila + " no existe (1-" + numFilas + ").");
        }
        if (columna < 0) {
            throw new IllegalArgumentException("La columna " + posicion.getCol() + " no existe ("
                    + columnas.get(0) + "-" + columnas.get(numColumnas - 1) + ").");
        }
        return (fila - 1) * numColumnas + columna;
    }

    /**
     * Obtiene el símbolo que representa el contenido oculto de una casilla.
     *
     * @param indice índice de la casilla en la rejilla.
     * @return código de rareza del tesoro o el símbolo de casilla vacía.
     */
    private String getSimbolo(int indice) {
        Treasure tesoro = tesoros.get(indice);
        return (tesoro == null) ? VACIO : tesoro.getRarezaTesoro().getCode();
    }

    // ===== Estado =========================================================

    /**
     * Comprueba si la excavación ha terminado por falta de acciones o porque ya
     * no quedan tesoros por encontrar.
     *
     * @return true si se cumple alguna de las condiciones de finalización.
     */
    public boolean isFinalizada() {
        return acciones <= 0 || tesorosRestantes <= 0;
    }

    /**
     * CAMBIO 13 (método nuevo). Devuelve el terreno actual de una casilla.
     *
     * @param posicion posición de la casilla.
     * @return el terreno que tiene ahora la casilla (cambia al romper un nivel).
     * @throws IllegalArgumentException si la posición es null o no pertenece a la rejilla.
     */
    public ETerreno getTerreno(Position posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        return terrenos.get(getIndice(posicion));
    }

    /**
     * CAMBIO 13 (método nuevo). Cuenta las casillas que tienen ahora un terreno.
     *
     * @param buscado terreno que se desea contar.
     * @return número de casillas con ese terreno.
     */
    public int contarTerreno(ETerreno buscado) {
        int cantidad = 0;
        for (ETerreno t : terrenos) {
            if (t == buscado) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve el número de acciones que todavía se pueden realizar.
     *
     * @return acciones restantes.
     */
    public int getAccionesRestantes() {
        return acciones;
    }

    /**
     * Devuelve el número total de tesoros que aún no se han encontrado.
     *
     * @return tesoros restantes.
     */
    public int getTesorosRestantes() {
        return tesorosRestantes;
    }

    /**
     * Cuenta los tesoros todavía sin excavar de la rareza indicada.
     *
     * @param rareza rareza que se desea consultar.
     * @return cantidad de tesoros restantes de esa rareza.
     */
    public int getTesorosRestantes(ETreasureRarity rareza) {
        int cantidad = 0;
        for (Integer indice : tesoros.keySet()) {
            Treasure tesoro = tesoros.get(indice);
            if (tesoro.getRarezaTesoro() == rareza && sinExcavar(indice)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Cuenta los tesoros de la rareza indicada que se colocaron al inicio.
     *
     * @param rareza rareza que se desea consultar.
     * @return cantidad total de tesoros de esa rareza en la rejilla.
     */
    public int getTotalTesoros(ETreasureRarity rareza) {
        int cantidad = 0;
        for (Treasure tesoro : tesoros.values()) {
            if (tesoro.getRarezaTesoro() == rareza) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve cuántas casillas quedan por excavar.
     *
     * @return número de posiciones sin excavar.
     */
    public int getPosicionesRestantes() {
        return cavarRestantes;
    }

    /**
     * Devuelve los puntos obtenidos al encontrar tesoros.
     *
     * @return puntuación acumulada durante la excavación.
     */
    public int getPuntosObtenidos() {
        return puntos;
    }

    /**
     * Calcula la experiencia obtenida sumando los puntos de los tesoros y la
     * bonificación correspondiente al tamaño del terreno.
     *
     * @return experiencia total obtenida.
     */
    public int getExperienciaObtenida() {
        switch (terreno.getTamanho()) {
            case PEQUENO:
                return puntos + 2;
            case MEDIANO:
                return puntos + 5;
            default: // GRANDE (el constructor ya descarta cualquier otro tamaño)
                return puntos + 10;
        }
    }

    /**
     * Devuelve una copia de los códigos completos de los tesoros encontrados.
     *
     * @return lista independiente con los códigos de los tesoros y sus partes.
     */
    public List<String> getCodigosEncontrados() {
        return new ArrayList<>(codigosEncontrados);
    }

    /**
     * Devuelve la región asociada a esta excavación.
     *
     * @return región cuyos tesoros se utilizan en la excavación.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Devuelve el arqueólogo que participa en esta excavación.
     *
     * @return arqueólogo asociado a la excavación.
     */
    public Archeologist getArqueologo() {
        return arqueologo;
    }

    // ===== Rejilla ========================================================

    /**
     * Devuelve una representación textual de la rejilla tal como la ve el
     * jugador, con las casillas aún ocultas.
     *
     * @return rejilla visible durante la excavación.
     */
    @Override
    public String toString() {
        return dibujar(casillas);
    }

    /**
     * Devuelve la rejilla mostrando el contenido de todas sus casillas.
     *
     * @return representación textual de la rejilla completamente revelada.
     */
    public String getRejillaDescubierta() {
        ArrayList<String> reveladas = new ArrayList<>();
        for (int i = 0; i < casillas.size(); i++) {
            reveladas.add(getSimbolo(i));
        }
        return dibujar(reveladas);
    }

    /**
     * Construye la representación textual de la rejilla usando los símbolos
     * proporcionados.
     *
     * @param simbolos símbolos que se mostrarán en cada casilla.
     * @return rejilla formateada con etiquetas de filas y columnas.
     */
    private String dibujar(ArrayList<String> simbolos) {
        StringBuilder sb = new StringBuilder(cabecera);
        for (int fila = 0; fila < numFilas; fila++) {
            sb.append(System.lineSeparator());
            sb.append(String.format("%" + anchoNumFila + "d", fila + 1));
            for (int col = 0; col < numColumnas; col++) {
                sb.append(' ').append(centrar(simbolos.get(fila * numColumnas + col)));
            }
        }
        return sb.toString();
    }

    /**
     * Crea una cadena formada por la cantidad de espacios indicada.
     *
     * @param cantidad número de espacios que se añadirán.
     * @return cadena con los espacios solicitados.
     */
    private String espacios(int cantidad) {
        String texto = "";
        for (int i = 0; i < cantidad; i++) {
            texto += " ";
        }
        return texto;
    }

    /**
     * Centra un símbolo en el espacio reservado para una etiqueta de columna.
     *
     * @param simbolo texto que se desea centrar.
     * @return símbolo con los espacios necesarios a ambos lados.
     */
    private String centrar(String simbolo) {
        int relleno = anchoEtiqueta - simbolo.length();
        int izquierda = relleno / 2;
        return espacios(izquierda) + simbolo + espacios(relleno - izquierda);
    }

    // ===== Proceso por consola ============================================

    /**
     * Ejecuta la excavación por consola hasta que se agoten las acciones, se
     * encuentren todos los tesoros o se interrumpa la entrada.
     *
     * @return lista de códigos completos de los tesoros encontrados.
     */
    public List<String> ejecutar() {
        System.out.println(ADORNO + " Excavación en " + region.getNombre() + " " + ADORNO);
        while (!isFinalizada()) {
            mostrarTurno();
            String linea = InputHelper.leerLinea();
            procesarEntrada(linea);
            if (!isFinalizada()) {
                System.out.println(SEPARADOR_TURNO);
            }
        }
        mostrarFinal();
        return getCodigosEncontrados();
    }

    /**
     * Muestra la rejilla y la información correspondiente al turno actual.
     */
    private void mostrarTurno() {
        System.out.println(this);
        System.out.println("Número de acciones restantes: " + acciones);
        if (arqueologo.isInvestigacion()) {
            System.out.println("Quedan " + getTesorosRestantes(ETreasureRarity.COMMON) + " comunes, "
                    + getTesorosRestantes(ETreasureRarity.UNCOMMON) + " infrecuentes y "
                    + getTesorosRestantes(ETreasureRarity.RARE) + " raros.");
        }
        System.out.print("Conjunto de coordenadas a excavar separadas por espacios: ");
    }

    /**
     * Procesa las posiciones introducidas por el jugador en una línea de texto.
     * <p>
     * CAMBIO 14: se reescribe el bucle sin {@code continue} ni {@code return} dentro
     * de él. Se recorre con un índice mientras queden indicaciones y la excavación no
     * haya terminado; si sobran indicaciones, se avisa una sola vez al final.
     * </p>
     *
     * @param linea texto que contiene una o varias posiciones.
     */
    private void procesarEntrada(String linea) {
        if (linea.trim().isEmpty()) {
            System.out.println("No has introducido ninguna posición.");
            return;
        }
        String[] indicaciones = linea.trim().split(" ");
        int i = 0;
        while (i < indicaciones.length && !isFinalizada()) {
            // varios espacios seguidos generan cadenas vacías: se saltan sin "continue"
            if (!indicaciones[i].isEmpty()) {
                excavarIndicacion(indicaciones[i]);
            }
            i++;
        }
        if (i < indicaciones.length) {
            System.out.println("Se ignoran las posiciones restantes: la excavación ha terminado.");
        }
    }

    /**
     * CAMBIO 14 (método nuevo, sale del cuerpo del bucle anterior). Excava una
     * indicación. Si no es válida, se avisa al jugador y, además, se anota la
     * excepción con su pila en el registro de errores.
     *
     * @param indicacion coordenada escrita por el jugador, por ejemplo "3B".
     */
    private void excavarIndicacion(String indicacion) {
        try {
            excavar(leerPosicion(indicacion));
        } catch (IllegalArgumentException e) {
            System.out.println(indicacion + ": " + e.getMessage());
            Registro.registrarError("Posición no válida (" + indicacion + "): " + e.getMessage(), e);
        }
    }

    /**
     * Convierte un texto con formato de fila y columna en un objeto Position.
     *
     * @param texto coordenada introducida, por ejemplo, "12AB".
     * @return posición representada por el texto.
     * @throws IllegalArgumentException si el texto no tiene el formato esperado
     *                                  o la fila no es un número válido.
     */
    private Position leerPosicion(String texto) {
        int i = 0;
        while (i < texto.length() && Character.isDigit(texto.charAt(i))) {
            i++;
        }
        String letras = texto.substring(i).toUpperCase();
        if (i == 0 || letras.isEmpty()) {
            throw new IllegalArgumentException("formato no válido, usa número y letra (ej. 3B).");
        }
        try {
            return new Position(Integer.parseInt(texto.substring(0, i)), letras);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("el número de fila no es válido.");
        }
    }

    /**
     * Muestra en consola el resultado final y el resumen de tesoros encontrados.
     */
    private void mostrarFinal() {
        System.out.println(SEPARADOR_FIN);
        System.out.println("Excavación terminada");
        if (tesorosRestantes == 0) {
            System.out.println("¡Has encontrado todos los tesoros!");
        }
        System.out.println(this);
        System.out.println(getRejillaDescubierta());
        System.out.println("Se han encontrado:");
        mostrarResumen(ETreasureRarity.COMMON, "comunes");
        mostrarResumen(ETreasureRarity.UNCOMMON, "infrecuentes");
        mostrarResumen(ETreasureRarity.RARE, "raros");
    }

    /**
     * Muestra el resumen de cuántos tesoros de una rareza se encontraron respecto al total,
     * siempre que hubiera alguno en la rejilla.
     *
     * @param rareza rareza de los tesoros que se resumirán.
     * @param plural nombre en plural de la rareza para mostrar en el resumen.
     */
    private void mostrarResumen(ETreasureRarity rareza, String plural) {
        int total = getTotalTesoros(rareza);
        if (total > 0) {
            int encontrados = total - getTesorosRestantes(rareza);
            System.out.println(encontrados + "/" + total + " tesoros " + plural + ".");
        }
    }
}