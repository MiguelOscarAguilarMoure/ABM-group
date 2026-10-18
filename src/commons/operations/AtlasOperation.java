package commons.operations;

import commons.repositories.RegionRepository;
import helpers.InputHelper;
import helpers.TreasureHelper;
import java.util.List;
import regions.Region;
import treasures.Treasure;

/**
 * Opción Atlas: muestra las regiones con todos sus tesoros posibles.
 */
public class AtlasOperation  implements  IOperation{

    

    @Override
    public String getName() {
        return "Atlas";
    }

    @Override
    public void operation() {
        boolean seguir = true;

        while (seguir) {
            // 1. Elegir la región
            Region region = elegirRegion();
            if (region == null) {
                return;   // eligió 0: vuelve al menú principal
            }

            // 2. Mostrar sus tesoros
            mostrarRegion(region);

            // 3. ¿Volver al atlas o al menú?
            seguir = InputHelper.confirmar("¿Volver al atlas?");
            
        }
    }
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
    private void mostrarRegion(Region region) {
        System.out.println("-------------------- " + region.getNombre() + " --------------------");
        for (Treasure t : region.getTesoros()) {
            System.out.println("-" + t.getNombre() + " (" + t.getTipoTesoro() + " " + TreasureHelper.getRareza(t) + ")");
        }
    }
    
}
