package skills.Habilidad;


import skills.ESkillType;
import skills.Skill;

public class TerrenoDemoler extends Skill {

    protected String nombre = "Demoler";
    protected String descripcion =
            "Permite Destruir estructuras";

    public TerrenoDemoler(ESkillType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
