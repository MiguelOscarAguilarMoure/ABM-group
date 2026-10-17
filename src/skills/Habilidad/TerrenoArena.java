package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

public class TerrenoArena extends Skill {
    protected String nombre = "Arena";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoArena(ESkillType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
