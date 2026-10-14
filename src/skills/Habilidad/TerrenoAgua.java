package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

public class TerrenoAgua extends Skill {
    protected String nombre = "Agua";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoAgua(ESkillType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}

