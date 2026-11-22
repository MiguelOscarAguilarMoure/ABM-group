package regions;

/**
 * Un tesoro tal y como viene en los ficheros JSON de las regiones.
 * <p>
 * Gson rellena cada atributo con el campo del JSON que se llama igual.
 * Después se convierte en un {@link treasures.Treasure} con {@link RegionLoader}.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 */
public class TreasureData {

    /** Código completo del tesoro (por ejemplo "EGP_MON_001"). */
    String code;
    /** Nombre del tesoro. */
    String name;
    /** Descripción del tesoro. */
    String description;
    /** Código de la rareza: "C", "I" o "R". */
    String rarity;
    /** Número de partes. */
    int parts;
    /** Puntos que da. */
    int points;
    /** Código del tipo: "MON", "CER"... */
    String type;
}