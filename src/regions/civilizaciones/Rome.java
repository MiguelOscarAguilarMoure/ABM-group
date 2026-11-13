package regions.civilizaciones;

import regions.Region;

/**
 * Región básica de Roma.
 * <p>
 * Sus tesoros se cargan desde {@code data/regions/ROM.json} en el
 * {@code preInit} del juego.
 * </p>
 *
 * @author Miguel Aguilar
 * @author Borja Abal Dios
 * @version 2.0
 */
public class Rome extends Region {

    /** Código de la región. */
    public static final String CODIGO = "ROM";

    /** Crea la región de Roma, todavía sin tesoros. */
    public Rome() {
        super("Roma", CODIGO);
    }
}