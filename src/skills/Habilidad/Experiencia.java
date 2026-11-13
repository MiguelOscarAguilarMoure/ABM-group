package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Habilidades de experiencia: Experiencia I y II.
 * <p>
 * Aumentan en un porcentaje las acciones de excavación según su nivel:
 * </p>
 * <ul>
 *   <li>Nivel 1 (Experiencia I): +20 % de acciones.</li>
 *   <li>Nivel 2 (Experiencia II): +30 % de acciones.</li>
 * </ul>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getPorcentajeAcciones()
 */
public class Experiencia extends Skill {
     /** Nivel de la habilidad (1 o 2). */
    protected final int nivel;   // 1 o 2
    /**
     * Crea una habilidad de experiencia.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = I, 2 = II).
     */
    public Experiencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

   /**
     * Porcentaje de aumento de acciones según el nivel.
     *
     * @return 20 o 30 (0 si el nivel no es válido).
     */
    public int efecto() {
        switch (nivel) {
            case 1:
                return 20;
            case 2:
                return 30;
            default:
                return 0;
        }
    }

    
    /**
     * Porcentaje extra de acciones de excavación según el nivel.
     * Lo usa {@code Archeologist.getFactorAcciones()} para multiplicar las acciones.
     *
     * @return Porcentaje extra (20 o 30).
     */
    @Override
    public int getPorcentajeAcciones() {
        return efecto();
    }
}