package commons;

import commons.operations.ArcheologistsOperation;
import commons.operations.AtlasOperation;
import commons.operations.CatalagoTesoros;
import commons.operations.SkillsOperation;
import commons.repositories.RegionRepository;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import people.Archeologist;
import regions.civilizaciones.Greece;
import regions.civilizaciones.Iberia;
import regions.civilizaciones.Rome;
import stats.Stats;

public class Archaios {
    private static final String CODIGO_GRUPO = "2DAM2627";
    private final Console console = System.console();
    private String uid;
    private String nombrePartida;
    private int puntos;
    private List<Archeologist> arqueologos = new ArrayList<>();
    private Stats stats = new Stats();

     private ArcheologistsOperation arqueologosOp;
    private SkillsOperation habilidades;
    //private LicensesOperation licencias;
    private AtlasOperation atlas;
    private CatalagoTesoros catalogo;
    //private ExcavateOperation excavar;
    //private StatsOperation estadisticas;
    // private Stats stats;
      public static void main(String[] args) {
        Archaios juego = new Archaios();
        juego.preInit();   
        juego.init();      
        juego.run();       
        juego.end();       
    }

    /**
     * Resgrita regiones y habilidades antes de arrancar
     */
    private void preInit() {
        RegionRepository.registrar(new Greece());
        RegionRepository.registrar(new Iberia());
        RegionRepository.registrar(new Rome());
       

        // Habilidades: cuando tengas las subclases de Skill
        // SkillRepository.registrar(...);
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
     * Crea la partida y las operaciones del menu
     */
     private void init() {
        // Cargar partidas guardadas: para la entrega del guardado

        uid = console.readLine("Código de usuario: ");
        nombrePartida = console.readLine("Nombre de la partida: ");
        String nombreArq = console.readLine("Nombre del primer arqueólogo: ");
        arqueologos.add(new Archeologist(nombreArq));

        arqueologosOp = new ArcheologistsOperation(this);
        habilidades = new SkillsOperation();
        //licencias = new LicensesOperation(this);
        atlas = new AtlasOperation();
        catalogo = new CatalagoTesoros();
        //excavar = new ExcavateOperation();
        //estadisticas = new StatsOperation(this);
    }

    /**
     * Muestra texto del menu
     */
    private void menu() {
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
    
        private void run() {
        int op;
        do {
            menu();
            op = Integer.parseInt(console.readLine("Opción: "));
            switch (op) {
                case 1: arqueologosOp.operation(); break;
                //case 2: habilidades.operation(); break;
                //case 3: licencias.operation(); break;
                case 4: atlas.operation(); break;
                case 5: catalogo.operation(); break;
                //case 6: excavar.operation(); break;
                //case 7: estadisticas.operation(); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (op != 0);
    }
    

    private void save() {

    }

    private void end() {
        System.out.println("¡Hasta pronto!");
    }
    

    public ArcheologistsOperation getArqueologosOp() {
        return arqueologosOp;
    }

    public SkillsOperation getHabilidades() {
        return habilidades;
    }

    public AtlasOperation getAtlas() {
        return atlas;
    }

    public CatalagoTesoros getCatalogo() {
        return catalogo;
    }

    /*public StatsOperation getEstadisticas() {
        return estadisticas;
    }*/
      public Stats getStats() {
        return stats;
    }

    public void addPuntos(int cantidad) {
        puntos += cantidad;
    }
   

}
