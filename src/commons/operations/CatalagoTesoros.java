package commons.operations;

import commons.repositories.RegionRepository;
import helpers.InputHelper;
import helpers.UtilsHelper;
import java.util.List;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Opción Catálogo de tesoros: muestra la ficha individual de cada tesoro.
 */
public class CatalagoTesoros implements IOperation {

  

    @Override
    public String getName() {
        return "Catálogo de tesoros";
    }

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
     * Deja elegir tesoros de una región y muestra su ficha.
     * @return true si el usuario quiere volver al menú principal,
     *         false si eligió 0 para volver a la lista de regiones.
     */
    private boolean verTesoros(Region region) {
        while (true) {
            Treasure t = elegirTesoro(region);
            if (t == null) {
                return false;   // eligió 0: vuelve a las regiones
            }

            mostrarFicha(t);

            // ¿Consultar otro tesoro de esta región o volver al menú principal?
            
            if (!InputHelper.confirmar("¿Consultar otro tesoro de " + region.getNombre() + "?")) {
                return true;
            }
        }
    }

    // ========================================================== AUXILIARES

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

    /** Lista los tesoros de una región y devuelve el elegido, o null si elige 0. */
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

    /**
     * Muestra la ficha de un tesoro con el formato del PDF:
     * nombre, descripción, código, tipo, rareza y partes (si son más de una).
     */
    private void mostrarFicha(Treasure t) {
        System.out.println("-------------------- " + t.getNombre() + " --------------------");
        System.out.println(t.getDescripcion());
        System.out.println("Código: " + t.getCodigo());

        // "Moneda común" / "Cerámica común dividida en 10 partes" / "Equipo raro dividido en 21 partes"
        String linea = UtilsHelper.ucfirst(t.getTipoTesoro().toString()) + " " + rareza(t);
        if (t.getPartes() > 1) {
            String dividido = esFemenino(t.getTipoTesoro()) ? "dividida" : "dividido";
            linea += " " + dividido + " en " + t.getPartes() + " partes";
        }
        System.out.println(linea);
    }

    /** Rareza concordando en género con el tipo: "rara" en femenino, "raro" en masculino. */
    private String rareza(Treasure t) {
        if (t.getRarezaTesoro() == ETreasureRarity.RARE && esFemenino(t.getTipoTesoro())) {
            return "rara";
        }
        return t.getRarezaTesoro().toString();
    }

    /** Equipo y fragmento son masculinos; el resto de tipos son femeninos. */
    private boolean esFemenino(ETreasureType tipo) {
        return tipo != ETreasureType.EQUIP && tipo != ETreasureType.FRAGMENT;
    }
    
}
