package excavation;

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

    private ETerrainSize(int fil, int col, String tamanho, String nombre, int nivelMinimo, boolean cuadrado) {
        this.fil = fil;
        this.col = col;
        this.tamanho = tamanho;
        this.nombre = nombre;
        this.nivelMinimo = nivelMinimo;
        this.cuadrado = cuadrado;
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
    public int getNivelMinimo() {
        switch (tamanho) {
        case "Pequeño":
            return 0;
        case "Mediano":
            return 2;
        default:
            return 4;
    }
    }
    public boolean isCuadrado() {
        return nombre.equals("Cuadrado");
    }
    public int getNumeroCasillas() {
        return (fil * col);
    }
    public  int getNivelMinimoPorTamanho() {
        return nivelMinimo;
    }
   
    

    

}
