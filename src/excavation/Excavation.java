package excavation;

import excavation.utils.ExcavationConsoleUI;
import excavation.utils.ExcavationSetupHelper;
import excavation.utils.RejillaExcavacion;
import excavation.utils.TerrainHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import people.Archeologist;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.Treasure;

/**
 * Representa una excavación dentro del juego.
 * <p>
 * Actúa como fachada y coordinador principal de la partida: valida las
 * condiciones
 * de acceso, delega la construcción del tablero y el reparto inicial en el
 * paquete
 * {@code excavation.utils}, y controla las reglas de turno, acciones y
 * puntuación.
 * </p>
 *
 * @author Miguel Aguilar
 * @author Borja Abal
 * @version 2.1
 * @since 2026-10-18
 */
public class Excavation {

    /** Generador compartido para la preparación aleatoria de las excavaciones. */
    private static final Random RANDOM = new Random();

    /** Nombre del tamaño de terreno pequeño. */
    private static final String PEQUENO = "Pequeño";

    /** Nombre del tamaño de terreno mediano. */
    private static final String MEDIANO = "Mediano";

    /** Nombre del tamaño de terreno grande. */
    private static final String GRANDE = "Grande";

    /** Proporción base de casillas usada para calcular las acciones. */
    private static final double ACCIONES_MIN = 0.25;

    /** Proporción máxima de casillas usada para limitar las acciones. */
    private static final double ACCIONES_MAX = 0.75;

    /** Configuración de tamaño y dimensiones del terreno. */
    private final ETerrainSize terreno;

    /** Región de la que proceden los tesoros de la excavación. */
    private final Region region;

    /** Arqueólogo que participa en la excavación. */
    private final Archeologist arqueologo;

    /** Rejilla que almacena el estado y la representación de las casillas. */
    private final RejillaExcavacion rejilla;

    /** Tesoros enterrados, indexados por casilla. */
    private final Map<Integer, Treasure> tesoros;

    /** Códigos completos de los tesoros encontrados. */
    private final List<String> codigosEncontrados;

    /** Acciones que aún puede realizar el arqueólogo. */
    private int acciones;

    /** Tesoros que todavía no se han encontrado. */
    private int tesorosRestantes;

    /** Puntos obtenidos al encontrar tesoros. */
    private int puntos;

    /**
     * Crea una excavación para el terreno, la región y el arqueólogo indicados.
     * Inicializa la rejilla, coloca los tesoros, calcula las acciones disponibles
     * y aplica las habilidades iniciales del arqueólogo.
     *
     * @param terreno    configuración y dimensiones del terreno.
     * @param region     región cuyos tesoros pueden aparecer en la excavación.
     * @param arqueologo arqueólogo que realizará la excavación.
     * @throws IllegalArgumentException si algún parámetro es null, el arqueólogo
     *                                  no puede excavar el terreno o no tiene
     *                                  licencia para la región.
     */
    public Excavation(ETerrainSize terreno, Region region, Archeologist arqueologo) {
        if (terreno == null || region == null || arqueologo == null) {
            throw new IllegalArgumentException("El terreno, la región y el arqueólogo no pueden ser nulos.");
        }
        if (!arqueologo.puedeExcavarEn(region)) {
            throw new IllegalArgumentException(arqueologo.getNombre() + " no tiene licencia para excavar en "
                    + region.getNombre() + ".");
        }
        if (!arqueologo.puedeExcavar(terreno)) {
            throw new IllegalArgumentException(arqueologo.getNombre() + " no puede excavar el terreno "
                    + terreno.getTamanho() + " (" + terreno.getNombre() + ").");
        }

        this.terreno = terreno;
        this.region = region;
        this.arqueologo = arqueologo;

        // En ETerrainSize "AxB" es ancho x alto: A = columnas (letras), B = filas
        // (números).
        int numColumnas = terreno.getFil();
        int numFilas = terreno.getCol();
        int totalCasillas = numColumnas * numFilas;

        List<ETerreno> capaTerrenos = TerrainHelper.generarCapaTerrenos(region, totalCasillas, RANDOM);
        this.rejilla = new RejillaExcavacion(numColumnas, numFilas, capaTerrenos);
        this.tesoros = ExcavationSetupHelper.generarTesoros(terreno, region, arqueologo, totalCasillas, RANDOM);
        this.tesorosRestantes = this.tesoros.size();
        this.codigosEncontrados = new ArrayList<>();

        this.acciones = calcularAcciones();
        ExcavationSetupHelper.aplicarHabilidadesIniciales(rejilla, tesoros, arqueologo, RANDOM);
    }

    // ===== Excavar ========================================================

    /**
     * Excava la posición indicada consumiendo las acciones correspondientes al
     * terreno según las habilidades del arqueólogo. Si el terreno tiene varios
     * niveles (por ejemplo, piedra a tierra), reduce el nivel sin revelar aún el
     * contenido.
     *
     * @param posicion posición que se desea excavar.
     * @return true si la casilla contenía un tesoro; false si estaba vacía o solo
     *         se ha roto un nivel del terreno.
     * @throws IllegalArgumentException si la posición es null, no pertenece a la
     *                                  rejilla o ya se había excavado.
     * @throws IllegalStateException    si la excavación ya ha finalizado.
     */
    public boolean excavar(Position posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        if (isFinalizada()) {
            throw new IllegalStateException("La excavación ya ha terminado.");
        }

        int indice = rejilla.getIndice(posicion);
        if (rejilla.estaExcavada(indice)) {
            throw new IllegalArgumentException("ya está excavada.");
        }

        ETerreno tipo = rejilla.getTerreno(indice);
        acciones = Math.max(0, acciones - getCoste(tipo));

        if (tipo.tieneSiguiente()) {
            rejilla.bajarNivel(indice, tipo.getSiguiente());
            return false;
        }

        String simboloOculto = rejilla.obtenerSimboloOculto(indice, tesoros);
        rejilla.resolverCasilla(indice, simboloOculto);

        Treasure tesoro = tesoros.get(indice);
        if (tesoro == null) {
            return false;
        }

        tesorosRestantes--;
        puntos += tesoro.getPuntos();
        codigosEncontrados.add(tesoro.getCodigoConParte());
        return true;
    }

    /**
     * Calcula las acciones según el tamaño de la rejilla y las bonificaciones del
     * arqueólogo, con un mínimo de una acción y un límite basado en el 75 % de las
     * casillas.
     *
     * @return número de acciones iniciales.
     */
    private int calcularAcciones() {
        int total = rejilla.getTotalCasillas();
        int extra = arqueologo.getAccionesExtra(region);
        double factor = arqueologo.getFactorAcciones();
        int calculadas = (int) Math.round((total * ACCIONES_MIN + extra) * factor);
        int maximo = (int) (total * ACCIONES_MAX);
        return Math.max(1, Math.min(calculadas, maximo));
    }

    /**
     * Consulta al arqueólogo el coste de excavar un terreno asegurando un mínimo de
     * 1.
     *
     * @param tipo terreno que se va a excavar.
     * @return acciones que cuesta excavar dicho terreno (1 o más).
     */
    private int getCoste(ETerreno tipo) {
        return Math.max(1, arqueologo.getCosteExcavar(tipo));
    }

    // ===== Estado =========================================================

    /**
     * Comprueba si la excavación ha terminado por falta de acciones o porque ya
     * no quedan tesoros por encontrar.
     *
     * @return true si la partida ha finalizado.
     */
    public boolean isFinalizada() {
        return acciones <= 0 || tesorosRestantes <= 0;
    }

    /**
     * Devuelve el terreno actual de una casilla.
     *
     * @param posicion posición de la casilla.
     * @return terreno actual en dicha posición.
     * @throws IllegalArgumentException si la posición es null o está fuera de
     *                                  rango.
     */
    public ETerreno getTerreno(Position posicion) {
        return rejilla.getTerreno(posicion);
    }

    /**
     * Cuenta las casillas que tienen actualmente el terreno indicado.
     *
     * @param buscado terreno que se desea contar.
     * @return número de casillas con ese terreno.
     * @throws IllegalArgumentException si el terreno buscado es null.
     */
    public int contarTerreno(ETerreno buscado) {
        return rejilla.contarTerreno(buscado);
    }

    /**
     * Devuelve el número de acciones que todavía se pueden realizar.
     *
     * @return acciones restantes.
     */
    public int getAccionesRestantes() {
        return acciones;
    }

    /**
     * Devuelve el número total de tesoros que aún no se han encontrado.
     *
     * @return tesoros restantes.
     */
    public int getTesorosRestantes() {
        return tesorosRestantes;
    }

    /**
     * Cuenta los tesoros todavía sin excavar de la rareza indicada.
     *
     * @param rareza rareza que se desea consultar.
     * @return cantidad de tesoros restantes de esa rareza.
     * @throws IllegalArgumentException si la rareza es null.
     */
    public int getTesorosRestantes(ETreasureRarity rareza) {
        if (rareza == null) {
            throw new IllegalArgumentException("La rareza no puede ser nula.");
        }
        int cantidad = 0;
        for (Integer indice : tesoros.keySet()) {
            Treasure tesoro = tesoros.get(indice);
            if (tesoro != null && tesoro.getRarezaTesoro() == rareza && !rejilla.estaExcavada(indice)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Cuenta los tesoros de la rareza indicada que se colocaron al inicio.
     *
     * @param rareza rareza que se desea consultar.
     * @return cantidad total de tesoros de esa rareza en la rejilla.
     * @throws IllegalArgumentException si la rareza es null.
     */
    public int getTotalTesoros(ETreasureRarity rareza) {
        if (rareza == null) {
            throw new IllegalArgumentException("La rareza no puede ser nula.");
        }
        int cantidad = 0;
        for (Treasure tesoro : tesoros.values()) {
            if (tesoro != null && tesoro.getRarezaTesoro() == rareza) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve cuántas casillas quedan por excavar.
     *
     * @return número de posiciones sin excavar.
     */
    public int getPosicionesRestantes() {
        return rejilla.getCavarRestantes();
    }

    /**
     * Devuelve los puntos obtenidos al encontrar tesoros.
     *
     * @return puntuación acumulada durante la excavación.
     */
    public int getPuntosObtenidos() {
        return puntos;
    }

    /**
     * Calcula la experiencia obtenida sumando los puntos de los tesoros y la
     * bonificación correspondiente al tamaño del terreno.
     *
     * @return experiencia total obtenida.
     * @throws IllegalStateException si el tamaño del terreno es nulo o desconocido.
     */
    public int getExperienciaObtenida() {
        String tamanho = terreno.getTamanho();
        if (tamanho == null) {
            throw new IllegalStateException("El tamaño del terreno no puede ser nulo.");
        }
        switch (tamanho) {
            case PEQUENO:
                return puntos + 2;
            case MEDIANO:
                return puntos + 5;
            case GRANDE:
                return puntos + 10;
            default:
                throw new IllegalStateException("Tamaño de terreno desconocido: " + tamanho);
        }
    }

    /**
     * Devuelve una copia de los códigos completos de los tesoros encontrados.
     *
     * @return lista independiente con los códigos de los tesoros y sus partes.
     */
    public List<String> getCodigosEncontrados() {
        return new ArrayList<>(codigosEncontrados);
    }

    /**
     * Devuelve la región asociada a esta excavación.
     *
     * @return región cuyos tesoros se utilizan en la excavación.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Devuelve el arqueólogo que participa en esta excavación.
     *
     * @return arqueólogo asociado a la excavación.
     */
    public Archeologist getArqueologo() {
        return arqueologo;
    }

    // ===== Rejilla y ejecución ============================================

    /**
     * Devuelve una representación textual de la rejilla tal como la ve el
     * jugador, con las casillas aún ocultas.
     *
     * @return rejilla visible durante la excavación.
     */
    @Override
    public String toString() {
        return rejilla.dibujarVisible();
    }

    /**
     * Devuelve la rejilla mostrando el contenido de todas sus casillas.
     *
     * @return representación textual de la rejilla completamente revelada.
     */
    public String getRejillaDescubierta() {
        return rejilla.dibujarDescubierta(tesoros);
    }

    /**
     * Ejecuta la excavación por consola hasta que se agoten las acciones, se
     * encuentren todos los tesoros o se interrumpa la entrada.
     *
     * @return lista de códigos completos de los tesoros encontrados.
     */
    public List<String> ejecutar() {
        return new ExcavationConsoleUI(this).ejecutar();
    }
}