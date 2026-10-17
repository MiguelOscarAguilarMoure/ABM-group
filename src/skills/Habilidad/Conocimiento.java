package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;


import java.util.ArrayList;

public class Conocimiento extends Skill {
    protected int nivel;

    public Conocimiento(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

}