package regions.civilizaciones;

import excavation.ETerreno;
import java.util.Map;
import regions.RegionDificil;

/**
 * Región difícil de Sumeria, de la expansión Imperio de las arenas.
 * <p>
 * Terreno: 30% piedra, 50% arena y 20% tierra. Necesita licencia para excavar.
 * Sus tesoros se cargan desde {@code data/regions/SUM.json}.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 */
public class Sumeria extends RegionDificil {

    /** Código de la región. */
    public static final String CODIGO = "SUM";

    /** Crea la región de Sumeria con la proporción de cada terreno. */
    public Sumeria() {
        super("Sumeria", CODIGO, "Imperio de las arenas",
                Map.of(ETerreno.PIEDRA, 30, ETerreno.ARENA, 50, ETerreno.TIERRA, 20));
    }
}