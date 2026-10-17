package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;

public class Escaneo extends Skill {
    protected int nivel;

    public Escaneo(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
    }

    /**
     * Indica 3 casillas, donde 1 de ellas tiene un tesoro
     * @return
     */
    public int escaneo() {
        return 3;
    }
}

