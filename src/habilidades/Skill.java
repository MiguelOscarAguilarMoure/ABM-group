package habilidades;

public abstract class Skill {
    protected ESkillType tipo;
    protected String dependencia;


    public Skill(ESkillType tipo, String dependencia) {
        this.tipo = tipo;
        this.dependencia = dependencia;
    }

    //GETTERS ===========================================================


    public ESkillType getTipo() {
        return tipo;
    }

    public String getDependencia() {
        return dependencia;
    }

    //===================================================================
}