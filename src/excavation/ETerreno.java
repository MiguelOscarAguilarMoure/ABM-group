package excavation;

/**
 * Tipos de terreno que puede tener una casilla de la rejilla de excavación.
 * <p>
 * Cada terreno tiene un símbolo (el que ve el jugador mientras la casilla está
 * sin excavar), un coste en acciones y, opcionalmente, un siguiente nivel: al
 * excavarlo no se descubre lo que hay debajo, sino que la casilla pasa a ese
 * terreno y hay que excavarla de nuevo.
 * </p>
 * <ul>
 * <li><b>Tierra</b> {@code .} (coste 1): el terreno básico.</li>
 * <li><b>Piedra</b> {@code ^} (coste 1): al romperla pasa a tierra.</li>
 * <li><b>Arena</b> {@code ~} (coste 1-2, variable).</li>
 * <li><b>Agua</b> {@code -} (coste 2).</li>
 * </ul>
 *
 * @author Miguel Aguilar Moure
 * @version 2.0
 * @see regions.IRegionDificil#getProporciones()
 */
public enum ETerreno {
    // El siguiente nivel debe declararse antes que el terreno que lo usa (TIERRA antes que PIEDRA).
    TIERRA(".", 1, 1, null),
    PIEDRA("^", 1, 1, TIERRA),
    ARENA("~", 1, 2, null),
    AGUA("-", 2, 2, null);

    /** Símbolo que se dibuja en la rejilla. */
    private final String simbolo;
    /** Coste mínimo en acciones. */
    private final int costeMin;
    /** Coste máximo en acciones (igual al mínimo si el coste es fijo). */
    private final int costeMax;
    /** Terreno en el que se convierte al excavarlo ({@code null} si no tiene). */
    private final ETerreno siguiente;

    /**
     * Crea un terreno.
     *
     * @param simbolo   Símbolo de la rejilla.
     * @param costeMin  Coste mínimo en acciones.
     * @param costeMax  Coste máximo en acciones (igual al mínimo si es fijo).
     * @param siguiente Terreno al que pasa al excavarlo, o {@code null} si al
     *                  excavarlo se descubre lo que hay debajo.
     */
    private ETerreno(String simbolo, int costeMin, int costeMax, ETerreno siguiente) {
        this.simbolo = simbolo;
        this.costeMin = costeMin;
        this.costeMax = costeMax;
        this.siguiente = siguiente;
    }

    /**
     * @return El símbolo que se muestra en la rejilla.
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @return El coste mínimo en acciones.
     */
    public int getCosteMin() {
        return costeMin;
    }

    /**
     * @return El coste máximo en acciones.
     */
    public int getCosteMax() {
        return costeMax;
    }

    /**
     * Indica si el coste varía (se sortea entre el mínimo y el máximo).
     *
     * @return {@code true} si el coste mínimo y el máximo son distintos.
     */
    public boolean isCosteVariable() {
        return costeMin != costeMax;
    }

    /**
     * @return El terreno al que pasa al excavarlo, o {@code null} si no tiene siguiente nivel.
     */
    public ETerreno getSiguiente() {
        return siguiente;
    }

    /**
     * @return {@code true} si al excavarlo pasa a otro terreno y hay que volver a excavar la casilla.
     */
    public boolean tieneSiguiente() {
        return siguiente != null;
    }

    
}
