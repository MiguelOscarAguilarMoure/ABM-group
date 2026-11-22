package regions.civilizaciones;

import excavation.ETerreno;
import java.util.Map;
import regions.RegionDificil;

/**
 * Región difícil de Egipto, de la expansión Imperio de las arenas.
 * <p>
 * Terreno: 10% agua, 35% tierra y 55% arena. Necesita licencia para excavar.
 * Sus tesoros se cargan desde {@code data/regions/EGP.json}.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 */
public class Egypt extends RegionDificil {

    /** Código de la región. */
    public static final String CODIGO = "EGP";

    /** Crea la región de Egipto con la proporción de cada terreno. */
    public Egypt() {
        super("Egipto", CODIGO, "Imperio de las arenas",
                Map.of(ETerreno.AGUA, 10, ETerreno.TIERRA, 35, ETerreno.ARENA, 55));
    }
}