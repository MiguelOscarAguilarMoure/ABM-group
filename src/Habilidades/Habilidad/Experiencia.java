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
}
