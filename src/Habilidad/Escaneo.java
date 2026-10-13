package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Escaneo extends Skill {
    protected int nivel;

    public Escaneo(SkyllType tipo, String nombre, String descripcion, String dependencia, int nivel) {
        super(tipo.Excavacion, nombre, descripcion, dependencia);
        this.nivel = nivel;
    }
}
