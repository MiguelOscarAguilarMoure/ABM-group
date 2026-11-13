package excavation;

/**
 * Define los distintos tamaños y formas de terreno que pueden utilizarse durante
 * la excavación.
 * <p>
 * Cada constante del enum representa una configuración concreta de filas,
 * columnas, tamaño, forma y nivel mínimo necesario para realizar una
 * excavación, de esta forma, el sistema puede describir y
 * comparar de manera uniforme los distintos tipos de parcelas que puede
 * generar el juego.
 * <p>
 * De esta forma, queda modelado, el espacio de trabajo del arqueólogo de forma
 * estructurada, facilitando la selección del terreno más adecuado según el
 * nivel del jugador y la forma geométrica que se desea obtener.
 *
 * @author Miguel Aguilar
 * @version 1.0
 * @since 2026-10-18
 */
public enum ETerrainSize {

    P_FILA(5, 3, "Pequeño", "Fila", 0, false),
    P_HORIZONTAL(10, 2, "Pequeño", "Horizontal", 0, false),
    P_COLUMNA(3, 5, "Pequeño", "Columna", 0, false),
    P_VERTICAL(2, 10, "Pequeño", "Vertical", 0, false),
    P_CUADRADO(5, 5, "Pequeño", "Cuadrado", 0, true),
    M_FILA(20, 5, "Mediano", "Fila", 2, false),
    M_HORIZONTAL(30, 3, "Mediano", "Horizontal", 2, false),
    M_COLUMNA(5, 20, "Mediano", "Columna", 2, false),
    M_VERTICAL(3, 30, "Mediano", "Vertical", 2, false),
    M_CUADRADO(10, 10, "Mediano", "Cuadrado", 2, true),
    G_FILA(20, 10, "Grande", "Fila", 4, false),
    G_HORIZONTAL(40, 5, "Grande", "Horizontal", 4, false),
    G_COLUMNA(10, 20, "Grande", "Columna", 4, false),
    G_VERTICAL(5, 40, "Grande", "Vertical", 4, false),
    G_CUADRADO(15, 15, "Grande", "Cuadrado", 4, true);

    private final int fil;
    private final int col;
    private final String tamanho;
    private final String nombre;
    private final int nivelMinimo;
    private final boolean cuadrado;

    /**
     * Construye un tipo de terreno con sus dimensiones y atributos asociados.
     *
     * @param fil número de filas del terreno.
     * @param col número de columnas del terreno.
     * @param tamanho tamaño del terreno, como pequeño, mediano o grande.
     * @param nombre forma geométrica del terreno.
     * @param nivelMinimo nivel mínimo necesario para poder usar esa configuración.
     * @param cuadrado indica si el terreno tiene forma cuadrada.
     */
    private ETerrainSize(int fil, int col, String tamanho, String nombre, int nivelMinimo, boolean cuadrado) {
        this.fil = fil;
        this.col = col;
        this.tamanho = tamanho;
        this.nombre = nombre;
        this.nivelMinimo = nivelMinimo;
        this.cuadrado = cuadrado;
    }

    /**
     * Devuelve el número de filas del terreno.
     *
     * @return cantidad de filas de la zona de excavación.
     */
    public int getFil() {
        return fil;
    }

    /**
     * Devuelve el número de columnas del terreno.
     *
     * @return cantidad de columnas de la zona de excavación.
     */
    public int getCol() {
        return col;
    }

    /**
     * Devuelve el tamaño del terreno.
     *
     * @return valor textual que indica si el terreno es pequeño, mediano o grande.
     */
    public String getTamanho() {
        return tamanho;
    }

    /**
     * Obtiene el nombre de la forma geométrica del terreno.
     *
     * @return nombre de la forma, como fila, columna, vertical u horizontal.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el nivel mínimo requerido para utilizar este tipo de terreno.
     *
     * @return nivel mínimo necesario para acceder a esta configuración.
     */
    public int getNivelMinimo() {
        return nivelMinimo;
    }

    /**
     * Indica si el terreno tiene forma cuadrada.
     *
     * @return true si es un terreno cuadrado; false en caso contrario.
     */
    public boolean isCuadrado() {
        return cuadrado;
    }

    /**
     * Calcula el número total de casillas del terreno.
     *
     * @return resultado del producto entre filas y columnas.
     */
    public int getNumeroCasillas() {
        return (fil * col);
    }

    /**
     * Devuelve la forma geométrica asociada a este terreno para compararla con la
     * del arqueólogo.
     *
     * @return la forma equivalente representada mediante {@link TerrainShape}.
     */
    public TerrainShape getForma() {
        switch (nombre) {
            case "Fila":
                return TerrainShape.ROW;
            case "Horizontal":
                return TerrainShape.HORIZONTAL;
            case "Columna":
                return TerrainShape.COLUMN;
            case "Vertical":
                return TerrainShape.VERTICAL;
            default:
                return TerrainShape.SQUARE;
        }
    }
}
