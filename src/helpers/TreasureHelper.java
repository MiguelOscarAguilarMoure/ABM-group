package helpers;

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
     * Muestra la ficha de un tesoro con un código concreto
     * (al excavar, el código lleva la parte encontrada).
     */
    public static void mostrar(Treasure t, String codigo) {
        System.out.println("-------------------- " + t.getNombre() + " --------------------");
        System.out.println(t.getDescripcion());
        System.out.println("Código: " + codigo);

        String linea = UtilsHelper.ucfirst(t.getTipoTesoro().toString()) + " " + getRareza(t);
        if (t.getPartes() > 1) {
            String dividido = esFemenino(t.getTipoTesoro()) ? "dividida" : "dividido";
            linea += " " + dividido + " en " + t.getPartes() + " partes";
        }
        System.out.println(linea);
    }

    /** Ficha con el código base del tesoro (la usa el Catálogo). */
    public static void mostrar(Treasure t) {
        mostrar(t, t.getCodigo());
    }
}
