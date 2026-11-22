package regions.civilizaciones;

import excavation.ETerreno;
import java.util.Map;
import regions.RegionDificil;

/**
 * Región difícil de Petra, de la expansión Imperio de las arenas.
 * <p>
 * Terreno: 75% piedra y 25% arena. Necesita licencia para excavar.
 * Sus tesoros se cargan desde {@code data/regions/PTR.json}.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 */
public class Petra extends RegionDificil {

    /** Código de la región. */
    public static final String CODIGO = "PTR";

    /** Crea la región de Petra con la proporción de cada terreno. */
    public Petra() {
        super("Petra", CODIGO, "Imperio de las arenas",
                Map.of(ETerreno.PIEDRA, 75, ETerreno.ARENA, 25));
    }
}