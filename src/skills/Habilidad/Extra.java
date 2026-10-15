package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Extra extends Skill {
    protected int nivel;

    public Extra(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias, int nivel) {
        super(nombre, descripcion, tipo, dependecias);
        this.nivel = nivel;
    }
}

