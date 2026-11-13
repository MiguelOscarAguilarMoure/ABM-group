package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

import java.util.ArrayList;

public class TerrenoArena extends Skill {
    public TerrenoArena(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }

}

/** aplica el efecto de la habilidad
 *
 * @return coste de excavacion en casillas "Arena"
 */
public int efecto() {
    return 1;
}