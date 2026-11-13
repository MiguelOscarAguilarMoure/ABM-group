package regions.civilizaciones;

import regions.Region;

/**
 * Región básica de Grecia.
 * <p>
 * Sus tesoros se cargan desde {@code data/regions/GRC.json} en el
 * {@code preInit} del juego.
 * </p>
 *
 * @author Miguel Aguilar
 * @author Borja Abal Dios
 * @version 2.0
 */
public class Greece extends Region {

    /** Código de la región. */
    public static final String CODIGO = "GRC";

    /** Crea la región de Grecia, todavía sin tesoros. */
    public Greece() {
        super("Grecia", CODIGO);
    }
}