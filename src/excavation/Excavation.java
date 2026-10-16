package excavation;

import auxiliares.UtilsHelper;
import regiones.Region;
import tesoros.ETreasureRarity;
import tesoros.Treasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Una excavación: genera la rejilla y los tesoros enterrados, mantiene el estado
 * del proceso (acciones, tesoros y posiciones restantes) y ofrece el bucle
 * interactivo por consola.
 * <p>
 * Se crea mediante {@link Builder}, que permite aplicar los bonos del arqueólogo.
 */
public class Excavation {

    private static final char[] LETRAS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final Random RANDOM = new Random();
    private static final Pattern PATRON_POSICION = Pattern.compile("^(\\d{1,3})([A-Z]+)$");

    private static final String SIN_EXCAVAR = "·";
    private static final String VACIO = "x";

    private static final double ACCIONES_BASE = 0.25;   // 25 % de la rejilla
    private static final double ACCIONES_MAX = 0.75;    // 75 % de la rejilla
    private static final int PCT_TESOROS = 10;          // 10 % de la rejilla
    private static final int PCT_TESOROS_MAX = 50;      // 50 % de la rejilla
    private static final int PROB_RARO = 10;
    private static final int PROB_INFRECUENTE = 30;

    /**
     * Categoría del terreno con sus mínimos de tesoros y la experiencia extra.
     * Se deduce del texto que ya almacena {@link ETerrainSize}.
     */
    private enum Categoria {
        PEQUENO("Pequeño", 2, 1, 0, 0, false),
        MEDIANO("Mediano", 5, 3, 1, 0, true),
        GRANDE("Grande", 10, 5, 3, 1, true);

        private final String nombre;
        private final int experienciaExtra;
        private final int minComunes;
        private final int minInfrecuentes;
        private final int minRaros;
        private final boolean permiteRaros;

        private Categoria(String nombre, int experienciaExtra, int minComunes,
                          int minInfrecuentes, int minRaros, boolean permiteRaros) {
            this.nombre = nombre;
            this.experienciaExtra = experienciaExtra;
            this.minComunes = minComunes;
            this.minInfrecuentes = minInfrecuentes;
            this.minRaros = minRaros;
            this.permiteRaros = permiteRaros;
        }

        private static Categoria deTamanho(String tamanho) {
            for (Categoria categoria : values()) {
                if (categoria.nombre.equals(tamanho)) {
                    return categoria;
                }
            }
            throw new IllegalArgumentException("Tamaño de terreno desconocido: " + tamanho);
        }
    }

    private final ETerrainSize terreno;
    private final Region region;
    private final Categoria categoria;
    private final int numColumnas;
    private final int numFilas;

    private final List<String> etiquetasColumnas = new ArrayList<>();
    private final int anchoEtiqueta;
    private final int anchoNumFila;
    private final String cabecera;

    /** Lo que ve el jugador en cada casilla (orden por filas). */
    private final List<String> casillas;
    private final boolean[] excavadas;
    /** Tesoros enterrados: índice de casilla -> tesoro. Las casillas sin clave están vacías. */
    private final Map<Integer, Treasure> tesoros = new HashMap<>();
    private final List<String> codigosEncontrados = new ArrayList<>();

    private int accionesRestantes;
    private int posicionesRestantes;
    private int tesorosRestantes;
    private int puntosObtenidos;

    private Excavation(Builder builder) {
        this.terreno = builder.terreno;
        this.region = builder.region;
        this.categoria = Categoria.deTamanho(terreno.getTamanho());

        // ETerrainSize guarda "AxB" como el documento: A = ancho (columnas/letras), B = alto (filas/números).
        this.numColumnas = terreno.getFil();
        this.numFilas = terreno.getCol();
        int total = numColumnas * numFilas;

        this.anchoEtiqueta = calcularLongitudEtiqueta(numColumnas);
        generarEtiquetas("", anchoEtiqueta, numColumnas, etiquetasColumnas);
        this.anchoNumFila = String.valueOf(numFilas).length();
        this.cabecera = construirCabecera();

        this.casillas = new ArrayList<>(Collections.nCopies(total, SIN_EXCAVAR));
        this.excavadas = new boolean[total];
        this.posicionesRestantes = total;

        generarTesoros(builder, total);
        this.accionesRestantes = calcularAcciones(builder, total);
    }

    // ===================================================================
    // Generación de la rejilla
    // ===================================================================

    /** Letras necesarias por etiqueta: 1 si caben en el abecedario, 2 si no, etc. */
    private static int calcularLongitudEtiqueta(int numColumnas) {
        int longitud = 1;
        int capacidad = LETRAS.length;
        while (capacidad < numColumnas) {
            longitud++;
            capacidad *= LETRAS.length;
        }
        return longitud;
    }

    /**
     * Genera recursivamente las etiquetas (A, B... AA, AB...) sin pasarse de la cantidad pedida.
     *
     * @param prefijo   Cadena construida hasta ahora.
     * @param longitud  Longitud final de cada etiqueta.
     * @param cantidad  Número de etiquetas necesarias.
     * @param etiquetas Lista donde se van añadiendo.
     */
    private static void generarEtiquetas(String prefijo, int longitud, int cantidad, List<String> etiquetas) {
        if (prefijo.length() == longitud) {
            etiquetas.add(prefijo);
            return;
        }
        for (int i = 0; i < LETRAS.length && etiquetas.size() < cantidad; i++) {
            generarEtiquetas(prefijo + LETRAS[i], longitud, cantidad, etiquetas);
        }
    }

    private String construirCabecera() {
        StringBuilder sb = new StringBuilder(" ".repeat(anchoNumFila));
        for (String etiqueta : etiquetasColumnas) {
            sb.append(' ').append(etiqueta);
        }
        return sb.toString();
    }

    // ===================================================================
    // Generación de tesoros y acciones
    // ===================================================================

    private void generarTesoros(Builder builder, int total) {
        List<ETreasureRarity> plan = planificarTesoros(builder, total);
        List<Integer> posiciones = UtilsHelper.genRandomIdxs(plan.size(), total);
        if (posiciones.size() != plan.size()) {
            throw new IllegalStateException("No se pudieron asignar posiciones a los tesoros.");
        }
        for (int i = 0; i < plan.size(); i++) {
            ETreasureRarity rareza = plan.get(i);
            Treasure tesoro = region.getTesoroAleatorioPorRareza(rareza);
            if (tesoro == null) {
                throw new IllegalStateException(
                        "La región " + region.getNombre() + " no tiene tesoros de rareza " + rareza + ".");
            }
            tesoros.put(posiciones.get(i), tesoro);
        }
        this.tesorosRestantes = tesoros.size();
    }

    /**
     * Decide cuántos tesoros hay y de qué rareza, respetando el orden del documento:
     * mínimos del terreno, aleatorios hasta el 10 %, extras del arqueólogo
     * (raros, infrecuentes, comunes) y aleatorios del arqueólogo. Nunca supera el 50 %.
     */
    private List<ETreasureRarity> planificarTesoros(Builder builder, int total) {
        int maximo = total * PCT_TESOROS_MAX / 100;
        int objetivo = Math.max(1, (total * PCT_TESOROS + 99) / 100); // 10 % redondeado hacia arriba
        List<ETreasureRarity> plan = new ArrayList<>();

        anadir(plan, ETreasureRarity.COMMON, categoria.minComunes, maximo);
        anadir(plan, ETreasureRarity.UNCOMMON, categoria.minInfrecuentes, maximo);
        anadir(plan, ETreasureRarity.RARE, categoria.minRaros, maximo);

        while (plan.size() < objetivo && plan.size() < maximo) {
            plan.add(tirarRareza(builder));
        }

        if (categoria.permiteRaros) {
            anadir(plan, ETreasureRarity.RARE, builder.tesorosExtra.getOrDefault(ETreasureRarity.RARE, 0), maximo);
        }
        anadir(plan, ETreasureRarity.UNCOMMON, builder.tesorosExtra.getOrDefault(ETreasureRarity.UNCOMMON, 0), maximo);
        anadir(plan, ETreasureRarity.COMMON, builder.tesorosExtra.getOrDefault(ETreasureRarity.COMMON, 0), maximo);

        for (int i = 0; i < builder.tesorosAleatoriosExtra && plan.size() < maximo; i++) {
            plan.add(tirarRareza(builder));
        }
        return plan;
    }

    private static void anadir(List<ETreasureRarity> plan, ETreasureRarity rareza, int cantidad, int maximo) {
        for (int i = 0; i < cantidad && plan.size() < maximo; i++) {
            plan.add(rareza);
        }
    }

    /** Rareza aleatoria: 60 % común, 30 % infrecuente, 10 % rara (más los bonos). Sin raros en terrenos pequeños. */
    private ETreasureRarity tirarRareza(Builder builder) {
        int probRaro = 0;
        if (categoria.permiteRaros) {
            probRaro = Math.min(100, PROB_RARO + builder.probabilidadExtra.getOrDefault(ETreasureRarity.RARE, 0));
        }
        int probInfrecuente = Math.min(100 - probRaro,
                PROB_INFRECUENTE + builder.probabilidadExtra.getOrDefault(ETreasureRarity.UNCOMMON, 0));

        int tirada = RANDOM.nextInt(100);
        if (tirada < probRaro) {
            return ETreasureRarity.RARE;
        }
        if (tirada < probRaro + probInfrecuente) {
            return ETreasureRarity.UNCOMMON;
        }
        return ETreasureRarity.COMMON;
    }

    /** (25 % de la rejilla + extra) * factor, acotado entre 1 y el 75 % de la rejilla. */
    private static int calcularAcciones(Builder builder, int total) {
        double bruto = (total * ACCIONES_BASE + builder.accionesExtra) * builder.factorAcciones;
        int maximo = (int) (total * ACCIONES_MAX);
        return Math.max(1, Math.min((int) Math.round(bruto), maximo));
    }

    // ===================================================================
    // Excavar
    // ===================================================================

    /**
     * Excava una posición, consumiendo una acción.
     *
     * @param posicion La posición a excavar.
     * @return <code>true</code> si había un tesoro.
     * @throws NullPointerException     Si la posición es nula.
     * @throws IllegalStateException    Si la excavación ya ha terminado.
     * @throws IllegalArgumentException Si la posición no existe o ya está excavada.
     */
    public boolean excavar(Position posicion) {
        Objects.requireNonNull(posicion, "La posición no puede ser nula.");
        if (isFinalizada()) {
            throw new IllegalStateException("La excavación ya ha finalizado.");
        }
        int indice = getIndice(posicion);
        if (excavadas[indice]) {
            throw new IllegalArgumentException("La posición " + posicion + " ya está excavada.");
        }

        excavadas[indice] = true;
        accionesRestantes--;
        posicionesRestantes--;
        casillas.set(indice, getSimboloOculto(indice));

        Treasure tesoro = tesoros.get(indice);
        if (tesoro == null) {
            return false;
        }
        tesorosRestantes--;
        puntosObtenidos += tesoro.getPuntos();
        codigosEncontrados.add(tesoro.getCodigoConParte());
        return true;
    }

    private int getIndice(Position posicion) {
        int fila = posicion.getFil();
        int columna = etiquetasColumnas.indexOf(posicion.getCol());
        if (fila < 1 || fila > numFilas) {
            throw new IllegalArgumentException("La fila " + fila + " no existe (1-" + numFilas + ").");
        }
        if (columna < 0) {
            throw new IllegalArgumentException("La columna " + posicion.getCol() + " no existe ("
                    + etiquetasColumnas.get(0) + "-" + etiquetasColumnas.get(numColumnas - 1) + ").");
        }
        return (fila - 1) * numColumnas + columna;
    }

    private String getSimboloOculto(int indice) {
        Treasure tesoro = tesoros.get(indice);
        return (tesoro == null) ? VACIO : tesoro.getRarezaTesoro().getCode();
    }

    // ===================================================================
    // Consulta de estado
    // ===================================================================

    public boolean isFinalizada() {
        return accionesRestantes <= 0 || tesorosRestantes <= 0;
    }

    public int getAccionesRestantes() {
        return accionesRestantes;
    }

    public int getTesorosRestantes() {
        return tesorosRestantes;
    }

    public int getPosicionesRestantes() {
        return posicionesRestantes;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    /** Experiencia ganada: puntos de los tesoros más el extra del tamaño (2, 5 o 10). */
    public int getExperienciaObtenida() {
        return puntosObtenidos + categoria.experienciaExtra;
    }

    /** @return Copia de los códigos completos (con parte) de los tesoros encontrados. */
    public List<String> getCodigosEncontrados() {
        return new ArrayList<>(codigosEncontrados);
    }

    public Region getRegion() {
        return region;
    }

    public ETerrainSize getTerreno() {
        return terreno;
    }

    // ===================================================================
    // Representación de la rejilla
    // ===================================================================

    /** La rejilla tal y como la ve el jugador ahora mismo. */
    @Override
    public String toString() {
        return renderizar(casillas);
    }

    /** La rejilla con todo su contenido revelado. */
    public String getRejillaDescubierta() {
        int total = numColumnas * numFilas;
        List<String> reveladas = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            reveladas.add(getSimboloOculto(i));
        }
        return renderizar(reveladas);
    }

    private String renderizar(List<String> simbolos) {
        StringBuilder sb = new StringBuilder(cabecera);
        for (int fila = 0; fila < numFilas; fila++) {
            sb.append(System.lineSeparator());
            sb.append(String.format("%" + anchoNumFila + "d", fila + 1));
            for (int col = 0; col < numColumnas; col++) {
                sb.append(' ').append(centrar(simbolos.get(fila * numColumnas + col)));
            }
        }
        return sb.toString();
    }

    /** Centra el símbolo bajo la etiqueta de su columna. */
    private String centrar(String simbolo) {
        int relleno = anchoEtiqueta - simbolo.length();
        int izquierda = relleno / 2;
        return " ".repeat(izquierda) + simbolo + " ".repeat(relleno - izquierda);
    }

    // ===================================================================
    // Proceso interactivo
    // ===================================================================

    /**
     * Ejecuta el proceso de excavación por consola hasta quedarse sin acciones o sin tesoros.
     * El Scanner lo crea y lo cierra quien lo llama (cerrarlo aquí cerraría System.in).
     *
     * @param entrada Scanner sobre la entrada del usuario.
     * @return Los códigos completos de los tesoros encontrados.
     */
    public List<String> ejecutar(Scanner entrada) {
        Objects.requireNonNull(entrada, "El Scanner no puede ser nulo.");

        boolean interrumpida = false;
        while (!isFinalizada() && !interrumpida) {
            System.out.println();
            System.out.println("Acciones restantes: " + accionesRestantes);
            System.out.println(this);
            System.out.print("Posiciones a excavar (ej. 3B 4C): ");

            String linea = null;
            try {
                linea = entrada.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("No hay entrada disponible. Se interrumpe la excavación.");
                interrumpida = true;
            }
            if (!interrumpida) {
                procesarEntrada(linea);
            }
        }
        mostrarFinal();
        return getCodigosEncontrados();
    }

    private void procesarEntrada(String linea) {
        if (linea == null || linea.isBlank()) {
            System.out.println("No has introducido ninguna posición.");
            return;
        }
        for (String indicacion : linea.trim().split("[\\s,]+")) {
            if (isFinalizada()) {
                System.out.println("Se ignoran las posiciones restantes: la excavación ha terminado.");
                return;
            }
            try {
                Position posicion = parsearPosicion(indicacion);
                boolean hallazgo = excavar(posicion);
                System.out.println("  " + posicion + ": " + (hallazgo ? "¡tesoro encontrado!" : "nada"));
            } catch (IllegalArgumentException e) {
                System.out.println("  " + indicacion + ": " + e.getMessage());
            }
        }
    }

    private static Position parsearPosicion(String texto) {
        Matcher matcher = PATRON_POSICION.matcher(texto.trim().toUpperCase(Locale.ROOT));
        if (!matcher.matches()) {
            throw new IllegalArgumentException("formato no válido, usa número y letra (ej. 3B).");
        }
        return new Position(Integer.parseInt(matcher.group(1)), matcher.group(2));
    }

    private void mostrarFinal() {
        System.out.println();
        if (tesorosRestantes == 0) {
            System.out.println("¡Has encontrado todos los tesoros!");
        }
        System.out.println("Estado final de la rejilla:");
        System.out.println(this);
        System.out.println();
        System.out.println("Rejilla descubierta:");
        System.out.println(getRejillaDescubierta());
    }

    // ===================================================================
    // Builder
    // ===================================================================

    /**
     * Construye una {@link Excavation}. Terreno y región son obligatorios; el resto de
     * valores son los bonos del arqueólogo y tienen un valor neutro por defecto.
     * <p>
     * TODO: añadir <code>arqueologo(Archeologist)</code> cuando la clase disponga de los métodos
     * de bonos; solo tendrá que rellenar estos valores, sin tocar Excavation.
     */
    public static class Builder {
        private final ETerrainSize terreno;
        private final Region region;
        private int accionesExtra = 0;
        private double factorAcciones = 1.0;
        private int tesorosAleatoriosExtra = 0;
        private final EnumMap<ETreasureRarity, Integer> tesorosExtra = new EnumMap<>(ETreasureRarity.class);
        private final EnumMap<ETreasureRarity, Integer> probabilidadExtra = new EnumMap<>(ETreasureRarity.class);

        public Builder(ETerrainSize terreno, Region region) {
            this.terreno = Objects.requireNonNull(terreno, "El terreno no puede ser nulo.");
            this.region = Objects.requireNonNull(region, "La región no puede ser nula.");
        }

        public Builder accionesExtra(int cantidad) {
            if (cantidad < 0) {
                throw new IllegalArgumentException("Las acciones extra no pueden ser negativas.");
            }
            this.accionesExtra = cantidad;
            return this;
        }

        /** Factor de aumento de acciones (producto de los de las habilidades). Mínimo 1. */
        public Builder factorAcciones(double factor) {
            if (Double.isNaN(factor) || factor < 1.0) {
                throw new IllegalArgumentException("El factor de acciones debe ser un número mayor o igual que 1.");
            }
            this.factorAcciones = factor;
            return this;
        }

        public Builder tesorosAleatoriosExtra(int cantidad) {
            if (cantidad < 0) {
                throw new IllegalArgumentException("Los tesoros extra no pueden ser negativos.");
            }
            this.tesorosAleatoriosExtra = cantidad;
            return this;
        }

        public Builder tesorosExtra(ETreasureRarity rareza, int cantidad) {
            Objects.requireNonNull(rareza, "La rareza no puede ser nula.");
            if (cantidad < 0) {
                throw new IllegalArgumentException("Los tesoros extra no pueden ser negativos.");
            }
            tesorosExtra.put(rareza, cantidad);
            return this;
        }

        /** Puntos porcentuales que se suman a la probabilidad de una rareza (infrecuente o rara). */
        public Builder probabilidadExtra(ETreasureRarity rareza, int puntosPorcentuales) {
            Objects.requireNonNull(rareza, "La rareza no puede ser nula.");
            if (puntosPorcentuales < 0 || puntosPorcentuales > 100) {
                throw new IllegalArgumentException("La probabilidad extra debe estar entre 0 y 100.");
            }
            probabilidadExtra.put(rareza, puntosPorcentuales);
            return this;
        }

        public Excavation build() {
            return new Excavation(this);
        }
    }
}