package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoOceano extends Skill {

    protected String nombre = "Oceano";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoOceano(SkyllType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}

