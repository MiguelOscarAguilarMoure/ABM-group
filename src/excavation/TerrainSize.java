package excavation;

public enum  TerrainSize {
     SMALL("pequeño"), MEDIUM("mediano"), LARGE("grande");

    private final String txt;
    TerrainSize(String txt) { this.txt = txt; }

    @Override
    public String toString() { 
        return txt; 
    }
}
