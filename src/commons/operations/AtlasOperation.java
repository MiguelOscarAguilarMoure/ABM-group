package commons.operations;

import commons.repositories.RegionRepository;
import helpers.InputHelper;
import helpers.TreasureHelper;
import java.util.List;
import regions.Region;
import treasures.Treasure;

/**
 * Opción "Atlas" del menú principal.
 * <p>
 * Muestra las regiones y, al elegir una, todos los tesoros que se pueden
 * encontrar
 * en ella con su tipo y su rareza. Después pregunta si se quiere volver al
 * atlas:
 * si la respuesta es sí vuelve a la lista de regiones, y si es no, al menú
 * principal.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see commons.repositories.RegionRepository
 * @see helpers.TreasureHelper
 */
public class AtlasOperation implements IOperation {

    @Override
    public String getName() {
        return "Atlas";
    }

    /**
     * Bucle del atlas: región → tesoros de la región → "¿Volver al atlas?".
     * Con 0 en la lista de regiones vuelve al menú principal.
     */

    @Override
    public void operation() {
        boolean seguir = true;

        while (seguir) {

            Region region = elegirRegion();
            if (region == null) {
                seguir = false;
            } else {

                mostrarRegion(region);

                seguir = InputHelper.confirmar("¿Volver al atlas?");
            }

        }
    }

    /**
     * Muestra las regiones y pide una.
     *
     * @return La región elegida, o {@code null} si elige 0 o una opción no válida.
     */
    private Region elegirRegion() {
        List<Region> regiones = RegionRepository.getTodas();

        System.out.println("-------------------- Atlas --------------------");
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
        }
        System.out.println("0. Volver");

        int op = InputHelper.leerEntero("Región: ");
        if (op < 1 || op > regiones.size()) {
            return null;
        }
        return regiones.get(op - 1);
    }

    /**
     * Muestra todos los tesoros de una región con su tipo y su rareza
     * (por ejemplo "-Ánfora (cerámica común)").
     *
     * @param region La región elegida.
     */
    private void mostrarRegion(Region region) {
        System.out.println("-------------------- " + region.getNombre() + " --------------------");
        for (Treasure t : region.getTesoros()) {
            System.out
                    .println("-" + t.getNombre() + " (" + t.getTipoTesoro() + " " + TreasureHelper.getRareza(t) + ")");
        }
    }

}
