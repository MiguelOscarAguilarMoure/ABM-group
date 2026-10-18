package excavation;

/**
 * Define los tamaños básicos que puede tener un terreno de excavación.
 * <p>
 * Cada constante representa una categoría de dimensión que permite clasificar
 * el
 * espacio de trabajo del arqueólogo según su escala.
 *
 * @author Borja Abal
 * @version 1.0
 * @since 2026-10-18
 */
public enum TerrainSize {
    SMALL("pequeño"), MEDIUM("mediano"), LARGE("grande");

    private final String txt;

    /**
     * Construye un tamaño de terreno con su valor textual asociado.
     *
     * @param txt nombre del tamaño del terreno.
     */
    TerrainSize(String txt) {
        this.txt = txt;
    }

    /**
     * Devuelve la representación textual del tamaño del terreno.
     *
     * @return nombre del tamaño.
     */
    @Override
    public String toString() {
        return txt;
    }
}
