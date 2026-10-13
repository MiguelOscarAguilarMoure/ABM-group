package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoAgua extends Skill {
    protected String nombre = "Agua";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoAgua(SkyllType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
