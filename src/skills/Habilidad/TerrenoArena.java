package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

import java.util.ArrayList;

public class TerrenoArena extends Skill {
    public TerrenoArena(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias) {
        super(nombre, descripcion, tipo, dependecias);
    }
}
