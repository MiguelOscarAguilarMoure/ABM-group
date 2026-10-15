package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

import java.util.ArrayList;

public class TerrenoDemoler extends Skill {
    public TerrenoDemoler(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias) {
        super(nombre, descripcion, tipo, dependecias);
    }
}
