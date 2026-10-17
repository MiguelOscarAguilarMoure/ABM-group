package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Experiencia extends Skill {
    protected int nivel;

    public Experiencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Devuvelve el porcentaje decimal (en Int)
     * @return devuelve un int reflejando el porcentaje "x * 10" (Ej: 2 = 20%)
     */
    public int efecto() {
        switch (this.nivel) {
            case 1 -> {return 2;}
            case 2 -> {return 3;}
            default -> {return 0;}
        }
    }

    public int getPorcentajeAcciones() {
        return 0;
    }

}