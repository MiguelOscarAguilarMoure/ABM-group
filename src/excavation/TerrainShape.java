package excavation;

/**
 * Define las distintas formas geométricas que conforma un terreno de
 * excavación.
 * <p>
 * Cada constante representa una disposición concreta del espacio de trabajo,
 * como cuadrado, fila, columna, horizontal o vertical.
 *
 * @author Borja Abal
 * @version 1.0
 * @since 2026-10-18
 */
public enum TerrainShape {
    SQUARE("cuadrado"),
    ROW("fila"),
    COLUMN("columna"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String nombre;

    /**
     * Construye una forma geométrica con su nombre descriptivo.
     *
     * @param nombre nombre que identifica la forma del terreno.
     */
    TerrainShape(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la representación textual de la forma del terreno.
     *
     * @return nombre de la forma geométrica.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
