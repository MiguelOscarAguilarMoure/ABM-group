package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

public class Especialismo extends Skill {
    protected int nivel;
    protected String nombre = "Especialismo";
    protected String descripcion =
            "Aumenta mucho las Rarezas de los tesoros en el terreno";

    public Especialismo(ESkillType tipo, String dependencia, int nivel) {
        super(tipo.EXCAVATION, dependencia);
        this.nivel = nivel;
    }

    // ======================================================================================
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // ======================================================================================

    /**
     *
     * @param habilidad
     * @return
     */

    public int efecto(Especialismo habilidad) {
        int nivel = habilidad.getNivel();

        switch (nivel) {
            case 0 -> {
                return 2;
            }
            case 1 -> {
                return 4;
            }
            case 2 -> {
                return 6;
            }
            default -> {
                return 0;
            }

        }

    }
}