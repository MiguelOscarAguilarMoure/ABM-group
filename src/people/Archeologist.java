package people;

import excavation.ETerrainSize;
import excavation.TerrainShape;
import excavation.TerrainSize;
import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.List;
import regions.Region;
import skills.Skill;
import treasures.ETreasureRarity;


/**
 * Un arqueólogo de la partida: nivel, experiencia, habilidades, terrenos,
 * especialización y licencias.
 */
public class Archeologist {

    public static final int NIVEL_MAX = 5;
  

    private String nombre;
    private int experiencia;
    private int nivel;
    private ArrayList<Skill> listaHabilidades = new ArrayList<>();
    private ArrayList<TerrainShape> tipoTerreno = new ArrayList<>();
    private Region especializacion = null;
    private ArrayList<String> licencias = new ArrayList<>();

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

    

    public int getExperiencia() {
        return experiencia;
    }

 

    public int getNivel() {
        return nivel;
    }

   

    public ArrayList<Skill> getListaHabilidades() {
        return listaHabilidades;
    }

 

    public ArrayList<TerrainShape> getTipoTerreno() {
        return tipoTerreno;
    }
        /** Códigos de las regiones para las que tiene licencia. */
    public ArrayList<String> getLicencias() {
        return licencias;
    }


    public Region getEspecializacion() {
        return especializacion;
    }

    /**
     * Pone la especialización. Solo se puede en el nivel máximo.
     * 
     * @param especializacion La región elegida.
     * @return true si se ha puesto; false si aún no es nivel máximo.
     */
    public boolean setEspecializacion(Region especializacion) {
        if (nivel < NIVEL_MAX) {
            return false;
        }
        this.especializacion = especializacion;
        return true;
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
    /**
     * Suma experiencia y sube de nivel tantas veces como corresponda.
     * Usa un while porque con mucha experiencia de golpe puede subir más de un
     * nivel.
     * 
     * @param exp La experiencia ganada.
     * @return Cuántos niveles ha subido (cada uno da una habilidad nueva).
     */
    public int addExperiencia(int exp) {
        experiencia += exp;
        int nivelesSubidos = 0;
        while (nivel < NIVEL_MAX && experiencia >= getExpNecesaria()) {
            experiencia -= getExpNecesaria(); // primero se resta, con el nivel actual
            nivel++;
            nivelesSubidos++;
        }
        return nivelesSubidos;
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
     * Comprueba si puede excavar un terreno: necesita el nivel mínimo del tamaño
     * y tener desbloqueada la forma.
     * @param terreno El terreno a comprobar.
     * @return true si puede excavarlo.
     */
    public boolean puedeExcavar(ETerrainSize terreno) {
        return nivel >= terreno.getNivelMinimo()
                && tipoTerreno.contains(terreno.getForma());
    }

    /**
     * Clase para saber si arqueplogo puede aprender una habildad
     * 
     * @param s
     * @return
     */
    public boolean puedeAprender(Skill s) {
        if (listaHabilidades.contains(s)) {
            return false;
        }

        for (Skill dep : s.getDependencias()) {
            if (!listaHabilidades.contains(dep)) {
                return false;
            }
        }
        for (Skill h : listaHabilidades) {
            if (s.esIncompatible(h)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Acciones extra: las de las habilidades + 1 cada 25 de exp si está
     * especializado aquí.
     */
    public int getAccionesExtra(Region r) {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getAccionesExtra();
        }
        if (estaEspecializadoEn(r)) {
            total += experiencia / 25;
        }
        return total;
    }

    /** Porcentaje de acciones extra que dan las habilidades. */
    public int getPorcentajeAcciones() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getPorcentajeAcciones();
        }
        return total;
    }
        /**
     * Factor de aumento de acciones: los porcentajes de las habilidades multiplicados entre sí.
     * Ejemplo: Experiencia I (20 %) -> 1.20.
     */
    public double getFactorAcciones() {
        double factor = 1.0;
        for (Skill s : listaHabilidades) {
            factor *= 1 + s.getPorcentajeAcciones() / 100.0;
        }
        return factor;
    }

    /**
     * Tesoros aleatorios extra: los de las habilidades + 1 cada 50 de exp si está
     * especializado aquí.
     */
    public int getTesorosExtra(Region r) {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getTesorosExtra();
        }
        if (estaEspecializadoEn(r)) {
            total += experiencia / 50;
        }
        return total;
    }

    /**
     * Tesoros extra de una rareza: los de las habilidades y, si está especializado
     * aquí,
     * 1 común cada 50 de exp, 1 infrecuente cada 100 y 1 raro cada 200.
     */
    public int getTesorosExtra(ETreasureRarity rareza, Region r) {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getTesorosExtra(rareza);
        }
        if (estaEspecializadoEn(r)) {
            if (rareza == ETreasureRarity.COMMON) {
                total += experiencia / 50;
            } else if (rareza == ETreasureRarity.UNCOMMON) {
                total += experiencia / 100;
            } else if (rareza == ETreasureRarity.RARE) {
                total += experiencia / 200;
            }
        }
        return total;
    }

    /** Probabilidad extra de encontrar tesoros de una rareza. */
    public int getProbabilidadExtra(ETreasureRarity rareza) {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getProbabilidadExtra(rareza);
        }
        return total;
    }

    /** Bono de limpieza de las habilidades de revelación. */
    public int getLimpieza() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getLimpieza();
        }
        return total;
    }

    /** Bono de suposición de las habilidades de revelación. */
    public int getSuposicion() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getSuposicion();
        }
        return total;
    }

    /** true si alguna de sus habilidades es de investigación. */
    public boolean isInvestigacion() {
        for (Skill s : listaHabilidades) {
            if (s.isInvestigacion()) {
                return true;
            }
        }
        return false;
    }

    /** true si está en nivel máximo y especializado en esa región. */
    private boolean estaEspecializadoEn(Region r) {
        return nivel == NIVEL_MAX && especializacion == r;
    }

    public void addTerrenos(List<TerrainShape> nuevas) {
        for (TerrainShape forma : nuevas) {
            if (!tipoTerreno.contains(forma)) {
                tipoTerreno.add(forma);
            }
        }
    }

    /**
     * Añade una habilidad y, si desbloquea formas de terreno, las añade también.
     * 
     * @param s La habilidad a añadir.
     */

    public void addHabilidad(Skill s) {
        listaHabilidades.add(s);
        addTerrenos(s.getTerrenos()); // las que no son de terreno devuelven una lista vacía
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

    /**
     * Añade la licencia para excavar en una región, si no la tiene ya.
     * 
     * @param codigoRegion El código de la región (por ejemplo "ROM").
     */
    public void addLicencia(String codigoRegion) {
        if (!licencias.contains(codigoRegion)) {
            licencias.add(codigoRegion);
        }
    }

    /**
     * Comprueba si puede excavar en una región: si no pide licencia, siempre;
     * si la pide, solo si la tiene.
     * 
     * @param r La región.
     * @return true si puede excavar en ella.
     */
    

    public void mostrar() {
        System.out.println("-------------------- " + nombre + " --------------------");
        System.out.println("Nivel: " + nivel);

        if (nivel == NIVEL_MAX) {
            if (especializacion != null) {
                System.out.println("Especialización: " + especializacion.getNombre());
            }
            System.out.println("Exp: " + experiencia);
        } else {
            System.out.println("Exp: " + experiencia + "/" + getExpNecesaria());
        }

        System.out.println("Terrenos:");
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
