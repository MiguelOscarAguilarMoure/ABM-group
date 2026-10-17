package stats;

import commons.repositories.RegionRepository;
import helpers.CodeHelper;
import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import regions.Region;
import treasures.Treasure;

/**
 * Estadísticas de la partida: lugares visitados y tesoros encontrados.
 * Guarda los datos y sabe mostrarlos; el menú está en StatsOperation.
 */
public class Stats {

    /** Código de cada tesoro encontrado (con parte) → veces encontrado. */
    private final HashMap<String, Integer> tesoros = new HashMap<>();

    /** Código de cada región → veces que se ha excavado allí. */
    private final HashMap<String, Integer> lugares = new HashMap<>();

    

    /**
     * Registra una excavación: suma una visita a la región y cada tesoro encontrado.
     * Se llama desde la excavación al terminar.
     * @param region   La región excavada.
     * @param codigos  Los códigos completos de los tesoros encontrados ("GRC_CER_001_0005").
     */
    public void registrar(Region region, List<String> codigos) {
        lugares.merge(region.getCodigo(), 1, Integer::sum);
        for (String codigo : codigos) {
            tesoros.merge(codigo, 1, Integer::sum); //la lista de códigos que has encontrado en una excavación y le suma 1 a cada uno en el mapa
        }
    }

    

    /**
     * Por cada región: veces visitada, tesoros distintos encontrados frente al total
     * (con porcentaje) y total de tesoros excavados.
     */
    public void mostrarLugares() {
        for (Region r : RegionRepository.getTodas()) {
            int distintos = 0;   // códigos distintos encontrados en esa región
            int total = 0;       // suma de las veces que se han encontrado
            for (String codigo : tesoros.keySet()) {
                if (CodeHelper.getRegion(codigo).equals(r.getCodigo())) {
                    distintos++;
                    total += tesoros.get(codigo);
                }
            }
            int posibles = r.getNumPartes();   // cada parte cuenta como un tesoro distinto

            System.out.println("-------------------- " + r.getNombre() + " --------------------");
            System.out.println("Veces visitada: " + lugares.getOrDefault(r.getCodigo(), 0));//busca en el mapa lugares cuántas veces has visitado esa región, y si nunca has ido devuelve 0.
            System.out.println("Tesoros encontrados: " + distintos + "/" + posibles
                    + " (" + UtilsHelper.getPercent(distintos, posibles) + "%)");
            System.out.println("Total de tesoros excavados: " + total);
        }
    }

    

    /** Tesoros divididos por región y ordenados por código. */
    public void mostrarPorRegion() {
        List<String> codigos = codigosOrdenados();

        for (Region r : RegionRepository.getTodas()) {
            // Los códigos de esta región, ya en orden
            List<String> deLaRegion = new ArrayList<>();
            for (String codigo : codigos) {
                if (CodeHelper.getRegion(codigo).equals(r.getCodigo())) {
                    deLaRegion.add(codigo);
                }
            }
            if (!deLaRegion.isEmpty()) {
                System.out.println("-------------------- " + r.getNombre() + " --------------------");
                mostrarGrupo(deLaRegion);
            }
        }
    }

    /** Tesoros divididos por tipo y luego ordenados por código. */
    public void mostrarPorTipo() {
        List<String> codigos = codigosOrdenados();

        // Los tipos que aparecen ("CER", "MON"...), sin repetir y en orden
        List<String> tipos = new ArrayList<>();
        for (String codigo : codigos) {
            String tipo = CodeHelper.getType(codigo);
            if (!tipos.contains(tipo)) {
                tipos.add(tipo);
            }
        }
        Collections.sort(tipos);

        for (String tipo : tipos) {
            // Los códigos de este tipo, ya en orden
            List<String> delTipo = new ArrayList<>();
            for (String codigo : codigos) {
                if (CodeHelper.getType(codigo).equals(tipo)) {
                    delTipo.add(codigo);
                }
            }
            // El nombre del tipo se saca del primer tesoro ("cerámica" → "Cerámica")
            Treasure primero = RegionRepository.getTesoro(delTipo.get(0));
            String titulo = UtilsHelper.ucfirst(primero.getTipoTesoro().toString());
            System.out.println("-------------------- " + titulo + " --------------------");
            mostrarGrupo(delTipo);
        }
    }

    // AUXILIARES

    /** Devuelve los códigos encontrados ordenados alfabéticamente (el HashMap no los ordena). */
    private List<String> codigosOrdenados() {
        List<String> codigos = new ArrayList<>(tesoros.keySet());
        Collections.sort(codigos);
        return codigos;
    }

    /**
     * Muestra una lista de códigos (ya ordenada) agrupados por tesoro:
     * si el tesoro tiene una sola parte, "-Nombre: veces";
     * si tiene varias, "-Nombre:" y debajo cada parte con cuatro espacios.
     */
    private void mostrarGrupo(List<String> codigos) {
        String tesoroActual = "";
        for (String codigo : codigos) {
            Treasure t = RegionRepository.getTesoro(codigo);
            if (t == null) {
                continue;   // código que no corresponde a ningún tesoro registrado
            }
            int veces = tesoros.get(codigo);

            if (t.getPartes() == 1) {
                System.out.println("-" + t.getNombre() + ": " + veces);
            } else {
                // Cabecera solo la primera vez que aparece una parte de este tesoro
                if (!t.getCodigo().equals(tesoroActual)) {
                    System.out.println("-" + t.getNombre() + ":");
                    tesoroActual = t.getCodigo();
                }
                System.out.println("    " + codigo + ": " + veces);
            }
        }
    }
}