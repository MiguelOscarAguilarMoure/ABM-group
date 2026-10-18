package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

public class Limpieza extends Skill {
    protected int nivel;

    public Limpieza(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    public int efecto() {
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
   /** Casillas vacías a destapar (lo suma Archeologist.getLimpieza). */
    @Override
    public int getLimpieza() {
        return efecto();
    }
}
