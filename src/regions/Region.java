package regions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import treasures.ETreasureRarity;
import treasures.Treasure;

public abstract class Region {
    private static final Random random = new Random();
    private final String nombre;
    protected ArrayList<Treasure> listaTesoros = new ArrayList<>();

    public Region(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para obtener el codigo de la region
     * 
     * @return
     */
    public abstract String getCodigo();

    public String getNombre() {
        return nombre;
    }
     /**
     * Devuelve todos los tesoros de la región.
     * @return Una copia de la lista de tesoros.
     */
    public List<Treasure> getTesoros() {
        return List.copyOf(listaTesoros);
    }

    /**
     * Busca un tesoro de una rareza especifica
     * 
     * @param rareza
     * @return
     */
    public List<Treasure> getTesoros(ETreasureRarity rareza) {
        List<Treasure> resultado = new ArrayList<>();
        for (Treasure t : listaTesoros) {
            if (t.getRarezaTesoro() == rareza) {
                resultado.add(t);
            }
        }
        return resultado;
    }
    /**
     * Devuelve un tesoro aleatorio
     * @return
     */
    public Treasure getTesoroAleatorio() {
        return listaTesoros.get(random.nextInt(listaTesoros.size()));
    }
      /**
     * Devuelve un tesoro aleatorio de una rareza
     * @return
     */
    public Treasure getTesoroAleatorio(ETreasureRarity rareza) {
        List<Treasure> lista = getTesoros( rareza);
        if(lista.isEmpty()){
            return  null;
        }
        return lista.get(random.nextInt(lista.size()));

    }
    /**
     * Busca un tesoro por su código base
     * @param codigo
     * @return
     */
      public Treasure getTesoro(String codigo) {
        for (Treasure t : listaTesoros) {
            if (t.getCodigo().equals(codigo)) {
                return t;
            }
        }
        return null;
    }

}
