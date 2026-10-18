package commons.operations;

import commons.repositories.RegionRepository;
import helpers.InputHelper;
import helpers.TreasureHelper;
import java.util.List;
import regions.Region;
import treasures.Treasure;

/**
 * Opción "Catálogo de tesoros" del menú principal.
 * <p>
 * Muestra las regiones; al elegir una, sus tesoros; y al elegir un tesoro, su ficha
 * (nombre, descripción, código, tipo, rareza y partes). Después pregunta si se quiere
 * consultar otro tesoro de la misma región: si la respuesta es sí vuelve a la lista de tesoros,
 * y si es no, al menú principal.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see helpers.TreasureHelper
 * @see commons.repositories.RegionRepository
 */
public class CatalagoTesoros implements IOperation {

  

    @Override
    public String getName() {
        return "Catálogo de tesoros";
    }
    /**
     * Bucle del catálogo: región → tesoro → ficha → "¿Consultar otro tesoro?".
     * Con 0 en la lista de regiones vuelve al menú principal;
     * con 0 en la lista de tesoros vuelve a las regiones.
     */
    @Override
    public void operation() {
        while (true) {
            // 1. Elegir la región
            Region region = elegirRegion();
            if (region == null) {
                return;   // eligió 0: vuelve al menú principal
            }

            // 2. Elegir tesoros de esa región y ver sus fichas
            boolean volverAlMenu = verTesoros(region);
            if (volverAlMenu) {
                return;
            }
            // si eligió 0 en la lista de tesoros, vuelve a la lista de regiones
        }
    }
    

     /**
     * Deja elegir tesoros de una región y muestra su ficha, hasta que el usuario
     * elige 0 o responde que no quiere consultar otro.
     *
     * @param region La región elegida.
     * @return {@code true} si el usuario quiere volver al menú principal;
     *         {@code false} si eligió 0 para volver a la lista de regiones.
     */
    private boolean verTesoros(Region region) {
        while (true) {
            Treasure t = elegirTesoro(region);
            if (t == null) {
                return false;   // eligió 0: vuelve a las regiones
            }

            TreasureHelper.mostrar(t);

            // ¿Consultar otro tesoro de esta región o volver al menú principal?
            
            if (!InputHelper.confirmar("¿Consultar otro tesoro de " + region.getNombre() + "?")) {
                return true;
            }
        }
    }

    
    /**
     * Muestra las regiones y pide una.
     *
     * @return La región elegida, o {@code null} si elige 0 o una opción no válida.
     */
    /** Lista las regiones y devuelve la elegida, o null si elige 0. */
    private Region elegirRegion() {
        List<Region> regiones = RegionRepository.getTodas();

        System.out.println("-------------------- Catálogo de tesoros --------------------");
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
     * Muestra los tesoros de una región y pide uno.
     *
     * @param region La región elegida.
     * @return El tesoro elegido, o {@code null} si elige 0 o una opción no válida.
     */
    private Treasure elegirTesoro(Region region) {
        List<Treasure> tesoros = region.getTesoros();

        System.out.println("-------------------- " + region.getNombre() + " --------------------");
        for (int i = 0; i < tesoros.size(); i++) {
            System.out.println((i + 1) + ". " + tesoros.get(i).getNombre());
        }
        System.out.println("0. Volver");

                int op = InputHelper.leerEntero("Tesoro: ");
        if (op < 1 || op > tesoros.size()) {
            return null;
        }
        return tesoros.get(op - 1);
    }

    
   

    
    
}
