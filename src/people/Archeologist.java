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
 * Arqueólogo de la partida.
 * <p>
 * Guarda su nivel, experiencia, habilidades, formas de terreno desbloqueadas,
 * especialización y licencias de región, y aplica las reglas del juego:
 * subir de nivel, aprender habilidades y calcular los bonos para la excavación.
 * </p>
 * <p>
 * La rareza con la que se contrató no se guarda: se deduce de sus habilidades
 * (las que tiene menos una por nivel).
 * </p>
 *
 * @author Borja Abal Dios
 * @author Miguel Aguilar
 * @version 1.0
 * @see skills.Skill
 * @see regions.Region
 */
public class Archeologist {

    /** Nivel máximo que puede alcanzar un arqueólogo. */
    public static final int NIVEL_MAX = 5;

    /** Nombre del arqueólogo. */
    private String nombre;
    /** Experiencia acumulada en el nivel actual (en nivel máximo, la total). */
    private int experiencia;
    /** Nivel actual (de 0 a {@link #NIVEL_MAX}). */
    private int nivel;
    /** Habilidades aprendidas. */
    private ArrayList<Skill> listaHabilidades = new ArrayList<>();
    /** Formas de terreno desbloqueadas (empieza solo con el cuadrado). */
    private ArrayList<TerrainShape> tipoTerreno = new ArrayList<>();
    /**
     * Región en la que está especializado (solo en nivel máximo; {@code null} si no
     * tiene).
     */
    private Region especializacion = null;
    /** Códigos de las regiones para las que tiene licencia. */
    private ArrayList<String> licencias = new ArrayList<>();

    /**
     * Crea un arqueólogo con todos sus datos (útil para cargar una partida
     * guardada).
     *
     * @param nombre           Nombre del arqueólogo.
     * @param experiencia      Experiencia acumulada.
     * @param nivel            Nivel actual.
     * @param listaHabilidades Habilidades aprendidas.
     * @param tipoTerreno      Formas de terreno desbloqueadas.
     * @param especializacion  Región de especialización ({@code null} si no tiene).
     */
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

    // NIVEL Y EXPERIENCIA

    /**
     * Experiencia necesaria para subir al siguiente nivel.
     *
     * @return 50 más 50 por cada nivel actual.
     */
    public int getExpNecesaria() {
        return 50 + 50 * nivel;
    }

    /**
     * Suma experiencia y sube de nivel tantas veces como corresponda.
     * Usa un {@code while} porque con mucha experiencia de golpe puede subir más de
     * un nivel.
     * En el nivel máximo la experiencia sigue acumulándose para los bonos de
     * especialización.
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

    // TERRENOS
    /**
     * Tamaños de terreno que puede excavar según su nivel.
     *
     * @return Pequeño siempre, mediano desde el nivel 2 y grande desde el nivel 4.
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
     * 
     * @param terreno El terreno a comprobar.
     * @return true si puede excavarlo.
     */
    public boolean puedeExcavar(ETerrainSize terreno) {
        return nivel >= terreno.getNivelMinimo()
                && tipoTerreno.contains(terreno.getForma());
    }

    /**
     * Añade formas de terreno que aún no tenga.
     *
     * @param nuevas Las formas a añadir.
     */
    public void addTerrenos(List<TerrainShape> nuevas) {
        for (TerrainShape forma : nuevas) {
            if (!tipoTerreno.contains(forma)) {
                tipoTerreno.add(forma);
            }
        }
    }

    // HABILIDADES
    /**
     * Comprueba si puede aprender una habilidad.
     *
     * @param s La habilidad a comprobar.
     * @return {@code true} si no la tiene ya, tiene todas sus dependencias
     *         y ninguna habilidad incompatible con ella.
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
     * Añade una habilidad y, si desbloquea formas de terreno (Licencias), las añade
     * también.
     *
     * @param s La habilidad a añadir.
     */
    public void addHabilidad(Skill s) {
        listaHabilidades.add(s);
        addTerrenos(s.getTerrenos()); // las que no son de terreno devuelven una lista vacía
    }
    // BONOS
    // Cada método recorre las habilidades y suma el bono de cada una.
    // Los que reciben la región suman además el bono de especialización.

    /**
     * Acciones extra para la excavación.
     *
     * @param r La región donde se excava.
     * @return Las de las habilidades, más 1 cada 25 de experiencia si está
     *         especializado en esa región.
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

    /**
     * Porcentaje de acciones extra que dan las habilidades, sumado.
     *
     * @return La suma de los porcentajes (por ejemplo 20 para un 20 %).
     */
    public int getPorcentajeAcciones() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getPorcentajeAcciones();
        }
        return total;
    }

    /**
     * Factor por el que se multiplican las acciones: los porcentajes de las
     * habilidades
     * multiplicados entre sí. Por ejemplo, Experiencia I (20 %) da 1.20.
     *
     * @return El factor (1.0 si no tiene ninguna habilidad de experiencia).
     */
    public double getFactorAcciones() {
        double factor = 1.0;
        for (Skill s : listaHabilidades) {
            factor *= 1 + s.getPorcentajeAcciones() / 100.0;
        }
        return factor;
    }

    /**
     * Tesoros aleatorios extra para la excavación.
     *
     * @param r La región donde se excava.
     * @return Los de las habilidades, más 1 cada 50 de experiencia si está
     *         especializado en esa región.
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
     * Tesoros extra de una rareza concreta para la excavación.
     * Si está especializado en esa región suma además 1 común cada 50 de
     * experiencia,
     * 1 infrecuente cada 100 y 1 raro cada 200.
     *
     * @param rareza La rareza de los tesoros.
     * @param r      La región donde se excava.
     * @return El número de tesoros extra de esa rareza.
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

    /**
     * Probabilidad extra de que un tesoro sea de una rareza concreta.
     *
     * @param rareza La rareza de los tesoros.
     * @return Los puntos porcentuales extra de todas sus habilidades.
     */
    public int getProbabilidadExtra(ETreasureRarity rareza) {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getProbabilidadExtra(rareza);
        }
        return total;
    }

    /**
     * Casillas vacías que se destapan al empezar la excavación (Limpieza).
     *
     * @return La suma de todas sus habilidades de limpieza.
     */
    public int getLimpieza() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getLimpieza();
        }
        return total;
    }

    /**
     * Casillas que se marcan con '?' al empezar la excavación (Escaneo).
     *
     * @return La suma de todos sus escaneos.
     */

    public int getSuposicion() {
        int total = 0;
        for (Skill s : listaHabilidades) {
            total += s.getSuposicion();
        }
        return total;
    }

    /**
     * Indica si puede ver cuántos tesoros de cada rareza quedan en la excavación.
     *
     * @return {@code true} si tiene la habilidad de Investigación.
     */
    public boolean isInvestigacion() {
        for (Skill s : listaHabilidades) {
            if (s.isInvestigacion()) {
                return true;
            }
        }
        return false;
    }

    // LICENCIAR

    /**
     * Calcula los puntos que da licenciar al arqueólogo.
     * La rareza se deduce de las habilidades iniciales: las que tiene menos una por nivel.
     *
     * @return 10 por nivel, más 10 si era infrecuente o 20 si era raro.
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
    public boolean puedeExcavarEn(Region r) {
        return !r.necesitaLicencia() || licencias.contains(r.getCodigo());
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
    // AUXILIARES

    /**
     * Comprueba si los bonos de especialización se aplican en una región.
     *
     * @param r La región donde se excava.
     * @return {@code true} si está en nivel máximo y especializado en esa región.
     */
    private boolean estaEspecializadoEn(Region r) {
        return nivel == NIVEL_MAX && especializacion == r;
    }

}
