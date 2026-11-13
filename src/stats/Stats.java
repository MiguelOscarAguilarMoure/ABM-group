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
 * <p>
 * Guarda los datos en dos {@link HashMap} y sabe mostrarlos con el formato del enunciado.
 * El menú que elige qué estadística mostrar está en {@code StatsOperation}.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see commons.operations.StatsOperation
 * @see commons.repositories.RegionRepository
 */
public class Stats {

    /** Código de cada tesoro encontrado (con parte) → veces encontrado. */
    private final HashMap<String, Integer> tesoros = new HashMap<>();

    /** Código de cada región → veces que se ha excavado allí. */
    private final HashMap<String, Integer> lugares = new HashMap<>();

    /**
     * Registra una excavación: suma una visita a la región y cada tesoro
     * encontrado.
     * Se llama desde la excavación al terminar.
     * 
     * @param region  La región excavada.
     * @param codigos Los códigos completos de los tesoros encontrados
     *                
     */
    public void registrar(Region region, List<String> codigos) {
        lugares.merge(region.getCodigo(), 1, Integer::sum);
        for (String codigo : codigos) {
            tesoros.merge(codigo, 1, Integer::sum); 
                                                  
        }
    }

     /**
     * Muestra, por cada región, las veces que se ha visitado, los tesoros distintos encontrados
     * frente al total (con porcentaje) y el total de tesoros excavados.
     * <p>
     * Cada parte de un tesoro cuenta como un tesoro distinto.
     * </p>
     */
    public void mostrarLugares() {
        for (Region r : RegionRepository.getTodas()) {
            int distintos = 0; // códigos distintos encontrados en esa región
            int total = 0; // suma de las veces que se han encontrado
            for (String codigo : tesoros.keySet()) {
                if (CodeHelper.getRegion(codigo).equals(r.getCodigo())) {
                    distintos++;
                    total += tesoros.get(codigo);
                }
            }
            int posibles = r.getNumPartes(); // cada parte cuenta como un tesoro distinto

            System.out.println("-------------------- " + r.getNombre() + " --------------------");
            System.out.println("Veces visitada: " + lugares.getOrDefault(r.getCodigo(), 0));// busca en el mapa lugares
                                                                                            // cuántas veces has
                                                                                            // visitado esa región, y si
                                                                                            // nunca has ido devuelve 0.
            System.out.println("Tesoros encontrados: " + distintos + "/" + posibles
                    + " (" + UtilsHelper.getPercent(distintos, posibles) + "%)");
            System.out.println("Total de tesoros excavados: " + total);
        }
    }

    /**
     * Muestra los tesoros encontrados divididos por región y ordenados por código.
     * Las regiones sin tesoros encontrados no se muestran.
     * Si todavía no se ha encontrado ninguno, lo indica.
     */
    public void mostrarPorRegion() {
        List<String> codigos = codigosOrdenados();
        if (codigos.isEmpty()) {
            System.out.println("Todavía no has encontrado tesoros.");
            return;
        }
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

    /**
     * Muestra los tesoros encontrados divididos por tipo (ordenados alfabéticamente)
     * y, dentro de cada tipo, ordenados por código.
     * Si todavía no se ha encontrado ninguno, lo indica.
     */
    public void mostrarPorTipo() {
        List<String> codigos = codigosOrdenados();

        
        List<String> tipos = new ArrayList<>();
        for (String codigo : codigos) {
            String tipo = CodeHelper.getType(codigo);
            if (!tipos.contains(tipo)) {
                tipos.add(tipo);
            }
        }
        Collections.sort(tipos);

        for (String tipo : tipos) {
            
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

       /**
     * Devuelve los códigos de los tesoros encontrados ordenados alfabéticamente,
     * porque el {@link HashMap} no guarda ningún orden.
     *
     * @return Una lista nueva con los códigos ordenados (vacía si no hay ninguno).
     */
    private List<String> codigosOrdenados() {
        List<String> codigos = new ArrayList<>(tesoros.keySet());
        Collections.sort(codigos);
        return codigos;
    }

     /**
     * Muestra una lista de códigos agrupados por tesoro.
     * <ul>
     *   <li>Si el tesoro tiene una sola parte: {@code -Nombre: veces}</li>
     *   <li>Si tiene varias: {@code -Nombre:} y debajo cada parte con cuatro espacios</li>
     * </ul>
     * Los códigos que no corresponden a ningún tesoro registrado se ignoran.
     *
     * @param codigos Los códigos a mostrar, ya ordenados.
     */
    private void mostrarGrupo(List<String> codigos) {
        String tesoroActual = "";
        for (String codigo : codigos) {
            Treasure t = RegionRepository.getTesoro(codigo);
            if (t == null) {
                continue; // código que no corresponde a ningún tesoro registrado
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