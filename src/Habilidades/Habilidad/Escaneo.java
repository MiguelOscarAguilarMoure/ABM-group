package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Escaneo extends Skill {
    protected int nivel;
    protected String nombre = "Escaneo";
    protected String descripcion =
            "Marca casillas con \"?\" indicando que puede haber un tesoro ";

    public Escaneo(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}
