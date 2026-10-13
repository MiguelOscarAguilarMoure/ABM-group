package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Extra extends Skill {
    protected int nivel;
    protected String nombre = "Extra";
    protected String descripcion =
            "Tesoros adicionales en el terreno";

    public Extra(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}
