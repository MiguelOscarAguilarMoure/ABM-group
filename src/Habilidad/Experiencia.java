package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Experiencia extends Skill {
    protected int nivel;

    public Experiencia(SkyllType tipo, String nombre, String descripcion, String dependencia, int nivel) {
        super(tipo.Tesoro, nombre, descripcion, dependencia);
        this.nivel = nivel;
    }
}
