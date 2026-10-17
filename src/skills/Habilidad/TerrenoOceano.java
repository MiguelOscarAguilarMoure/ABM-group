package skills.Habilidad;

import skills.Skill;
import skills.ESkillType;

public class TerrenoOceano extends Skill {

    protected String nombre = "Oceano";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoOceano(ESkillType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}

