package excavation;

public class Position {
    private int fil;
    private String col;

    public Position(int fil, String col) {
        this.fil = fil;
        this.col = col;
    }
    public int getFil() {
        return fil;
    }
    public String getCol() {
        return col;
    }
    public String toString() {
        return fil + col;
    }
}
