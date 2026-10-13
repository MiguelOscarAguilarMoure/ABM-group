package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Excavaciones extends Skill {
    protected int nivel;
    protected String nombre = "Excavacion";
    protected String descripcion =
            "Aumenta las Rarezas de los tesoros en el terreno";

    public Excavaciones(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}
