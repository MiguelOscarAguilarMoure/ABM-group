package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

import java.util.ArrayList;
import java.util.Random;


public class Licencia extends Skill {
    protected int nivel;

    public Licencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Recibe el tipo de licencia y devuelve las lineas que hay que ampliar en el terreno
     * [Horizontal o  Vertical]
     * @param nivel
     * @return los numeros a sumar en los vertices
     */
    public int[] efecto(int nivel) {
        switch (nivel) {
            case 1 -> {
                int random = (int) (Math.random() * 2);
                if (random == 1) {
                    return new int[]{2, 0};
                } else {
                    return new int[]{0, 2};
                }
            }

            case 2 -> {
                return new int[]{1, 1};
            }

            default -> {
                return new int[]{0, 0};
            }
        }
    }
}







