package excavation;

/**
 * Representa una posición dentro del tablero de excavación.
 * 
 * Cada posición se identifica mediante una fila numérica y una columna
 * expresada como letra, permitiendo localizar de forma sencilla una casilla
 * concreta dentro del terreno.
 *
 * @author Miguel Aguilar
 * @author Borja Abal
 * @version 1.0
 * @since 2026-10-18
 */
public class Position {
    private int fil;
    private String col;

    /**
     * Construye una nueva posición con la fila y la columna indicadas.
     *
     * @param fil número de fila en la que se encuentra la posición.
     * @param col identificador de la columna.
     */
    public Position(int fil, String col) {
        this.fil = fil;
        this.col = col;
    }

    /**
     * Devuelve el número de fila de la posición.
     *
     * @return fila de la casilla.
     */
    public int getFil() {
        return fil;
    }

    /**
     * Devuelve la columna de la posición.
     *
     * @return identificador de la columna.
     */
    public String getCol() {
        return col;
    }

    /**
     * Devuelve una representación textual de la posición.
     *
     * @return cadena formada por la fila y la columna.
     */
    public String toString() {
        return fil + col;
    }
}
