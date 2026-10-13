package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Especialismo extends Skill {
    protected int nivel;
    protected String nombre = "Especialismo";
    protected String descripcion =
            "Aumenta mucho las Rarezas de los tesoros en el terreno";

    public Especialismo(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}
