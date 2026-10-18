
package commons.operations;

import commons.Archaios;
import helpers.InputHelper;
import stats.Stats;

/**
 * Opción "Estadísticas" del menú principal.
 * <p>
 * Muestra los menús y pide al usuario qué quiere ver: estadísticas de lugares
 * o de tesoros (por región o por tipo). Los datos y los cálculos están en
 * {@link Stats};
 * esta clase solo se encarga de los menús.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see stats.Stats
 */
public class StatsOperation implements IOperation {
    /** Partida en curso, de la que se obtienen las estadísticas. */
    private final Archaios juego;

    /**
     * Crea la operación de estadísticas.
     *
     * @param juego La partida en curso.
     */
    public StatsOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Estadísticas";
    }

    /**
     * Menú de estadísticas: lugares o tesoros.
     * Tras ver una, vuelve a este menú; con 0 vuelve al menú principal.
     */
    @Override
    public void operation() {
        Stats stats = juego.getStats();
        int op;
        do {
            System.out.println("-------------------- Estadísticas --------------------");
            System.out.println("1. Lugares");
            System.out.println("2. Tesoros");
            System.out.println("0. Volver");
            op = InputHelper.leerOpcion("Opción: ", 2);

            if (op == 1) {
                stats.mostrarLugares();
            } else if (op == 2) {
                menuTesoros(stats);
            }
        } while (op != 0);
    }

    // ========================================================== AUXILIARES

    /**
     * Submenú de tesoros: por región o por tipo.
     *
     * @param stats Las estadísticas de la partida.
     */
    private void menuTesoros(Stats stats) {
        System.out.println("-------------------- Tesoros --------------------");
        System.out.println("1. Por región");
        System.out.println("2. Por tipo");
        System.out.println("0. Volver");
        int op = InputHelper.leerOpcion("Opción: ", 2);

        if (op == 1)
            stats.mostrarPorRegion();
        if (op == 2)
            stats.mostrarPorTipo();
    }
}
