package regions;

import java.util.ArrayList;
import java.util.List;
import treasures.ETreasureRarity;
import treasures.Treasure;

public abstract class Region {
    private final String nombre;
    private  ArrayList<Treasure> listaTesoros = new ArrayList<>();

     public Region(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Método para obtener el codigo de la region
     * @return
     */
     public abstract String getCodigo();

     public String getNombre() {
        return nombre;
    }

    public List<Treasure> getTesoros(ETreasureRarity rareza) {
        List<Treasure> resultado = new ArrayList<>();
        for (Treasure t : listaTesoros) {
            if (t.getRarezaTesoro() == rareza) {
                resultado.add(t);
            }
        }
        return resultado;
    }


}
