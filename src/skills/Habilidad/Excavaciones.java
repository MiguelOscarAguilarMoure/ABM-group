package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

public class Excavaciones extends Skill {
    protected int nivel;

    public Excavaciones(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Amplia en numero de acciones de excarvar
     * @param nivel
     * @return numero de acciones adicionales
     */
    public int efecto() {
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
   /** Acciones extra de excavación (lo suma Archeologist.getAccionesExtra). */
    @Override
    public int getAccionesExtra() {
        return efecto();
    }
}