package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Limpieza extends Skill {
    protected int nivel;

    public Limpieza(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    public int efecto(int nivel) {
        switch (nivel) {
            case 1 -> {
                return 5;
            }
            case 2 -> {
                return 10;
            }
            default -> {
                return 0;
            }
        }
    }
}