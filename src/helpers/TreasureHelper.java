package helpers;

import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Funciones auxiliares para mostrar tesoros.
 * <p>
 * Se usa en el Atlas, en el Catálogo y al terminar una excavación,
 * para que la ficha de un tesoro se vea igual en todos los sitios.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see treasures.Treasure
 */
public class TreasureHelper {
    /** Clase de métodos estáticos: no se crean objetos de ella. */
    private TreasureHelper() {
    }

    /**
     * Indica si el tipo de tesoro es femenino, para que concuerden la rareza
     * y "dividido/dividida". Equipo y fragmento son masculinos; el resto,
     * femeninos.
     *
     * @param tipo El tipo del tesoro.
     * @return {@code true} si es femenino; {@code false} si es masculino.
     */
    public static boolean esFemenino(ETreasureType tipo) {
        return tipo != ETreasureType.EQUIP && tipo != ETreasureType.FRAGMENT;
    }

    /**
     * Devuelve la rareza del tesoro concordando en género con su tipo.
     * Solo cambia "raro", que pasa a "rara" en femenino;
     * "común" e "infrecuente" son iguales en los dos géneros.
     *
     * @param t El tesoro.
     * @return La rareza en texto (por ejemplo "común", "rara" o "raro").
     */
    public static String getRareza(Treasure t) {
        if (t.getRarezaTesoro() == ETreasureRarity.RARE && esFemenino(t.getTipoTesoro())) {
            return "rara";
        }
        return t.getRarezaTesoro().toString();
    }

    /**
     * Muestra la ficha de un tesoro con un código concreto: nombre, descripción,
     * código, tipo, rareza y, si tiene varias partes, en cuántas está dividido.
     * Al excavar se le pasa el código con la parte encontrada.
     *
     * @param t      El tesoro.
     * @param codigo El código a mostrar (por ejemplo "ROM_COI_001_0003").
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

    /**
     * Muestra la ficha de un tesoro con su código base, sin parte.
     * La usan el Atlas y el Catálogo.
     *
     * @param t El tesoro.
     */
    public static void mostrar(Treasure t) {
        mostrar(t, t.getCodigo());
    }
}
