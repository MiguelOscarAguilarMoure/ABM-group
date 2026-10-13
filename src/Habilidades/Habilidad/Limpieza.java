package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Limpieza extends Skill {
    protected int nivel;

    public Limpieza(SkyllType tipo, String nombre, String descripcion, String dependencia, int nivel) {
        super(tipo.Excavacion, nombre, descripcion, dependencia);
        this.nivel = nivel;
    }
}
