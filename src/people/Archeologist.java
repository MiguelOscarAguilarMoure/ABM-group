package people;

import excavation.ETerrainSize;
import habilidades.Skill;
import regiones.Region;
import tesoros.ETreasureRarity;

import java.util.ArrayList;
import java.util.List;

/**
 * Arqueólogo del sistema: nivel, experiencia, habilidades y terrenos disponibles.
 * Las validaciones se resuelven con lógica: los métodos devuelven boolean o
 * normalizan el valor, y no lanzan excepciones.
 */
public class Archeologist {

    public static final int NIVEL_MAX = 5;
    private static final int EXP_BASE = 50;
    private static final int EXP_POR_NIVEL = 50;
    private static final String NOMBRE_POR_DEFECTO = "Sin nombre";

    private String nombre;
    private int experiencia;
    private int nivel;
    private final ArrayList<Skill> listaHabilidades;
    private final ETreasureRarity rareza;
    /** Todos los terrenos desbloqueados (por nivel y por habilidades). */
    private final ArrayList<ETerrainSize> tipoTerreno;
    private Region especializacion;

    /**
     * Crea un arqueólogo nuevo: nivel 0, sin experiencia y solo con el terreno pequeño cuadrado.
     */
    public Archeologist(String nombre, ETreasureRarity rareza) {
        this(nombre, 0, 0, null, rareza, null, null);
    }

    /**
     * Constructor completo, pensado también para cargar una partida guardada.
     * Los valores incorrectos se corrigen: nombre vacío, rareza nula (común),
     * experiencia negativa (0) y nivel fuera de rango (se limita a 0-5).
     * La especialización solo se conserva si el nivel es el máximo.
     */
    public Archeologist(String nombre, int experiencia, int nivel,
            List<Skill> listaHabilidades, ETreasureRarity rareza,
            List<ETerrainSize> tipoTerreno, Region especializacion) {

        this.nombre = esNombreValido(nombre) ? nombre.trim() : NOMBRE_POR_DEFECTO;
        this.rareza = (rareza != null) ? rareza : ETreasureRarity.COMMON;
        this.experiencia = Math.max(0, experiencia);
        this.nivel = Math.max(0, Math.min(NIVEL_MAX, nivel));
        this.especializacion = (this.nivel == NIVEL_MAX) ? especializacion : null;

        this.listaHabilidades = new ArrayList<>();
        if (listaHabilidades != null) {
            for (Skill skill : listaHabilidades) {
                if (skill != null) {
                    this.listaHabilidades.add(skill);
                }
            }
        }

        this.tipoTerreno = new ArrayList<>();
        addTerrenos(tipoTerreno);
        asegurarTerrenosBase();
    }

    // ===================== VALIDACIÓN =====================

    /**
     * Permite al menú comprobar un nombre antes de usarlo.
     *
     * @param nombre El nombre a comprobar.
     * @return true si no es nulo ni está vacío.
     */
    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    // ===================== TERRENOS =====================

    /**
     * Garantiza que tenga el terreno cuadrado de cada tamaño al que su nivel da acceso
     * (pequeño: 0, mediano: 2, grande: 4).
     */
    private void asegurarTerrenosBase() {
        for (ETerrainSize terreno : ETerrainSize.values()) {
            if (terreno.isCuadrado()
                    && nivel >= terreno.getNivelMinimo()
                    && !tipoTerreno.contains(terreno)) {
                tipoTerreno.add(terreno);
            }
        }
    }

    /**
     * Añade terrenos desbloqueados, por ejemplo por una habilidad modificadora de terreno.
     * Ignora la lista nula, los elementos nulos y los que ya posee.
     *
     * @param nuevos Los terrenos a añadir.
     * @return El número de terrenos realmente añadidos.
     */
    public int addTerrenos(List<ETerrainSize> nuevos) {
        int anadidos = 0;
        if (nuevos == null) {
            return anadidos;
        }
        for (ETerrainSize terreno : nuevos) {
            if (terreno != null && !tipoTerreno.contains(terreno)) {
                tipoTerreno.add(terreno);
                anadidos++;
            }
        }
        return anadidos;
    }

    /** @return Copia de todos los terrenos desbloqueados, sin filtrar por nivel. */
    public List<ETerrainSize> getTipoTerreno() {
        return new ArrayList<>(tipoTerreno);
    }

    /**
     * Terrenos que puede excavar ahora mismo: desbloqueados Y acordes a su nivel.
     *
     * @return Lista de terrenos excavables.
     */
    public List<ETerrainSize> getTerrenosExcavables() {
        List<ETerrainSize> excavables = new ArrayList<>();
        for (ETerrainSize terreno : tipoTerreno) {
            if (nivel >= terreno.getNivelMinimo()) {
                excavables.add(terreno);
            }
        }
        return excavables;
    }

    /**
     * @param terreno El terreno a comprobar.
     * @return true si lo tiene desbloqueado y su nivel es suficiente.
     */
    public boolean puedeExcavar(ETerrainSize terreno) {
        return terreno != null
                && tipoTerreno.contains(terreno)
                && nivel >= terreno.getNivelMinimo();
    }

    // ===================== EXPERIENCIA Y NIVEL =====================

    /** @return La experiencia necesaria para subir desde el nivel actual. */
    public int getExpNecesaria() {
        return EXP_BASE + EXP_POR_NIVEL * nivel;
    }

    public boolean esNivelMaximo() {
        return nivel >= NIVEL_MAX;
    }

    /**
     * Suma experiencia y sube de nivel tantas veces como corresponda.
     * En nivel máximo la experiencia sigue acumulándose (bonos de especialización).
     *
     * @param exp Experiencia a sumar. Si es negativa no se hace nada.
     * @return El número de niveles subidos; cada uno otorga una habilidad nueva.
     */
    public int addExperiencia(int exp) {
        if (exp < 0) {
            return 0;
        }
        experiencia += exp;

        int nivelesSubidos = 0;
        while (nivel < NIVEL_MAX && experiencia >= getExpNecesaria()) {
            experiencia -= getExpNecesaria();
            nivel++;
            nivelesSubidos++;
        }
        if (nivelesSubidos > 0) {
            asegurarTerrenosBase();
        }
        return nivelesSubidos;
    }

    // ===================== HABILIDADES =====================

    /**
     * Indica si puede aprender una habilidad.
     * Pendiente: dependencias e incompatibilidades cuando Skill las exponga.
     */
    public boolean puedeAprender(Skill skill) {
        return skill != null && !listaHabilidades.contains(skill);
    }

    /**
     * Añade una habilidad si puede aprenderla.
     *
     * @param skill La habilidad a añadir.
     * @return true si se añadió; false si es nula o no puede aprenderla.
     */
    public boolean addHabilidad(Skill skill) {
        if (!puedeAprender(skill)) {
            return false;
        }
        listaHabilidades.add(skill);
        return true;
    }

    public List<Skill> getListaHabilidades() {
        return new ArrayList<>(listaHabilidades);
    }

    // ===================== LICENCIA =====================

    /** @return 10 por nivel, más 10 si es infrecuente o 20 si es raro. */
    public int getPuntosLicencia() {
        int puntos = 10 * nivel;
        if (rareza == ETreasureRarity.UNCOMMON) {
            puntos += 10;
        } else if (rareza == ETreasureRarity.RARE) {
            puntos += 20;
        }
        return puntos;
    }

    // ===================== GETTERS / SETTERS =====================

    public static int getNivelMax() {
        return NIVEL_MAX;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre El nuevo nombre.
     * @return true si se cambió; false si el nombre es nulo o está vacío.
     */
    public boolean setNombre(String nombre) {
        if (!esNombreValido(nombre)) {
            return false;
        }
        this.nombre = nombre.trim();
        return true;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public ETreasureRarity getRareza() {
        return rareza;
    }

    public Region getEspecializacion() {
        return especializacion;
    }

    /**
     * Establece la especialización, que solo es posible en el nivel máximo.
     *
     * @param region La región elegida.
     * @return true si se estableció; false si no es nivel máximo o la región es nula.
     */
    public boolean setEspecializacion(Region region) {
        if (!esNivelMaximo() || region == null) {
            return false;
        }
        this.especializacion = region;
        return true;
    }
}