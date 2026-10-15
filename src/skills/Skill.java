
package skills;

import excavation.TerrainShape;
import java.util.ArrayList;
import java.util.List;
import treasures.ETreasureRarity;

/**
 * Clase base de todas las habilidades.
 *
 * Tiene un método por cada tipo de bono, que devuelve un valor neutro (0, false
 * o lista vacía).
 * Cada subclase sobrescribe solo los bonos que mejora.
 */
public abstract class Skill {

    protected final String nombre;
    protected final String descripcion;
    protected final ESkillType tipo;
    protected final List<Skill> dependencias = new ArrayList<>();
    protected final List<Skill> incompatibilidades = new ArrayList<>();

    public Skill(String nombre, String descripcion, ESkillType tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    // ================================================== RESTRICCIONES

    /**
     * Añade una habilidad de la que depende esta.
     * 
     * @param s La habilidad que hay que tener antes.
     */
    public void addDependencia(Skill s) {
        dependencias.add(s);
    }

    /**
     * Hace incompatibles esta habilidad y otra. Se refleja en las dos.
     * 
     * @param s La habilidad incompatible.
     */
    public void addIncompatibilidad(Skill s) {
        if (!incompatibilidades.contains(s)) {
            incompatibilidades.add(s);
            s.addIncompatibilidad(this);
        }
    }

    // ================================================== BONOS DE TESOROS

    /** Tesoros extra de rareza aleatoria (Extra I, II, III). */
    public int getTesorosExtra() {
        return 0;
    }

    /** Tesoros extra de una rareza concreta (Aprendiz, Experto, Maestro). */
    public int getTesorosExtra(ETreasureRarity rareza) {
        return 0;
    }

    /** Porcentaje extra de probabilidad de una rareza (Especialista U, R, M). */
    public int getProbabilidadExtra(ETreasureRarity rareza) {
        return 0;
    }

    // ================================================== BONOS DE EXCAVACIÓN

    /** Acciones extra (Excavación I, II, III). */
    public int getAccionesExtra() {
        return 0;
    }

    /** Porcentaje extra de acciones (Experiencia I, II). */
    public int getPorcentajeAcciones() {
        return 0;
    }

    /** Casillas vacías que se limpian antes de excavar (Limpieza I, II). */
    public int getLimpieza() {
        return 0;
    }

    /** Número de interrogaciones que pone (Escaneo A, B, C, D). */
    public int getSuposicion() {
        return 0;
    }

    /** Si muestra cuántos tesoros hay de cada rareza (Investigación). */
    public boolean isInvestigacion() {
        return false;
    }

    // ================================================== MODIFICADORAS DE TERRENO

    /** Formas de terreno que añade al arqueólogo (Licencia A, B). */
    public List<TerrainShape> getTerrenos() {
        return new ArrayList<>();
    }

    // ================================================== GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ESkillType getTipo() {
        return tipo;
    }

    public List<Skill> getDependencias() {
        return dependencias;
    }

    public List<Skill> getIncompatibilidades() {
        return incompatibilidades;
    }
}
