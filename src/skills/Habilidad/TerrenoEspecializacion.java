package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Base de las habilidades de especialización en un terreno (desierto, agua...).
 * <p>
 * Un arqueólogo solo puede tener una especialización en un terreno: todas las
 * que heredan de esta clase son incompatibles entre sí.
 * </p>
 *
 * @author Miguel Aguilar Moure
 * @version 1.0
 * @see TerrenoArena
 * @see Skill#esIncompatible(Skill)
 */
public abstract class TerrenoEspecializacion extends Skill {

    /**
     * Crea una especialización de terreno.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     */
    protected TerrenoEspecializacion(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }

    /**
     * Incompatible con cualquier otra especialización de terreno.
     *
     * @param otra La habilidad con la que se compara.
     * @return {@code true} si la otra también es una especialización de terreno (y no es esta misma).
     */
    @Override
    public boolean esIncompatible(Skill otra) {
        return otra instanceof TerrenoEspecializacion && otra != this;
    }
}
