package Habilidades;

public abstract class Skill {
    protected SkyllType tipo;
    protected String nombre;
    protected String descripcion;
    protected String dependencia;


    /**
     * Constructor de la clase
     * @param tipo
     * @param nombre
     * @param descripcion
     * @param dependencia
     */
    public Skill(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dependencia = dependencia;
    }

    //GETTERS ===========================================================


    public SkyllType getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDependencia() {
        return dependencia;
    }

    //===================================================================
}
