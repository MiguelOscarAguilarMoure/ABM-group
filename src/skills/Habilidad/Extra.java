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

    public int getNivel() {
        return nivel;
    }

    /**
     * Añade acciones de tesoros en el terro
     * @param nivel el nivel de la habilidad
     * @return numero de tesoros adicionales
     */

    public int efecto(int nivel) {
        switch (nivel) {
            case 1 -> {return 5;}
            case 2 -> {return 10;}
            case 3 -> {return 15;}
            default -> {
                return 0;
            }
        }
    }
}

