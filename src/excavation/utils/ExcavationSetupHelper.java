package excavation.utils;

import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import excavation.ETerrainSize;
import excavation.ETerreno;
import people.Archeologist;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.Treasure;

/**
 * Helper encargado de la preparación inicial de una excavación:
 * distribución aleatoria de tesoros y aplicación de las habilidades previas del arqueólogo.
 * @author Miguel Aguilar Moure
 * @version 2.0
 * @see ETerrainSize
 * @see Region
 * @see Archeologist
 * @see ETreasureRarity
 * @see Treasure
 * @since 2026-11-18
 */
public final class ExcavationSetupHelper {

    /** Nombre del tamaño de terreno pequeño. */
    private static final String PEQUENO = "Pequeño";

    /** Nombre del tamaño de terreno mediano. */
    private static final String MEDIANO = "Mediano";

    /** Nombre del tamaño de terreno grande. */
    private static final String GRANDE = "Grande";

    /** Fracción máxima del tablero que pueden revelar las habilidades iniciales. */
    private static final double REVELACION_MAX = 0.25;

    /** Impide la creación de instancias de esta clase de utilidad. */
    private ExcavationSetupHelper() {
    }

    /**
     * Genera y ubica los tesoros de la excavación según el tamaño del terreno,
     * la región y las bonificaciones del arqueólogo.
     *
     * @param terreno       configuración de tamaño del terreno.
     * @param region        región de la que se obtienen los tesoros.
     * @param arqueologo    arqueólogo que realiza la excavación.
     * @param totalCasillas número total de casillas del tablero.
     * @param random        generador aleatorio.
     * @return mapa que asocia índice de casilla con el tesoro enterrado.
     * @throws IllegalArgumentException si algún argumento es nulo o totalCasillas <= 0.
     * @throws IllegalStateException    si el tamaño del terreno no está reconocido.
     */
    public static Map<Integer, Treasure> generarTesoros(ETerrainSize terreno, Region region,
            Archeologist arqueologo, int totalCasillas, Random random) {
        if (terreno == null || region == null || arqueologo == null || random == null) {
            throw new IllegalArgumentException("Los parámetros de generación de tesoros no pueden ser nulos.");
        }
        if (totalCasillas <= 0) {
            throw new IllegalArgumentException("El total de casillas debe ser mayor que cero.");
        }

        int cantidadBase = Math.max(1, (int) Math.ceil(totalCasillas / 10.0));
        List<ETreasureRarity> rarezas = new ArrayList<>();

        switch (terreno.getTamanho()) {
            case PEQUENO:
                anadir(rarezas, ETreasureRarity.COMMON, 1);
                break;
            case MEDIANO:
                anadir(rarezas, ETreasureRarity.COMMON, 3);
                anadir(rarezas, ETreasureRarity.UNCOMMON, 1);
                break;
            case GRANDE:
                anadir(rarezas, ETreasureRarity.COMMON, 5);
                anadir(rarezas, ETreasureRarity.UNCOMMON, 3);
                anadir(rarezas, ETreasureRarity.RARE, 1);
                break;
            default:
                throw new IllegalStateException("Tamaño de terreno desconocido: " + terreno.getTamanho());
        }

        boolean permiteRaros = !terreno.getTamanho().equals(PEQUENO);
        while (rarezas.size() < cantidadBase) {
            rarezas.add(tirarRareza(permiteRaros, arqueologo, random));
        }

        int maximo = totalCasillas / 2;
        if (permiteRaros) {
            anadirHasta(rarezas, ETreasureRarity.RARE,
                    arqueologo.getTesorosExtra(ETreasureRarity.RARE, region), maximo);
        }
        anadirHasta(rarezas, ETreasureRarity.UNCOMMON,
                arqueologo.getTesorosExtra(ETreasureRarity.UNCOMMON, region), maximo);
        anadirHasta(rarezas, ETreasureRarity.COMMON,
                arqueologo.getTesorosExtra(ETreasureRarity.COMMON, region), maximo);

        int extrasGenericos = arqueologo.getTesorosExtra(region);
        for (int i = 0; i < extrasGenericos && rarezas.size() < maximo; i++) {
            rarezas.add(tirarRareza(permiteRaros, arqueologo, random));
        }

        List<Integer> posiciones = UtilsHelper.genRandomIdxs(rarezas.size(), totalCasillas);
        Map<Integer, Treasure> tesorosColocados = new HashMap<>();
        int colocables = Math.min(rarezas.size(), posiciones.size());

        for (int i = 0; i < colocables; i++) {
            Treasure tesoro = region.getTesoroAleatorioPorRareza(rarezas.get(i));
            if (tesoro != null) {
                tesorosColocados.put(posiciones.get(i), tesoro);
            }
        }

        return tesorosColocados;
    }

    /**
     * Aplica las habilidades iniciales del arqueólogo sin consumir acciones.
     * Orden obligatorio: 1º demoliciones de terreno, 2º limpieza de casillas vacías,
     * 3º escaneo (suposiciones).
     *
     * @param rejilla    rejilla de la excavación que se actualizará.
     * @param tesoros    mapa de tesoros colocados, indexados por casilla.
     * @param arqueologo arqueólogo cuyas habilidades se aplican.
     * @param random     generador aleatorio usado para elegir casillas.
     * @throws IllegalArgumentException si algún argumento es nulo.
     */
    public static void aplicarHabilidadesIniciales(RejillaExcavacion rejilla, Map<Integer, Treasure> tesoros,
            Archeologist arqueologo, Random random) {
        if (rejilla == null || tesoros == null || arqueologo == null || random == null) {
            throw new IllegalArgumentException("Los parámetros para aplicar habilidades no pueden ser nulos.");
        }

        cambiarTerrenos(rejilla, arqueologo, random);

        int total = rejilla.getTotalCasillas();
        int maximo = (int) (total * REVELACION_MAX);
        int reveladas = limpiar(rejilla, tesoros, arqueologo.getLimpieza(), maximo, random);
        suponer(rejilla, tesoros, arqueologo.getSuposicion() / 3, maximo - reveladas, random);
    }

    /**
     * Convierte en tierra las casillas indicadas por las habilidades del arqueólogo.
     *
     * @param rejilla rejilla cuyas casillas se modificarán.
     * @param arqueologo arqueólogo que aporta las cantidades de cambio.
     * @param random generador aleatorio para seleccionar las casillas.
     */
    private static void cambiarTerrenos(RejillaExcavacion rejilla, Archeologist arqueologo, Random random) {
        ETerreno[] tipos = ETerreno.values();
        for (int i = 0; i < tipos.length; i++) {
            ETerreno tipo = tipos[i];
            if (tipo != ETerreno.TIERRA) {
                int cantidad = arqueologo.getCasillasCambiadas(tipo);
                if (cantidad > 0) {
                    rejilla.cambiarATierra(tipo, cantidad, random);
                }
            }
        }
    }

    /**
     * Descubre casillas vacías seleccionadas al azar, respetando el límite indicado.
     *
     * @param rejilla rejilla que se actualizará.
     * @param tesoros mapa de casillas ocupadas por tesoros.
     * @param cantidad cantidad máxima de casillas que se intentará descubrir.
     * @param maximo límite total de casillas que pueden revelarse.
     * @param random generador aleatorio para elegir las casillas.
     * @return cantidad de casillas vacías descubiertas.
     */
    private static int limpiar(RejillaExcavacion rejilla, Map<Integer, Treasure> tesoros,
            int cantidad, int maximo, Random random) {
        int total = rejilla.getTotalCasillas();
        List<Integer> vacias = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            if (!tesoros.containsKey(i)) {
                vacias.add(i);
            }
        }

        int destapadas = 0;
        while (destapadas < cantidad && destapadas < maximo && !vacias.isEmpty()) {
            int indice = vacias.remove(random.nextInt(vacias.size()));
            rejilla.resolverCasilla(indice, RejillaExcavacion.VACIO);
            destapadas++;
        }
        return destapadas;
    }

    /**
     * Marca suposiciones sobre tesoros y casillas vacías hasta alcanzar el límite.
     * Cada escaneo marca un tesoro y puede marcar hasta dos casillas vacías.
     *
     * @param rejilla rejilla donde se registrarán las suposiciones.
     * @param tesoros mapa de casillas ocupadas por tesoros.
     * @param escaneos cantidad de escaneos disponibles.
     * @param maximo cantidad máxima de casillas que se pueden marcar.
     * @param random generador aleatorio para elegir las casillas.
     */
    private static void suponer(RejillaExcavacion rejilla, Map<Integer, Treasure> tesoros,
            int escaneos, int maximo, Random random) {
        int total = rejilla.getTotalCasillas();
        List<Integer> conTesoro = new ArrayList<>(tesoros.keySet());
        List<Integer> sinTesoro = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            if (!tesoros.containsKey(i)) {
                sinTesoro.add(i);
            }
        }

        int marcadas = 0;
        for (int e = 0; e < escaneos && marcadas < maximo && !conTesoro.isEmpty(); e++) {
            int indiceTesoro = conTesoro.remove(random.nextInt(conTesoro.size()));
            rejilla.marcarSuposicion(indiceTesoro);
            marcadas++;

            for (int j = 0; j < 2 && marcadas < maximo && !sinTesoro.isEmpty(); j++) {
                int indiceVacia = sinTesoro.remove(random.nextInt(sinTesoro.size()));
                if (!rejilla.estaExcavada(indiceVacia)) {
                    rejilla.marcarSuposicion(indiceVacia);
                    marcadas++;
                }
            }
        }
    }

    /**
     * Añade una rareza a la lista el número de veces indicado.
     *
     * @param lista lista que recibirá las rarezas.
     * @param rareza rareza que se añadirá.
     * @param veces número de veces que se añadirá.
     */
    private static void anadir(List<ETreasureRarity> lista, ETreasureRarity rareza, int veces) {
        for (int i = 0; i < veces; i++) {
            lista.add(rareza);
        }
    }

    /**
     * Añade una rareza sin superar el tamaño máximo de la lista.
     *
     * @param lista lista que recibirá las rarezas.
     * @param rareza rareza que se añadirá.
     * @param veces número máximo de veces que se añadirá.
     * @param maximo tamaño máximo de la lista.
     */
    private static void anadirHasta(List<ETreasureRarity> lista, ETreasureRarity rareza, int veces, int maximo) {
        for (int i = 0; i < veces && lista.size() < maximo; i++) {
            lista.add(rareza);
        }
    }

    /**
     * Elige una rareza al azar según las probabilidades y bonificaciones aplicables.
     *
     * @param permiteRaros indica si se pueden obtener tesoros raros.
     * @param arqueologo arqueólogo cuyas bonificaciones modifican las probabilidades.
     * @param random generador aleatorio para realizar la tirada.
     * @return rareza seleccionada.
     */
    private static ETreasureRarity tirarRareza(boolean permiteRaros, Archeologist arqueologo, Random random) {
        int raro = permiteRaros ? 10 + arqueologo.getProbabilidadExtra(ETreasureRarity.RARE) : 0;
        int infrecuente = 30 + arqueologo.getProbabilidadExtra(ETreasureRarity.UNCOMMON);

        int tirada = random.nextInt(100);
        if (tirada < raro) {
            return ETreasureRarity.RARE;
        }
        if (tirada < raro + infrecuente) {
            return ETreasureRarity.UNCOMMON;
        }
        return ETreasureRarity.COMMON;
    }
}