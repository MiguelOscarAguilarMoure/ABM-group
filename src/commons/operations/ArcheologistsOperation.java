package commons.operations;

import commons.Archaios;
import java.io.Console;
import java.util.List;
import java.util.Random;
import people.Archeologist;

public class ArcheologistsOperation implements  IOperation{
    private final  static int COSTE_CONTRATAR=50;
    private  Archaios juego;
    private final Console console = System.console();
    private final Random random = new Random();

    public ArcheologistsOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
      return "Arqueólogos";
    }

    @Override
    public void operation() {
      
      int op;
      do { 
           System.out.println("1. Ver arqueólogos");
        System.out.println("2. Contratar");
        System.out.println("3. Licenciar");
        System.out.println("0. Volver");
        op = Integer.parseInt(console.readLine("Opción: "));

        if (op == 1) ver();
        if (op == 2) contratar();
        if (op == 3) licenciar();
      } while (op!=0);
    }
    private void ver() {
        System.out.println("(Ver arqueólogos, por hacer)");
    }
 
    private void contratar() {
        System.out.println("(Contratar, por hacer)");
    }
 
    private void licenciar() {
        System.out.println("(Licenciar, por hacer)");
    }

    //Metedo auxiliar para listar arqueologos
    private Archeologist eligirArqueologo(){
        List <Archeologist> lista = juego.getArqueologos();
        for(int i =0 ; i < lista.size() ; i++){
            Archeologist a = lista.get(i);
             System.out.println((i + 1) + ". " + a.getNombre() + " (nivel " + a.getNivel() + ")");
        }
        System.out.println("0. Volver");
         int op = Integer.parseInt(console.readLine("Arqueólogo: "));
          if (op < 1 || op > lista.size()) {
            return null;
        }
        return lista.get(op - 1);
    }
}
