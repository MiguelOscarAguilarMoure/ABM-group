package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Experiencia I o II: aumenta en un porcentaje las acciones de excavación.
 */
public class Experiencia extends Skill {

    protected final int nivel;   // 1 o 2

    public Experiencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Porcentaje de aumento de acciones según el nivel.
     * @return 20 o 30 (por ciento).
     */
    public int efecto() {
        switch (nivel) {
            case 1:
                return 20;
            case 2:
                return 30;
            default:
                return 0;
        }
    }

    /** Porcentaje de aumento de acciones (lo usa Archeologist.getFactorAcciones). */
    @Override
    public int getPorcentajeAcciones() {
        return efecto();
    }
}