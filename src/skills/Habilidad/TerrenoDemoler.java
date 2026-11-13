package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

import java.util.ArrayList;

public class TerrenoDemoler extends Skill {
    public TerrenoDemoler(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }
}

/** aplica el efecto de la habilidad
 *
 * @return numero de piedras que han de romperse
 */
public int efecto() {
    return 5;
}