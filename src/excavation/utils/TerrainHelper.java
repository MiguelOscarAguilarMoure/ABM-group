package excavation.utils;

import excavation.ETerreno;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import regions.IRegionDificil;
import regions.Region;

/**
 * Utilidad sin estado encargada de calcular el reparto proporcional de terrenos
 * y generar la capa inicial de terreno para una excavación.
 * @author Miguel Aguilar Moure
 * @version 2.0
 * @see ETerreno
 * @see Region
 * @since 2026-11-18
 * 
 */
public final class TerrainHelper {

    /** Impide la creación de instancias de esta clase de utilidad. */
    private TerrainHelper() {
    }

    /**
     * Genera y mezcla la lista de terrenos correspondiente a una región.
     * Si la región es básica o sus proporciones no son válidas, rellena todo con tierra.
     *
     * @param region región de la excavación.
     * @param totalCasillas número total de casillas del tablero.
     * @param random generador aleatorio usado para mezclar los terrenos.
     * @return lista mezclada con tantos terrenos como {@code totalCasillas}.
     * @throws IllegalArgumentException si la región o el generador son nulos, o el total
     *         de casillas no es positivo.
     */
    public static List<ETerreno> generarCapaTerrenos(Region region, int totalCasillas, Random random) {
        if (region == null) {
            throw new IllegalArgumentException("La región no puede ser nula.");
        }
        if (totalCasillas <= 0) {
            throw new IllegalArgumentException("El total de casillas debe ser mayor que cero.");
        }
        if (random == null) {
            throw new IllegalArgumentException("El generador aleatorio no puede ser nulo.");
        }

        List<ETerreno> capa = new ArrayList<>();
        if (region instanceof IRegionDificil) {
            Map<ETerreno, Integer> proporciones = ((IRegionDificil) region).getProporciones();
            capa = repartirTerrenos(proporciones, totalCasillas);
        }

        if (capa.size() != totalCasillas) {
            capa = new ArrayList<>();
            for (int i = 0; i < totalCasillas; i++) {
                capa.add(ETerreno.TIERRA);
            }
        }

        Collections.shuffle(capa, random);
        return capa;
    }

    /**
     * Calcula cuántas casillas de cada terreno corresponden según sus porcentajes
     * aplicando el método del resto mayor para que la suma coincida con {@code total}.
     *
     * @param proporciones mapa con el porcentaje asignado a cada tipo de terreno; los
     *        valores nulos o negativos se consideran cero.
     * @param total número total de casillas a repartir.
     * @return lista sin mezclar con {@code total} terrenos, o vacía si no hay proporciones válidas.
     * @throws IllegalArgumentException si {@code total} es menor o igual que cero.
     */
    public static List<ETerreno> repartirTerrenos(Map<ETerreno, Integer> proporciones, int total) {
        if (total <= 0) {
            throw new IllegalArgumentException("El total de casillas debe ser mayor que cero.");
        }

        List<ETerreno> capa = new ArrayList<>();
        int suma = sumarProporciones(proporciones);
        if (suma > 0) {
            ETerreno[] tipos = ETerreno.values();
            int[] cantidades = new int[tipos.length];
            int[] sobrante = new int[tipos.length];
            int asignadas = 0;

            for (int i = 0; i < tipos.length; i++) {
                int porcentaje = getPorcentaje(proporciones, tipos[i]);
                if (porcentaje > 0) {
                    cantidades[i] = total * porcentaje / suma;
                    sobrante[i] = total * porcentaje % suma;
                    asignadas += cantidades[i];
                } else {
                    sobrante[i] = -1;
                }
            }

            // Reparte las casillas restantes por orden de resto, sin repetir tipo.
            while (asignadas < total) {
                int mejor = indiceMayor(sobrante);
                cantidades[mejor]++;
                sobrante[mejor] = -1;
                asignadas++;
            }

            for (int i = 0; i < tipos.length; i++) {
                for (int j = 0; j < cantidades[i]; j++) {
                    capa.add(tipos[i]);
                }
            }
        }
        return capa;
    }

    /**
     * Suma las proporciones no negativas de los tipos de terreno.
     *
     * @param proporciones mapa de porcentajes; si es nulo, la suma es cero.
     * @return suma de las proporciones válidas.
     */
    private static int sumarProporciones(Map<ETerreno, Integer> proporciones) {
        int suma = 0;
        if (proporciones != null) {
            ETerreno[] tipos = ETerreno.values();
            for (int i = 0; i < tipos.length; i++) {
                suma += getPorcentaje(proporciones, tipos[i]);
            }
        }
        return suma;
    }

    /**
     * Obtiene el porcentaje válido asociado a un tipo de terreno.
     *
     * @param proporciones mapa de porcentajes.
     * @param tipo tipo de terreno consultado.
     * @return porcentaje, o cero si no está definido o es negativo.
     */
    private static int getPorcentaje(Map<ETerreno, Integer> proporciones, ETerreno tipo) {
        Integer porcentaje = proporciones.get(tipo);
        return (porcentaje == null || porcentaje < 0) ? 0 : porcentaje;
    }

    /**
     * Busca el primer índice con el valor máximo del arreglo.
     *
     * @param valores valores que se compararán.
     * @return índice del valor máximo; en caso de empate, el primero.
     */
    private static int indiceMayor(int[] valores) {
        int mejor = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > valores[mejor]) {
                mejor = i;
            }
        }
        return mejor;
    }
}