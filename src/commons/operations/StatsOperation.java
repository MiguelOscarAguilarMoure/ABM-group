
package commons.operations;
import commons.Archaios;
import helpers.InputHelper;
import stats.Stats;

/**
 * Opción Estadísticas: muestra los menús y pide al usuario qué quiere ver.
 * Los datos y los cálculos están en la clase Stats.
 */
public class StatsOperation implements IOperation {

    private final Archaios juego;
    

    public StatsOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Estadísticas";
    }

    /** Menú de estadísticas: lugares o tesoros. Tras ver una, vuelve a este menú. */
    @Override
    public void operation() {
        Stats stats = juego.getStats();
        int op;
        do {
            System.out.println("-------------------- Estadísticas --------------------");
            System.out.println("1. Lugares");
            System.out.println("2. Tesoros");
            System.out.println("0. Volver");
             op = InputHelper.leerEntero("Opción: ");

            if (op == 1) stats.mostrarLugares();
            if (op == 2) menuTesoros(stats);
        } while (op != 0);
    }

    // ========================================================== AUXILIARES

    /** Submenú de tesoros: por región o por tipo. */
    private void menuTesoros(Stats stats) {
        System.out.println("-------------------- Tesoros --------------------");
        System.out.println("1. Por región");
        System.out.println("2. Por tipo");
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Opción: ");

        if (op == 1) stats.mostrarPorRegion();
        if (op == 2) stats.mostrarPorTipo();
    }
}
