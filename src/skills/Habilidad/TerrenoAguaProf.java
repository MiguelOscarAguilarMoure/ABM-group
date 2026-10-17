package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

public class TerrenoAguaProf extends Skill {
    protected String nombre = "Agua Profunda";
    protected String descripcion =
            "Permite excarvar en terrenos del tipo de la habilidad";

    public TerrenoAguaProf(ESkillType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
