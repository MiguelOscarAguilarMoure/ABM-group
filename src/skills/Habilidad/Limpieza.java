package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
/**
 * Habilidades de limpieza: Limpieza I y II.
 * <p>
 * Al empezar la excavación destapan casillas vacías al azar (quedan como 'x'),
 * para que el arqueólogo no gaste acciones en ellas:
 * </p>
 * <ul>
 *   <li>Nivel 1 (Limpieza I): 5 casillas.</li>
 *   <li>Nivel 2 (Limpieza II): 10 casillas.</li>
 * </ul>
 * <p>
 * Junto con el Escaneo, como mucho se revela el 25 % de la rejilla.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getLimpieza()
 */

public class Limpieza extends Skill {
    /** Nivel de la habilidad (1 o 2). */
    protected int nivel;

     /**
     * Crea una habilidad de limpieza.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = I, 2 = II).
     */
    public Limpieza(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Casillas a destapar según el nivel.
     *
     * @return 5 o 10 casillas (0 si el nivel no es válido).
     */
    public int efecto() {
        switch (nivel) {
            case 1 -> {
                return 5;
            }
            case 2 -> {
                return 10;
            }
            default -> {
                return 0;
            }
        }
    }
    /**
     * Casillas vacías que se destapan al empezar la excavación, según el nivel.
     * Lo suma {@code Archeologist.getLimpieza()} con el resto de habilidades.
     *
     * @return Número de casillas a destapar.
     */
    @Override
    public int getLimpieza() {
        return efecto();
    }
}
