package commons;

import commons.operations.ArcheologistsOperation;
import commons.operations.AtlasOperation;
import commons.operations.CatalagoTesoros;
import commons.operations.SkillsOperation;
import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository; // 1. IMPORTANTE: Importar el repositorio

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
    private AtlasOperation atlas;
    private CatalagoTesoros catalogo;

    public static void main(String[] args) {
        Archaios juego = new Archaios();
        juego.preInit();
        juego.init();
        juego.run();
        juego.end();
    }

    /**
     * Registra regiones y habilidades antes de arrancar
     */
    private void preInit() {
        RegionRepository.registrar(new Greece());
        RegionRepository.registrar(new Iberia());
        RegionRepository.registrar(new Rome());

        // 2. LLAMADA CORRECTA A LAS HABILIDADES AQUÍ:
        SkillRepository.InicializadosSkills();
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

    private void init() {
        uid = console.readLine("Código de usuario: ");
        nombrePartida = console.readLine("Nombre de la partida: ");
        String nombreArq = console.readLine("Nombre del primer arqueólogo: ");
        arqueologos.add(new Archeologist(nombreArq));

        arqueologosOp = new ArcheologistsOperation(this);
        habilidades = new SkillsOperation();
        atlas = new AtlasOperation();
        catalogo = new CatalagoTesoros();
    }

    // Se eliminó la línea "void InicializadosSkills();" que causaba el error de sintaxis

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

    private void run() {
        int op;
        do {
            menu();
            op = Integer.parseInt(console.readLine("Opción: "));
            switch (op) {
                case 1: arqueologosOp.operation(); break;
                case 2: habilidades.operation(); break;
                case 4: atlas.operation(); break;
                case 5: catalogo.operation(); break;
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

    public Stats getStats() {
        return stats;
    }

    public void addPuntos(int cantidad) {
        puntos += cantidad;
    }
}