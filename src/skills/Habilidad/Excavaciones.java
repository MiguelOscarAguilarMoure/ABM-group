package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Excavaciones extends Skill {
    protected int nivel;

    public Excavaciones(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias, int nivel) {
        super(nombre, descripcion, tipo, dependecias);
        this.nivel = nivel;
    }

    /**
     * Amplia en numero de acciones de excarvar
     * @param nivel
     * @return numero de acciones adicionales
     */
    public int efecto(int nivel) {
        switch (nivel) {
            case 1 -> {
                return 5;
            }
            case 2 -> {
                return 10;
            }
            case 3 -> {
                return 15;
            }
            default -> {
                return 0;
            }
        }
    }
}