package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
/**
 * Habilidades de excavación: Excavación I, II y III.
 * <p>
 * Añaden acciones extra a cada excavación según su nivel:
 * </p>
 * <ul>
 *   <li>Nivel 1 (Excavación I): +5 acciones.</li>
 *   <li>Nivel 2 (Excavación II): +10 acciones.</li>
 *   <li>Nivel 3 (Excavación III): +15 acciones.</li>
 * </ul>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getAccionesExtra()
 */
public class Excavaciones extends Skill {
     /** Nivel de la habilidad (1, 2 o 3). */
    protected int nivel;
     /**
     * Crea una habilidad de excavación.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = I, 2 = II, 3 = III).
     */
    public Excavaciones(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Acciones extra según el nivel.
     *
     * @return 5, 10 o 15 acciones (0 si el nivel no es válido).
     */
    public int efecto() {
        switch (nivel) {
            case 1 -> {
                return 5;
            }
            case 2 -> {
                return 10;
            }
            case 3 -> {
                return 15;
            }
            default -> {
                return 0;
            }
        }
    }
   /**
     * Acciones de excavación extra según el nivel.
     * Lo suma {@code Archeologist.getAccionesExtra()} con el resto de habilidades.
     *
     * @return Número de acciones extra.
     */
    @Override
    public int getAccionesExtra() {
        return efecto();
    }
}