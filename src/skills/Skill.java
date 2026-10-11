package skills;

import java.util.ArrayList;

public class Skill {

    private String nombre;
    private String descripcion;
    private int nivel;
    private ESkillType tipo;
    public static ArrayList<Skill> habilidades = new ArrayList<>();


    /**
     *
     * @param nombre el nombre de la habilidad
     * @param descripcion Utilidad de la habilidad en texto
     * @param nivel grado de mejora de cada habilidad
     * @param tipo Clasificacion de habilidades
     */
    public Skill(String nombre, String descripcion, int nivel, ESkillType tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public ESkillType getTipo() {
        return tipo;
    }

    public void setTipo(ESkillType tipo) {
        this.tipo = tipo;
    }

    public static ArrayList<Skill> getHabilidades() {
        return habilidades;
    }

    public static void setHabilidades(ArrayList<Skill> habilidades) {
        Skill.habilidades = habilidades;
    }
}