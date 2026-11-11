package regions.civilizaciones;

import regions.Region;

/**
 * Región básica de Iberia.
 * <p>
 * Sus tesoros se cargan desde {@code data/regions/IBR.json} en el
 * {@code preInit} del juego.
 * </p>
 *
 * @author Miguel Aguilar
 * @author Borja Abal Dios
 * @version 2.0
 */
public class Iberia extends Region {

    /** Código de la región. */
    public static final String CODIGO = "IBR";

    /** Crea la región de Iberia, todavía sin tesoros. */
    public Iberia() {
        super("Iberia", CODIGO);
    }
}