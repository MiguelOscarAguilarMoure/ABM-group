package regions;

import java.util.ArrayList;
import java.util.List;

import helpers.CodeHelper;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;




/**
 * @author Miguel Aguilar
 * La clase Region representa una región en el juego Archaios. 
 * Cada región tiene un nombre, un código y una lista de tesoros asociados. 
 * Proporciona métodos para acceder a los tesoros por tipo, rareza y obtener tesoros aleatorios.
 */
public abstract class Region {
    protected final String nombre;
    protected ArrayList<Treasure> treasures = new ArrayList<>();


    public Region(String nombre) {
        this.nombre = nombre;
        
    }
    public abstract String getCodigo();
    public String getNombre() {
        return nombre;
    }
    
    public List<Treasure> getTesoros() {
        return List.copyOf(treasures);
    }

    public String [] getNombreTesoros() {
        String[] nombres = new String[treasures.size()];
        for (int i = 0; i < treasures.size(); i++) {
            nombres[i] = treasures.get(i).getNombre();
        }
        return nombres;
    }
    public ArrayList<Treasure> getTiposTesoros(String codigoTesoro) {
        ArrayList<Treasure> tesorosPorTipo = new ArrayList<>();
        for (Treasure t : treasures) {
            if(CodeHelper.getType(t.getCodigo()).equals(codigoTesoro)) {
                tesorosPorTipo.add(t);
            }
        }
        return tesorosPorTipo;
    }
    public ArrayList<Treasure>getTesorosPorTipo(ETreasureType codigo){
        return getTiposTesoros(codigo.getCode());

    }
    public ArrayList<Treasure> getTesorosPorRareza(ETreasureRarity rareza){
        ArrayList<Treasure> listaTesoros = new ArrayList<>();
        for (Treasure t : treasures) {
            if(t.getRarezaTesoro().equals(rareza)) {
                listaTesoros.add(t);
            }
        }
        return listaTesoros;
    }
    public Treasure getTesoro(String codigoTesoro) {
        for (Treasure t : treasures) {
            if(t.getCodigo().equals(codigoTesoro)) {
                return t;
            }
        }
        return null;
    }

    public Treasure getTesoroAleatorio() {
        if(treasures.size() == 0) {
            return null;
        }
        int index = (int) (Math.random() * treasures.size());
        return treasures.get(index);
    }
    public Treasure getTesoroAleatorioPorRareza(ETreasureRarity rareza) {
        ArrayList<Treasure> listaTesoros = getTesorosPorRareza(rareza);
        if(listaTesoros.size() == 0) {
            return null;
        }
        int index = (int) (Math.random() * listaTesoros.size());
        return listaTesoros.get(index);
    }

    public int getNumPartes(){
        int cantidad=0;
        for(Treasure t: treasures){
            cantidad += t.getPartes();
        }
        return  cantidad;
    }




   
}