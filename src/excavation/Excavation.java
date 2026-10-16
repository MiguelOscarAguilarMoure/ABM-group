package excavation;

import auxiliares.UtilsHelper;
import regiones.Region;
import tesoros.ETreasureRarity;
import tesoros.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Una excavación: genera la rejilla y los tesoros enterrados, y controla
 * el estado del proceso (acciones, tesoros y posiciones restantes).
 */
public class Excavation {

    private static final char[] LETRAS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final Random RANDOM = new Random();

    private static final String SIN_EXCAVAR = "·";
    private static final String VACIO = "x";

    // Valores de ETerrainSize.getTamanho()
    private static final String PEQUENO = "Pequeño";
    private static final String MEDIANO = "Mediano";
    private static final String GRANDE = "Grande";

    private static final double ACCIONES_MIN = 0.25; // 25 % de la rejilla
    private static final double ACCIONES_MAX = 0.75; // 75 % de la rejilla

    private final ETerrainSize terreno;
    private final Region region;
    private final int numColumnas;
    private final int numFilas;

    private final ArrayList<String> columnas = new ArrayList<>();        // A, B... AA, AB...
    private final ArrayList<String> casillas = new ArrayList<>();        // lo que ve el jugador
    private final HashMap<Integer, Treasure> tesoros = new HashMap<>();  // casilla -> tesoro enterrado
    private final ArrayList<String> codigosEncontrados = new ArrayList<>();

    private String cabecera;
    private int anchoEtiqueta;
    private int anchoNumFila;
    private int acciones;
    private int tesorosRestantes;
    private int cavarRestantes;
    private int puntos;

    // TODO Añadir el arqueólogo que realiza la excavación y aplicar sus habilidades.
     public Excavation(ETerrainSize terreno, Region region) {
        if (terreno == null || region == null) {
            throw new IllegalArgumentException("El terreno y la región no pueden ser nulos.");
        }
        this.terreno = terreno;
        this.region = region;
        // En ETerrainSize "AxB" es ancho x alto: A = columnas (letras), B = filas (números).
        this.numColumnas = terreno.getFil();
        this.numFilas = terreno.getCol();

        generarRejilla();
        generarTesoros();
        this.acciones = calcularAcciones();
    }

    // ===== Generación =====================================================
    // genera la rejilla de casillas y las etiquetas de columna (A, B, C... AA, AB...)
    private void generarRejilla() {
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
        // Cabecera: espacios para el número de fila + etiquetas de columna
       StringBuilder sb = new StringBuilder(espacios(anchoNumFila));
        for (String etiqueta : columnas) {
            sb.append(' ').append(etiqueta);
        }
        cabecera = sb.toString();
    }

    /** Genera recursivamente las etiquetas de columna sin pasarse del número necesario. */
    private void generarEtiquetas(String actual) {
        if (actual.length() == anchoEtiqueta) {
            columnas.add(actual);
            return;
        }
        for (int i = 0; i < LETRAS.length && columnas.size() < numColumnas; i++) {
            generarEtiquetas(actual + LETRAS[i]);
        }
    }
    // genera la lista de tesoros a enterrar y los coloca en las casillas aleatoriamente
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
        boolean permiteRaros = ! (terreno.getTamanho().equals(PEQUENO)) ;
        while (rarezas.size() < cantidad) {
            rarezas.add(tirarRareza(permiteRaros));
        }
        // TODO Extras del arqueólogo (raros, infrecuentes, comunes y aleatorios) sin pasar del 50 % de casillas.

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

    private void anadir(ArrayList<ETreasureRarity> lista, ETreasureRarity rareza, int veces) {
        for (int i = 0; i < veces; i++) {
            lista.add(rareza);
        }
    }

    /** 60 % común, 30 % infrecuente, 10 % rara (los terrenos pequeños no tienen raros). */
    private ETreasureRarity tirarRareza(boolean permiteRaros) {
        int tirada = RANDOM.nextInt(100);
        if (tirada < 60) {
            return ETreasureRarity.COMMON;
        }
        if (tirada < 90) {
            return ETreasureRarity.UNCOMMON;
        }
        return permiteRaros ? ETreasureRarity.RARE : ETreasureRarity.COMMON;
    }

    /** (25 % de la rejilla + extra) * factor, entre 1 y el 75 % de la rejilla. */
    private int calcularAcciones() {
        int total = numColumnas * numFilas;
        int extra = 0;        // TODO Extra del arqueólogo
        double factor = 1.0;  // TODO Producto de los factores de sus habilidades
        int calculadas = (int) Math.round((total * ACCIONES_MIN + extra) * factor);
        int maximo = (int) (total * ACCIONES_MAX);
        return Math.max(1, Math.min(calculadas, maximo));
    }

    // ===== Excavar ========================================================

    /**
     * Excava una posición, consumiendo una acción.
     *
     * @param posicion La posición a excavar.
     * @return <code>true</code> si había un tesoro.
     * @throws IllegalArgumentException Si la posición es nula, no existe o ya está excavada.
     * @throws IllegalStateException    Si la excavación ya ha terminado.
     */
    public boolean excavar(Position posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        if (isFinalizada()) {
            throw new IllegalStateException("La excavación ya ha terminado.");
        }
        int indice = getIndice(posicion);
        if (!casillas.get(indice).equals(SIN_EXCAVAR)) {
            throw new IllegalArgumentException("La posición " + posicion + " ya está excavada.");
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
        return ( (fila - 1) * numColumnas + columna) ;
    }

    /** Símbolo de lo que hay enterrado en la casilla: C, I, R o x. */
    private String getSimbolo(int indice) {
        Treasure tesoro = tesoros.get(indice);
        return (tesoro == null) ? VACIO : tesoro.getRarezaTesoro().getCode();
    }

    // ===== Estado =========================================================

    public boolean isFinalizada() {
        return ((acciones <= 0) || (tesorosRestantes <= 0)) ;
    }

    public int getAccionesRestantes() {
        return acciones;
    }

    public int getTesorosRestantes() {
        return tesorosRestantes;
    }

    public int getPosicionesRestantes() {
        return cavarRestantes;
    }

    public int getPuntosObtenidos() {
        return puntos;
    }

    /** Puntos de los tesoros más el extra por el tamaño del terreno (2, 5 o 10). */
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

    /** @return Una copia con los códigos completos (con parte) de los tesoros encontrados. */
    public List<String> getCodigosEncontrados() {
        return new ArrayList<>(codigosEncontrados);
    }

    public Region getRegion() {
        return region;
    }

    // ===== Rejilla ========================================================

   
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
    private String espacios(int cantidad) {
    String texto = "";
    for (int i = 0; i < cantidad; i++) {
        texto += " ";
    }
    return texto;
}
    /** Centra el símbolo bajo la etiqueta de su columna. */
    private String centrar(String simbolo) {
        int relleno = anchoEtiqueta - simbolo.length();
        int izquierda = relleno / 2;
        return espacios(izquierda) + simbolo + espacios(relleno - izquierda);
    }
     /** La rejilla tal y como la ve el jugador. */
    @Override
    public String toString() {
        return dibujar(casillas);
    }

    /** La rejilla con todo el contenido revelado. */
    public String getRejillaDescubierta() {
        ArrayList<String> reveladas = new ArrayList<>();
        for (int i = 0; i < casillas.size(); i++) {
            reveladas.add(getSimbolo(i));
        }
        return dibujar(reveladas);
    }


    // ===== Proceso por consola ============================================

    /**
     * Ejecuta el proceso de excavación hasta quedarse sin acciones o sin tesoros.
     * El Scanner lo crea y lo cierra quien llama (cerrarlo aquí cerraría System.in).
     *
     * @param sc Scanner sobre la entrada del usuario.
     * @return Los códigos completos de los tesoros encontrados.
     */
    public List<String> ejecutar(Scanner sc) {
        if (sc == null) {
            throw new IllegalArgumentException("El Scanner no puede ser nulo.");
        }
        while (!isFinalizada()) {
            System.out.println();
            System.out.println("Acciones restantes: " + acciones);
            System.out.println(this);
            System.out.print("Posiciones a excavar (ej. 3B 4C): ");

            String linea;
            try {
                linea = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("No hay entrada disponible. Se interrumpe la excavación.");
                break;
            }
            procesarEntrada(linea);
        }
        mostrarFinal();
        return getCodigosEncontrados();
    }

    private void procesarEntrada(String linea) {
        if (linea.trim().isEmpty()) {
            System.out.println("No has introducido ninguna posición.");
            return;
        }
        for (String indicacion : linea.trim().split(" ")) {
            if (isFinalizada()) {
                System.out.println("Se ignoran las posiciones restantes: la excavación ha terminado.");
                return;
            }
            try {
                Position posicion = leerPosicion(indicacion);
                boolean hallazgo = excavar(posicion);
                System.out.println("  " + posicion + ": " + (hallazgo ? "¡tesoro encontrado!" : "nada"));
            } catch (IllegalArgumentException e) {
                System.out.println("  " + indicacion + ": " + e.getMessage());
            }
        }
    }

    /** Convierte un texto como "12AB" en una posición (fila 12, columna AB). */
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
    // Finaliza la excavación mostrando el estado final y la rejilla descubierta.
    private void mostrarFinal() {
        System.out.println();
        if (tesorosRestantes == 0) {
            System.out.println("¡Has encontrado todos los tesoros!");
        }
        System.out.println("Estado final de la rejilla:");
        System.out.println(this);
        System.out.println();
        System.out.println("Rejilla descubierta:");
        System.out.println(getRejillaDescubierta());
    }
}