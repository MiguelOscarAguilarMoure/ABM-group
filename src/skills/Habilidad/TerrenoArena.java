package skills.Habilidad;

import excavation.ETerreno;
import skills.ESkillType;

/**
 * Habilidad "Especialización en desierto".
 * <p>
 * Estos arqueólogos están acostumbrados a la arena, por lo que su coste de
 * excavación en ese terreno se fija en {@value #COSTE_ARENA}.
 * Es incompatible con cualquier otra especialización en un terreno.
 * </p>
 *
 * @author Miguel Aguilar Moure
 * @since Alberto Ameneiros
 * @version 1.0
 * @see TerrenoEspecializacion
 * @see skills.Skill#getCosteTerreno(ETerreno)
 */
public class TerrenoArena extends TerrenoEspecializacion {
    /** Coste fijo de excavar arena con esta habilidad. */
    private static final int COSTE_ARENA = 1;

    /**
     * Crea la habilidad de especialización en desierto.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     */
    public TerrenoArena(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }

}
