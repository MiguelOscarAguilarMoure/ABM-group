package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Especialista extends Skill {
    protected int nivel;

    public Especialista(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
    }

    public int[] efecto() {
        switch (getNombre()) {
            case "EspecialistaU" -> {
                return new int[]{10, 0};
            }
            case "EspecialistaR" -> {
                return new int[]{0, 10};
            }
            case "EspecialistaM" -> {
                return new int[]{5, 5};
            }
            default -> {
                return new int[]{0, 0};
            }
        }

    }
}