package excavation;

public enum ETerrainSize {

    P_FILA(5, 3, "Pequeño", "Fila"),
    P_HORIZONTAL(10, 2, "Pequeño", "Horizontal"),
    P_COLUMNA(3, 5, "Pequeño", "Columna"),
    P_VERTICAL(2, 10, "Pequeño", "Vertical"),
    P_CUADRADO(5, 5, "Pequeño", "Cuadrado"),
    M_FILA(20, 5, "Mediano", "Fila"),
    M_HORIZONTAL(30, 3, "Mediano", "Horizontal"),
    M_COLUMNA(5, 20, "Mediano", "Columna"),
    M_VERTICAL(3, 30, "Mediano", "Vertical"),
    M_CUADRADO(10, 10, "Mediano", "Cuadrado"),
    G_FILA(20, 10, "Grande", "Fila"),
    G_HORIZONTAL(40, 5, "Grande", "Horizontal"),
    G_COLUMNA(10, 20, "Grande", "Columna"),
    G_VERTICAL(5, 40, "Grande", "Vertical"),
    G_CUADRADO(15, 15, "Grande", "Cuadrado");

    private final int fil;
    private final int col;
    private final String tamanho;
    private final String nombre;

    private ETerrainSize(int fil, int col, String tamanho, String nombre) {
        this.fil = fil;
        this.col = col;
        this.tamanho = tamanho;
        this.nombre = nombre;
    }

    public int getFil() {
        return fil;
    }

    public int getCol() {
        return col;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getNombre() {
        return nombre;
    }
    

}
