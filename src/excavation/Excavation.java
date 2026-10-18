package excavation;

import helpers.UtilsHelper;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import people.Archeologist;
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

    private static final String SIN_EXCAVAR = "·";
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
    private final HashMap<Integer, Treasure> tesoros = new HashMap<>(); // casilla -> tesoro enterrado
    private final ArrayList<String> codigosEncontrados = new ArrayList<>();

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
        for (int i = 0; i < total; i++) {
            casillas.add(SIN_EXCAVAR);
        }
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
     * @throws IllegalStateException si el tamaño del terreno no está reconocido
     *                               o la región no contiene un tesoro de la
     *                               rareza necesaria.
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
            if (tesoro == null) {
                throw new IllegalStateException("La región no tiene tesoros de rareza " + rarezas.get(i) + ".");
            }
            tesoros.put(posiciones.get(i), tesoro);
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
     * Aplica las revelaciones iniciales del arqueólogo sin consumir acciones.
     * Primero limpia casillas vacías y después marca posibles posiciones de
     * tesoros, sin superar el límite establecido para la rejilla.
     */
    private void aplicarRevelacion() {
        int total = numColumnas * numFilas;
        int maximo = (int) (total * REVELACION_MAX);
        int reveladas = limpiar(arqueologo.getLimpieza(), maximo);
        suponer(arqueologo.getSuposicion() / 3, maximo - reveladas);
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
                if (casillas.get(indice).equals(SIN_EXCAVAR)) {
                    casillas.set(indice, SUPOSICION);
                    marcadas++;
                }
            }
        }
    }

    /**
     * Comprueba si una casilla todavía puede excavarse.
     *
     * @param indice índice de la casilla en la rejilla.
     * @return true si no se ha excavado o solo está marcada como suposición.
     */
    private boolean sinExcavar(int indice) {
        String c = casillas.get(indice);
        return c.equals(SIN_EXCAVAR) || c.equals(SUPOSICION);
    }

    // !===== Excavar ========================================================

    /**
     * Excava la posición indicada y consume una acción. Si encuentra un tesoro,
     * actualiza los tesoros restantes, la puntuación y los códigos encontrados.
     *
     * @param posicion posición que se desea excavar.
     * @return true si la casilla contenía un tesoro; false si estaba vacía.
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

        acciones--;
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
        Console console = System.console();
        System.out.println(ADORNO + " Excavación en " + region.getNombre() + " " + ADORNO);
        while (!isFinalizada()) {
            mostrarTurno();
            String linea = console.readLine();
            if (linea == null) {
                System.out.println("No hay entrada disponible. Se interrumpe la excavación.");
                break;
            }
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
     *
     * @param linea texto que contiene una o varias posiciones.
     */
    private void procesarEntrada(String linea) {
        if (linea.trim().isEmpty()) {
            System.out.println("No has introducido ninguna posición.");
            return;
        }
        for (String indicacion : linea.trim().split(" ")) {
            if (indicacion.isEmpty()) {
                continue; // varios espacios seguidos generan cadenas vacías
            }
            if (isFinalizada()) {
                System.out.println("Se ignoran las posiciones restantes: la excavación ha terminado.");
                return;
            }
            try {
                excavar(leerPosicion(indicacion));
            } catch (IllegalArgumentException e) {
                System.out.println(indicacion + ": " + e.getMessage());
            }
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