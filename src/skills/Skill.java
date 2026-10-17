package skills;

import commons.repositories.SkillRepository;
import excavation.TerrainShape;
import java.util.ArrayList;
import java.util.List;
import treasures.ETreasureRarity;

public abstract class Skill {

    final protected String nombre;
    final protected String descripcion;
    final protected ESkillType tipo;
    public ArrayList<Skill> dependecias = new ArrayList<>();

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
        dependecias.add(s);
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

    public ArrayList<Skill> getDependecias() {
        return dependecias;
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
        return dependecias;
    }

    /** Formas de terreno que añade (solo las Licencias). */
    public List<TerrainShape> getTerrenos() {
        return new ArrayList<>();
    }

    // =======================================================================================================
    /** Muestra nombre, descripción, tipo, dependencias e incompatibilidades. */
    public static void mostrarHabilidad(Skill habilidad) {
        System.out.println("------------------ " + habilidad.getNombre() + " ------------------");
        System.out.println(habilidad.getDescripcion());
        System.out.println("Tipo: " + habilidad.getTipo());

        if (!habilidad.getDependencias().isEmpty()) {
            System.out.println("Dependencias:");
            for (Skill d : habilidad.getDependencias()) {
                System.out.println("  -" + d.getNombre());
            }
        }

        List<Skill> incompatibles = new ArrayList<>();
        for (Skill otra : SkillRepository.getTodas()) {
            if (habilidad.esIncompatible(otra)) {
                incompatibles.add(otra);
            }
        }
        if (!incompatibles.isEmpty()) {
            System.out.println("Incompatibilidades:");
            for (Skill otra : incompatibles) {
                System.out.println("  -" + otra.getNombre());
            }
        }
    }
}
