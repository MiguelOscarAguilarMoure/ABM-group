package skills;

import excavation.ETerreno;
import excavation.TerrainShape;
import java.util.ArrayList;
import java.util.List;
import treasures.ETreasureRarity;

/**
 * Habilidad que puede aprender un arqueólogo.
 * <p>
 * Es abstracta: cada subclase sobrescribe solo el bono que aporta.
 * Los demás bonos devuelven un valor neutro (0, {@code false} o una lista
 * vacía),
 * así el arqueólogo puede sumar los bonos de todas sus habilidades
 * sin saber de qué subclase es cada una (polimorfismo).
 * </p>
 * <p>
 * Las habilidades se crean y se guardan en {@code SkillRepository}.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @author Miguel Aguilar Moure
 * @version 1.0
 * @see commons.repositories.SkillRepository
 * @see people.Archeologist
 */
public abstract class Skill {
    /** Nombre de la habilidad (por ejemplo "Excavación I"). */
    protected String nombre;
    /** Descripción que se muestra al usuario. */
    protected String descripcion;
    /** Tipo de la habilidad (extra, conocimiento, revelación...). */
    protected ESkillType tipo;
    /** Habilidades que hay que tener antes de poder aprender esta. */
    protected ArrayList<Skill> dependencias = new ArrayList<>();

    /**
     * Crea una habilidad sin dependencias.
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
    // Dependencias y Incompatibilidades

    /**
     * Añade una habilidad que hay que tener antes de aprender esta.
     * Se usa en {@code SkillRepository} al crear las habilidades.
     *
     * @param s La habilidad de la que depende.
     */
    public void addDependencia(Skill s) {
        dependencias.add(s);
    }

    /**
     * Indica si esta habilidad es incompatible con otra, es decir, si no se pueden
     * tener las dos.
     * Por defecto ninguna es incompatible; {@code Licencia} y {@code Especialista}
     * lo sobrescriben.
     *
     * @param otra La habilidad con la que se compara.
     * @return {@code true} si no se pueden tener las dos; {@code false} en caso
     *         contrario.
     */
    public boolean esIncompatible(Skill otra) {
        return false;
    }

    // GETTERS & SETTERS

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

    // BONOS
    // Valores neutros: cada subclase sobrescribe solo el bono que aporta.

    /**
     * Tesoros aleatorios extra que añade a la excavación.
     * Lo sobrescribe {@code Extra}.
     *
     * @return Número de tesoros extra (0 por defecto).
     */
    public int getTesorosExtra() {
        return 0;
    }

    /**
     * Tesoros extra de una rareza concreta que añade a la excavación.
     * Lo sobrescribe {@code Conocimiento}.
     *
     * @param rareza La rareza de los tesoros.
     * @return Número de tesoros extra de esa rareza (0 por defecto).
     */
    public int getTesorosExtra(ETreasureRarity rareza) {
        return 0;
    }

    /**
     * Probabilidad extra de que un tesoro sea de una rareza concreta.
     * Lo sobrescribe {@code Especialista}.
     *
     * @param rareza La rareza de los tesoros.
     * @return Puntos porcentuales extra (0 por defecto).
     */
    public int getProbabilidadExtra(ETreasureRarity rareza) {
        return 0;
    }

    /**
     * Acciones de excavación extra.
     * Lo sobrescribe {@code Excavaciones}.
     *
     * @return Número de acciones extra (0 por defecto).
     */
    public int getAccionesExtra() {
        return 0;
    }

    /**
     * Porcentaje extra de acciones de excavación.
     * Lo sobrescribe {@code Experiencia}.
     *
     * @return Porcentaje extra, por ejemplo 20 para un 20 % (0 por defecto).
     */
    public int getPorcentajeAcciones() {
        return 0;
    }

    /**
     * Casillas vacías que se destapan al empezar la excavación.
     * Lo sobrescribe {@code Limpieza}.
     *
     * @return Número de casillas (0 por defecto).
     */
    public int getLimpieza() {
        return 0;
    }

    /**
     * Casillas que se marcan con '?' al empezar la excavación.
     * Lo sobrescribe {@code Escaneo}.
     *
     * @return Número de casillas (0 por defecto).
     */
    public int getSuposicion() {
        return 0;
    }

    /**
     * Indica si la habilidad permite ver cuántos tesoros de cada rareza quedan.
     * Lo sobrescribe {@code Investigacion}.
     *
     * @return {@code true} si es de investigación; {@code false} por defecto.
     */
    public boolean isInvestigacion() {
        return false;
    }

     /**
     * Coste fijo al que esta habilidad deja la excavación de un terreno.
     * Lo sobrescribe {@code TerrenoArena} (Especialización en desierto).
     *
     * @param terreno El terreno que se va a excavar.
     * @return El coste fijo en acciones, o 0 si la habilidad no afecta a ese terreno.
     * @author Miguel Aguilar Moure
     * @Version 1.0
     */
    // !new
    public int getCosteTerreno(ETerreno terreno) {
        return 0;
    }
    /**
     * Número de casillas de un terreno que se cambian por tierra antes de empezar
     * la excavación. Lo sobrescribe {@code TerrenoDemoler} (Especialista en demoliciones).
     *
     * @param terreno El terreno que se cambia.
     * @return Cuántas casillas de ese terreno cambia como máximo (0 por defecto).
     * @author Miguel Aguilar Moure
     * @Version 1.0
     */
        /**
     * Coste fijo al que deja esta habilidad el excavar un terreno.
     * Lo sobrescriben las habilidades de especialización en un terreno.
     *
     * @param tipo El terreno que se va a excavar.
     * @return El coste en acciones, o 0 si la habilidad no modifica el coste de ese terreno.
     */
    public int getCosteExcavar(ETerreno tipo) {
        return 0;
    }

    /**
     * Casillas de un terreno que esta habilidad cambia por tierra antes de empezar
     * la excavación. Lo sobrescriben las habilidades de demolición.
     *
     * @param tipo El terreno que se cambia.
     * @return Número máximo de casillas a cambiar (0 por defecto).
     */
    public int getCasillasCambiadas(ETerreno tipo) {
        return 0;
    }

    /**
     * Formas de terreno que desbloquea al aprenderla (solo las Licencias).
     * Lo sobrescribe {@code Licencia}.
     * 
     * @return Lista de formas nuevas (vacía por defecto).
     */
    public List<TerrainShape> getTerrenos() {
        return new ArrayList<>();
    }

}
