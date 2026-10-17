package helpers;
/**
 * Funciones auxiliares para mostrar tesoros.
 * Se usa en el catálogo y al terminar una excavación.
 */
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Funciones auxiliares para mostrar tesoros.
 * Se usa en el Atlas, en el Catálogo y al terminar una excavación.
 */
public class TreasureHelper {

    /** Equipo y fragmento son masculinos; el resto de tipos son femeninos. */
    public static boolean esFemenino(ETreasureType tipo) {
        return tipo != ETreasureType.EQUIP && tipo != ETreasureType.FRAGMENT;
    }

    /** Rareza concordando en género con el tipo: "rara" en femenino, "raro" en masculino. */
    public static String getRareza(Treasure t) {
        if (t.getRarezaTesoro() == ETreasureRarity.RARE && esFemenino(t.getTipoTesoro())) {
            return "rara";
        }
        return t.getRarezaTesoro().toString();
    }

    /**
     * Muestra la ficha de un tesoro: nombre, descripción, código, tipo, rareza
     * y número de partes si son más de una.
     */
    public static void mostrar(Treasure t) {
        System.out.println("-------------------- " + t.getNombre() + " --------------------");
        System.out.println(t.getDescripcion());
        System.out.println("Código: " + t.getCodigo());

        String linea = UtilsHelper.ucfirst(t.getTipoTesoro().toString()) + " " + getRareza(t);
        if (t.getPartes() > 1) {
            String dividido = esFemenino(t.getTipoTesoro()) ? "dividida" : "dividido";
            linea += " " + dividido + " en " + t.getPartes() + " partes";
        }
        System.out.println(linea);
    }
}
