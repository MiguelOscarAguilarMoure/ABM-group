package Habilidades;

public abstract class Skill {
    protected SkyllType tipo;
    protected String dependencia;


    public Skill(SkyllType tipo, String dependencia) {
        this.tipo = tipo;
        this.dependencia = dependencia;
    }

    //GETTERS ===========================================================


    public SkyllType getTipo() {
        return tipo;
    }

    public String getDependencia() {
        return dependencia;
    }

    //===================================================================
}
