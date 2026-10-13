package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Especialismo extends Skill {
    protected int nivel;

    public Especialismo(SkyllType tipo, String nombre, String descripcion, String dependencia, int nivel) {
        super(tipo.Tesoro, nombre, descripcion, dependencia);
        this.nivel = nivel;
    }
}
