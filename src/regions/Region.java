package regions;

import helpers.CodeHelper;
import java.util.ArrayList;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Representa una región del entorno del juego en la que pueden encontrarse
 * distintos tesoros. Cada región se identifica por un nombre y un código y
 * mantiene la colección de objetos {@link Treasure} asociados a ella.
 * <p>
 * Esta clase sirve como entidad de dominio para organizar el contenido del
 * escenario, permitiendo consultar, filtrar y analizar los tesoros disponibles
 * en una zona determinada. Su finalidad principal es ofrecer acceso
 * estructurado
 * a la información de cada región sin modificar la lógica interna del programa.
 *
 * @author Miguel Aguilar
 * @version 1.0
 * @since 2026-10-18
 */
public class Region {
    protected final String nombre;
    protected final String codigo;
    protected ArrayList<Treasure> treasures = new ArrayList<>();

    public Region(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre de la región.
     *
     * @return el nombre identificativo de la región.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el código único identificativo de la región.
     *
     * @return el código de la región.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Se obtiene una lista completa de tesoros asociados a la región.
     * 
     * Se devuelve una copia de la colección interna para evitar que el exterior
     * modifique la estructura original sin control.
     *
     * @return una nueva lista con todos los tesoros almacenados en la región.
     */
    public ArrayList<Treasure> getTesoros() {
        return new ArrayList<>(treasures);
    }

    /**
     * Obtiene los nombres de todos los tesoros contenidos en la región.
     *
     * @return un array con los nombres de cada tesoro presente en la región.
     */
    public String[] getNombreTesoros() {
        String[] nombres = new String[treasures.size()];
        for (int i = 0; i < treasures.size(); i++) {
            nombres[i] = treasures.get(i).getNombre();
        }
        return nombres;
    }

    /**
     * Busca en la región todos los tesoros que pertenecen a un tipo concreto.
     * 
     * El parámetro recibido representa el código del tipo que se desea filtrar
     * y se compara con la clasificación asociada a cada tesoro.
     *
     * @param codigoTesoro El código del tipo de tesoro que se desea consultar.
     * @return una lista con los tesoros que coinciden con ese tipo.
     */
    public ArrayList<Treasure> getTiposTesoros(String codigoTesoro) {
        ArrayList<Treasure> tesorosPorTipo = new ArrayList<>();
        for (Treasure t : treasures) {
            if (CodeHelper.getType(t.getCodigo()).equals(codigoTesoro)) {
                tesorosPorTipo.add(t);
            }
        }
        return tesorosPorTipo;
    }

    /**
     * Filtra los tesoros de la región por su tipo enumerado.
     *
     * @param codigo tipo de tesoro que se desea localizar.
     * @return una lista con los tesoros que pertenecen a ese tipo.
     */
    public ArrayList<Treasure> getTesorosPorTipo(ETreasureType codigo) {
        return getTiposTesoros(codigo.getCode());

    }

    /**
     * Obtiene todos los tesoros de la región que tienen una rareza determinada.
     *
     * @param rareza rareza que se desea filtrar.
     * @return una lista con los tesoros de esa rareza.
     */
    public ArrayList<Treasure> getTesorosPorRareza(ETreasureRarity rareza) {
        ArrayList<Treasure> listaTesoros = new ArrayList<>();
        for (Treasure t : treasures) {
            if (t.getRarezaTesoro().equals(rareza)) {
                listaTesoros.add(t);
            }
        }
        return listaTesoros;
    }

    /**
     * Busca un tesoro en la colección de tesoros de la región
     * utilizando su código identificador.
     *
     * Si encuentra una coincidencia, devuelve el objeto Treasure
     * almacenado en la colección, sin crear una copia.
     * Si no encuentra ninguna coincidencia, devuelve null.
     *
     * @param codigoTesoro código identificador del tesoro que se busca.
     * @return el objeto Treasure encontrado o null si no existe.
     */
    public Treasure getTesoro(String codigoTesoro) {
        for (Treasure t : treasures) {
            if (t.getCodigo().equals(codigoTesoro)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Selecciona aleatoriamente un tesoro de la región.
     * Si la colección está vacía, el método devuelve null para indicar que no
     * hay ningún tesoro disponible.
     *
     * @return un tesoro aleatorio de la región o null si no hay ninguno.
     */
    public Treasure getTesoroAleatorio() {
        if (treasures.size() == 0) {
            return null;
        }
        int index = (int) (Math.random() * treasures.size());
        return treasures.get(index);
    }

    /**
     * Selecciona aleatoriamente un tesoro perteneciente a una rareza dada.
     *
     * @param rareza la rareza de los tesoros que se desea escoger.
     * @return un tesoro aleatorio de la rareza indicada o null si no existe.
     */
    public Treasure getTesoroAleatorioPorRareza(ETreasureRarity rareza) {
        ArrayList<Treasure> listaTesoros = getTesorosPorRareza(rareza);
        if (listaTesoros.size() == 0) {
            return null;
        }
        int index = (int) (Math.random() * listaTesoros.size());
        return listaTesoros.get(index);
    }

    /**
     * Calcula el número total de partes que contienen todos los tesoros de la
     * región.
     *
     * @return la suma de las partes de cada tesoro almacenado.
     */
    public int getNumPartes() {
        int cantidad = 0;
        for (Treasure t : treasures) {
            cantidad += t.getPartes();
        }
        return cantidad;
    }

    /**
     * Determina si la región requiere una licencia para acceder a su contenido.
     * 
     * En esta version inicial,  el valor devuelto es false, por lo que la región no
     * exige ningún tipo de licencia adicional para su uso.
     *
     * @return true si necesita licencia, false en caso contrario.
     */
    public boolean necesitaLicencia() {
        return false;
    }

}
