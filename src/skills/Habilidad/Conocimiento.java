package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;
import treasures.ETreasureRarity;


import java.util.ArrayList;

public class Conocimiento extends Skill {
    protected int nivel;

    public Conocimiento(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Devuelve la cantidad de tesoros del tipo de rareza
     * @return [COMMON,UNCOMMON, RARE]
     */
    public int[] efecto() {
        switch (this.nivel) {
            case 1 -> {
                return new int[]{5, 0, 0};
            }
            case 2 -> {
                return new int[]{5, 5, 0};
            }
            case 3 -> {
                return new int[]{0, 5, 2};
            }
            default -> {
                return new int[]{0, 0, 0};
            }
        }
    }
}