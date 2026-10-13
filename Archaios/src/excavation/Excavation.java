package excavation;
// TODO Dejar la clase preparada base de excavacion y que modifique la excavacion las habilidades del arqueologo y la rareza de los tesoros encontrados.
// Y modificar algunas coordenadas.

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import regiones.Region;

public class Excavation {
    private static final char[] LETRAS={'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z'};
    private final ETerrainSize terreno;
    private final Region region;
    // private final Archeologist arqueologo; // TODO Añadir el arqueologo que realiza la excavacion.
    private  ArrayList<String> casillas= new ArrayList<>();
    private ArrayList<String> enterrados= new ArrayList<>();    
    private HashMap<Integer, String> tesoros= new HashMap<>();
    private int tesorosRestantes=0;
    private int cavarRestantes=0;
    private int acciones=0;
    private String cabecera;
    private ArrayList<String> columnas= new ArrayList<>();
    private String col_espacio;
    private String col_margen;

    private Excavation(ETerrainSize terreno, Region region)// TODO Añadir el arqueologo que realiza la excavacion.
    {
        this.terreno = terreno;
        this.region = region;
    }
    /*
    private void generarCuadricula() {
        int longitudEtiquetas = terreno.getWidth();
    }
         */
    // TODO añadir excavacion builder para que se pueda crear una excavacion con un arqueologo y una region y un terreno.
    public int getTesorosRestantes() {
        return tesorosRestantes;
    }
    //public int getNumeroPosiciones() {
       // return tiles.size();
    //}
    public int getPosicionesRestantes() {
        return cavarRestantes;
    }


  private void generateGrid() {
    
}
private void generarTesoros(){
    

}
    

}
