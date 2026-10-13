package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

public class Excavaciones extends Skill {
    protected int nivel;
    protected String nombre = "Excavacion";
    protected String descripcion =
            "Aumenta las Rarezas de los tesoros en el terreno";

    public Excavaciones(ESkillType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }// ======================================================================================
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

    public int efecto(Excavaciones habilidad) {
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