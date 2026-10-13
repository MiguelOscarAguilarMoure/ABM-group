package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

public class Limpieza extends Skill {
    protected int nivel;

    protected String nombre = "Limpieza";
    protected String descripcion =
            "Excava automaticamente zonas del terreno";

    public Limpieza(ESkillType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
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

    public int efecto(Limpieza habilidad) {
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
