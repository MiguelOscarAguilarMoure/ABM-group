package commons.repositories;
import helpers.CodeHelper;
import java.util.ArrayList;
import java.util.List;
import regions.Region;
import treasures.Treasure;
/**
 * Repositorio de todas las regiones del juego.
 * <p>
 * Las regiones se registran una sola vez en {@code preInit} y se obtienen siempre de aquí,
 * así cada región existe como un único objeto en todo el programa.
 * Los tesoros no se registran aparte: están dentro de cada región y se accede a ellos a través de ella.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see regions.Region
 * @see treasures.Treasure
 */
public class RegionRepository {

    /** Lista con todas las regiones registradas. */
    private static final List<Region> regiones = new ArrayList<>();
    
    /**
     * Registra una región en el repositorio.
     * @param r La región a registrar.
     */
    public static void registrar(Region r) {
        regiones.add(r);
    }

    /**
     * Devuelve todas las regiones registradas.
     * @return Una copia de la lista de regiones.
     */
    public static List<Region> getTodas() {
        return List.copyOf(regiones);
    }

    /**
     * Busca una región por su código.
     * @param codigo El código de la región, por ejemplo "GRC".
     * @return La región con ese código, o null si no existe.
     */
    public static Region get(String codigo) {
        for (Region r : regiones) {
            if (r.getCodigo().equals(codigo)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Busca un tesoro por su código, a través de su región.
     * @param codigo El código del tesoro, con o sin parte ("GRC_CER_001" o "GRC_CER_001_0005").
     * @return El tesoro, o null si no existe.
     */
    public static Treasure getTesoro(String codigo) {
        Region r = get(CodeHelper.getRegion(codigo));
        if (r == null) {
            return null;
        }
        return r.getTesoro(CodeHelper.getFullTreasure(codigo));
    }
}
