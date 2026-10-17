package people;

import excavation.TerrainShape;
import excavation.TerrainSize;
import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.List;
import regions.Region;
import skills.Skill;

public class Archeologist {
    public static final int NIVEL_MAX = 5;
    private String nombre;
    private int experiencia;
    private int nivel;
    private ArrayList<Skill> listaHabilidades = new ArrayList<>();
    private ArrayList<TerrainShape> tipoTerreno = new ArrayList<>();
    private Region especializacion = null;

    public Archeologist(String nombre, int experiencia, int nivel, ArrayList<Skill> listaHabilidades,
            ArrayList<TerrainShape> tipoTerreno, Region especializacion) {
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.listaHabilidades = listaHabilidades;
        this.tipoTerreno = tipoTerreno;
        this.especializacion = especializacion;
    }

    /**
     * Crea un arqueólogo nuevo: nivel 0, sin experiencia, sin habilidades,
     * solo con el terreno cuadrado y sin especialización.
     * 
     * @param nombre El nombre del arqueólogo.
     */
    public Archeologist(String nombre) {
        this(nombre, 0, 0, new ArrayList<>(),
                new ArrayList<>(List.of(TerrainShape.SQUARE)), null);
    }

    public static int getNivelMax() {
        return NIVEL_MAX;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public ArrayList<Skill> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(ArrayList<Skill> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public ArrayList<TerrainShape> getTipoTerreno() {
        return tipoTerreno;
    }

    public void setTipoTerreno(ArrayList<TerrainShape> tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    public Region getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(Region especializacion) {
        this.especializacion = especializacion;
    }

    /**
     * meteodo para obtener la experiencia
     * 
     * @return
     */
    public int getExpNecesaria() {
        return 50 + 50 * nivel;
    }

    /**
     * Metodo para añadir la experiencia
     * 
     * @param exp
     * @return
     */
    public boolean addExperiencia(int exp) {
        experiencia += exp;
        if (nivel < NIVEL_MAX && experiencia >= getExpNecesaria()) {
            experiencia -= getExpNecesaria();
            nivel++;
            return true;
        }
        return false;
    }

    /**
     * metodo para obtener los distintos tamñaos segunb el nivel
     * 
     * @return
     */
    public List<TerrainSize> getTamaños() {
        List<TerrainSize> tamaños = new ArrayList<>();
        tamaños.add(TerrainSize.SMALL);
        if (nivel >= 2)
            tamaños.add(TerrainSize.MEDIUM);
        if (nivel >= 4)
            tamaños.add(TerrainSize.LARGE);
        return tamaños;
    }

    /**
     * Añade una habilidad y, si desbloquea formas de terreno, las añade también.
     * 
     * @param s La habilidad a añadir.
     */

    public void addHabilidad(Skill s) {
        // necesito la licencia para este metetodo
        listaHabilidades.add(s);

    }

    /**
     * Calcula los puntos que da licenciar al arqueólogo.
     * 
     * @return 10 por nivel, más 10 si es infrecuente o 20 si es raro.
     */
    public int getPuntosLicencia() {
        int puntos = 10 * nivel;

        int iniciales = listaHabilidades.size() - nivel;

        if (iniciales == 1) {
            puntos += 10;
        } else if (iniciales >= 2) {
            puntos += 20;
        }
        return puntos;
    }

    public void mostrar() {
        System.out.println("-------------------" + nombre + "-------------------");
        System.out.println("Nivel " + nivel);

        if (nivel == NIVEL_MAX) {
            String region = (especializacion != null) ? especializacion.getNombre() : "Sin especialización";
            System.out.println("Especialización: " + region);
            System.out.println("Exp " + experiencia);

        } else {
            System.out.println("Exp " + experiencia + " / " + getExpNecesaria());
        }
        System.out.println("Terrenos ");
        for (TerrainSize t : getTamaños()) {
            System.out.println("  -" + UtilsHelper.ucfirst(t.toString()));
        }
        if (!listaHabilidades.isEmpty()) {
            System.out.println("Habilidades:");
            for (Skill s : listaHabilidades) {
                System.out.println("  -" + s.getNombre() + ":");
                System.out.println("   " + s.getDescripcion());
            }
        }
    }
}
