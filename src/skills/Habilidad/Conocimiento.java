package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Conocimiento extends Skill {
    protected int nivel;

    public Conocimiento(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias, int nivel) {
        super(nombre, descripcion, tipo, dependecias);
        this.nivel = nivel;
    }

    @Override
    public int efecto() {

    }

}