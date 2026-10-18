package skills;

import excavation.TerrainShape;
import java.util.ArrayList;
import java.util.List;
import treasures.ETreasureRarity;

public abstract class Skill {

     protected String nombre;
     protected String descripcion;
    protected ESkillType tipo;
    protected  ArrayList<Skill> dependencias = new ArrayList<>();

    /**
     *
     * @param nombre      nombre de la habilidad
     * @param descripcion descripcion de la habilidad
     * @param tipo        Tipo de habilidad
     */
    public Skill(String nombre, String descripcion, ESkillType tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public void addDependencia(Skill s) {
        dependencias.add(s);
    }

    // Por defecto ninguna es incompatible; Licencia y Especialista lo sobrescriben.
    public boolean esIncompatible(Skill otra) {
        return false;
    }

    // == GETTERS & SETTERS
    // ==================================================================================

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ESkillType getTipo() {
        return tipo;
    }

    public int getTesorosExtra() {
        return 0;
    }

    public int getTesorosExtra(ETreasureRarity rareza) {
        return 0;
    }

    public int getProbabilidadExtra(ETreasureRarity rareza) {
        return 0;
    }

    public int getAccionesExtra() {
        return 0;
    }

    public int getPorcentajeAcciones() {
        return 0;
    }

    public int getLimpieza() {
        return 0;
    }

    public int getSuposicion() {
        return 0;
    }

    public boolean isInvestigacion() {
        return false;
    }

    public List<Skill> getDependencias() {
        return dependencias;
    }

    /** Formas de terreno que añade (solo las Licencias). */
    public List<TerrainShape> getTerrenos() {
        return new ArrayList<>();
    }

   
}
