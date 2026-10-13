package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Experiencia extends Skill {
    protected int nivel;
    protected String nombre = "Experiencia";
    protected String descripcion =
            "Proabilidad de que una accion de excavar sea gratis";

    public Experiencia(SkyllType tipo, String dependencia, int nivel) {
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

    public float efecto(Conocimiento habilidad) {
        int nivel = habilidad.getNivel();

        switch (nivel) {
            case 0 -> {
                return 1;
            }
            case 1 -> {
                return 2;
            }
            case 2 -> {
                return 3;
            }
            default -> {
                return 0;
            }

        }

    }
}