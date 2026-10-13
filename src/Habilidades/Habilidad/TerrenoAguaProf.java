package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoAguaProf extends Skill {
    protected String nombre = "Agua Profunda";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoAguaProf(SkyllType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
