package excavation;

public enum TerrainShape {
    SQUARE("cuadrado"),
    ROW("fila"),
    COLUMN("columna"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String nombre;

    TerrainShape(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
