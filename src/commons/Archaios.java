package commons;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import people.Archeologist;

public class Archaios {
    private static  final String CODIGO_GRUPO="2DAM2627";
    private final Console console = System.console();
    private String uid;
    private String nombrePartida;
    private int puntos;
    private List<Archeologist> arqueologos = new ArrayList<>();
     // private Stats stats; 


     /**
      * Resgrita regiones y habilidades antes de arrancar
      */
    private  void preInit(){

    }
    public static String getCodigoGrupo() {
        return CODIGO_GRUPO;
    }
     public Console getConsole() {
         return console;
     }
     public String getUid() {
         return uid;
     }
     public String getNombrePartida() {
         return nombrePartida;
     }
     public int getPuntos() {
         return puntos;
     }
     public List<Archeologist> getArqueologos() {
         return arqueologos;
     }
    /**
     * Crea la  partida y las operaciones del menu
    */
    private void init(){

    }
    /**
     * Muestra texto del menu
     */
    private  void menu(){
        System.out.println("-------------------- Archaios --------------------");
        System.out.println("1. Arqueólogos");
        System.out.println("2. Habilidades");
        System.out.println("3. Licencias");
        System.out.println("4. Atlas");
        System.out.println("5. Catalago tesoros");
        System.out.println("6. Excavar");
        System.out.println("7. Estadísticas");
        System.out.println("0. Salir");
    }
    /**
     * Bucle del menú principal
     */
    private  void run(){

    }
     private void save() {

    }
    private void end() {
        System.out.println("¡Hasta pronto!");
    }
    public void addPuntos(int cantidad) {
        puntos += cantidad;
    }

}
