package regions;

import excavation.ETerreno;
import java.util.Map;

/**
 * Marca una región como difícil: tiene terrenos especiales (piedra, arena, agua)
 * y pertenece a una expansión, por lo que necesita licencia para excavar en ella.
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see RegionDificil
 */
public interface IRegionDificil {

    /**
     * Devuelve qué porcentaje de la rejilla ocupa cada terreno.
     * La rejilla de la excavación se rellena según estas proporciones.
     *
     * @return Cada terreno con su porcentaje (entre todos suman 100).
     */
    Map<ETerreno, Integer> getProporciones();

    /**
     * Devuelve la expansión a la que pertenece la región. Se muestra en el Atlas.
     *
     * @return El nombre de la expansión (por ejemplo "Imperios de las arenas").
     */
    String getExpansion();
}