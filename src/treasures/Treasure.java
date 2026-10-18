package treasures;

import java.util.Random;
/**
 * Tesoro que se puede encontrar al excavar en una región.
 * <p>
 * Guarda sus datos fijos: código, nombre, descripción, rareza, tipo, puntos y número de partes.
 * Todos los atributos son {@code final} porque un tesoro no cambia una vez creado.
 * </p>
 * <p>
 * El código del tesoro tiene el formato {@code RRR_TTT_NNN} (región, tipo y número).
 * Cada parte encontrada añade un sufijo de cuatro cifras: {@code RRR_TTT_NNN_PPPP}.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Miguel Aguilar
 * @version 1.0
 * @see ETreasureRarity
 * @see ETreasureType
 */
public class Treasure {
    /** Generador aleatorio compartido para elegir la parte encontrada. */
    private static final Random random = new Random();
    /** Código del tesoro sin la parte  */
    private final String codigo;
    /** Nombre del tesoro. */
    private final String nombre;
    /** Descripción del tesoro. */
    private final String descripcion;
    /** Rareza del tesoro (común, infrecuente o raro). */
    private final ETreasureRarity rarezaTesoro;
     /** Tipo del tesoro (cerámica, moneda...). */
    private final ETreasureType tipoTesoro;
    /** Puntos que da al encontrarlo. */
    private final int puntos;
     /** Número de partes en las que está dividido (1 si es entero). */
    private final int partes;

     /**
     * Crea un tesoro montando su código a partir de la región, el tipo y el número.
     * Es el constructor que usan las regiones al registrar sus tesoros.
     *
     * @param nombre       Nombre del tesoro.
     * @param codigoRegion Código de la región (por ejemplo "ROM").
     * @param tipoTesoro   Tipo del tesoro.
     * @param numero       Número del tesoro dentro de su tipo (por ejemplo "001").
     * @param partes       Número de partes del tesoro.
     * @param puntos       Puntos que da al encontrarlo.
     * @param rarezaTesoro Rareza del tesoro.
     * @param descripcion  Descripción del tesoro.
     */
    public Treasure(String nombre, String codigoRegion, ETreasureType tipoTesoro, String numero,
            int partes, int puntos, ETreasureRarity rarezaTesoro, String descripcion) {
        this(codigoRegion + "_" + tipoTesoro.getCode() + "_" + numero,
                nombre, descripcion, rarezaTesoro, tipoTesoro, partes, puntos);
    }

     /**
     * Crea un tesoro con el código ya montado.
     *
     * @param codigo       Código completo sin la parte (por ejemplo "ROM_COI_001").
     * @param nombre       Nombre del tesoro.
     * @param descripcion  Descripción del tesoro.
     * @param rarezaTesoro Rareza del tesoro.
     * @param tipoTesoro   Tipo del tesoro.
     * @param partes       Número de partes del tesoro.
     * @param puntos       Puntos que da al encontrarlo.
     */
    public Treasure(String codigo, String nombre, String descripcion,
            ETreasureRarity rarezaTesoro, ETreasureType tipoTesoro,
            int partes, int puntos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rarezaTesoro = rarezaTesoro;
        this.tipoTesoro = tipoTesoro;
        this.partes = partes;
        this.puntos = puntos;
    }
    // GETTERS
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ETreasureRarity getRarezaTesoro() {
        return rarezaTesoro;
    }

    public ETreasureType getTipoTesoro() {
        return tipoTesoro;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPartes() {
        return partes;
    }

   /**
     * Devuelve el código del tesoro con una parte elegida al azar.
     * Se usa al encontrar el tesoro en una excavación.
     *
     * @return El código con la parte, por ejemplo "ROM_COI_001_0003".
     */
    public String getCodigoConParte() {
        int parte = random.nextInt(partes) + 1;
        return String.format("%s_%04d", codigo, parte);
    }
}
