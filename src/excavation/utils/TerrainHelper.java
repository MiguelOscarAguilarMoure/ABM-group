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
 */
public final class TerrainHelper {

    private TerrainHelper() {
        // Evita la instanciación de la clase de utilidad
    }

    /**
     * Genera y mezcla la lista de terrenos correspondiente a una región.
     * Si la región es básica o sus proporciones no son válidas, rellena todo con tierra.
     *
     * @param region        región de la excavación.
     * @param totalCasillas número total de casillas del tablero.
     * @param random        generador de números aleatorios.
     * @return lista mezclada con tantos terrenos como {@code totalCasillas}.
     * @throws IllegalArgumentException si la región o random son nulos, o totalCasillas <= 0.
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
     * @param proporciones mapa con el porcentaje de cada tipo de terreno.
     * @param total        número total de casillas a repartir.
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

    private static int getPorcentaje(Map<ETerreno, Integer> proporciones, ETerreno tipo) {
        Integer porcentaje = proporciones.get(tipo);
        return (porcentaje == null || porcentaje < 0) ? 0 : porcentaje;
    }

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