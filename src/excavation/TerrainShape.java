package excavation;

public enum  TerrainShape {
    ROW("fila"),
    HORINZOTAL("horizintal"),
     COLUMN("columna"),
    VERTICAL("vertical"),
    SQUARE("cuadrado");
    private final String txt;

    private TerrainShape(String txt) {
        this.txt = txt;
    }
    @Override 
    public  String toString(){
        return txt;
    }
}
