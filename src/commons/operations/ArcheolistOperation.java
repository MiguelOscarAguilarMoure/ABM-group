package commons.operations;

import java.io.Console;

public class ArcheolistOperation implements  IOperation{

    @Override
    public String getName() {
      return "Arqueólogos";
    }

    @Override
    public void operation() {
      Console console = System.console();
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
}
