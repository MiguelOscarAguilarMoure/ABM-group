package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoArena extends Skill {
    protected String nombre = "Arena";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoArena(SkyllType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
