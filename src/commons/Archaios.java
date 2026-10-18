package commons;

import commons.operations.ArcheologistsOperation;
import commons.operations.AtlasOperation;
import commons.operations.CatalagoTesoros;
import commons.operations.ExcavateOperation;
import commons.operations.LicenseOperation;
import commons.operations.SkillsOperation;
import commons.operations.StatsOperation;
import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository; // 1. IMPORTANTE: Importar el repositorio
import helpers.InputHelper;
import java.util.ArrayList;
import java.util.List;
import people.Archeologist;
import regions.Region;
import regions.civilizaciones.Greece;
import regions.civilizaciones.Iberia;
import regions.civilizaciones.Rome;
import skills.Skill;
import stats.Stats;

public class Archaios {
    private static final String CODIGO_GRUPO = "2DAM2627";
     private String uid;
    private String nombrePartida;
    private int puntos;
    private List<Archeologist> arqueologos = new ArrayList<>();
    private Stats stats = new Stats();   // una sola para toda la partida

    private ArcheologistsOperation arqueologosOp;
    private SkillsOperation habilidades;
    private LicenseOperation licencias;
    private AtlasOperation atlas;
    private CatalagoTesoros catalogo;
    private ExcavateOperation excavar;
    private StatsOperation estadisticas;

    public static void main(String[] args) {
        Archaios juego = new Archaios();
        juego.preInit();
        juego.init();
        juego.run();
        juego.end();
    }

     /**
     * Registra regiones y habilidades antes de arrancar.
     */
    private void preInit() {
        RegionRepository.registrar(new Greece());
        RegionRepository.registrar(new Iberia());
        RegionRepository.registrar(new Rome());
        SkillRepository.inicializar();
    }

    public static String getCodigoGrupo() {
        return CODIGO_GRUPO;
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
     * Crea la partida (usuario, nombre y primer arqueólogo) y las operaciones del menú.
     */
    private void init() {
        // Cargar partidas guardadas: para la entrega del guardado
        uid = InputHelper.leerTexto("Código de usuario: ");
        nombrePartida = InputHelper.leerTexto("Nombre de la partida: ");
        String nombreArq = InputHelper.leerTexto("Nombre del primer arqueólogo: ");
        arqueologos.add(new Archeologist(nombreArq));

        arqueologosOp = new ArcheologistsOperation(this);
        habilidades = new SkillsOperation();
        licencias = new LicenseOperation();
        atlas = new AtlasOperation();
        catalogo = new CatalagoTesoros();
        excavar = new ExcavateOperation(this);
        estadisticas = new StatsOperation(this);
    }

    // Se eliminó la línea "void InicializadosSkills();" que causaba el error de sintaxis

    private void menu() {
        System.out.println("-------------------- Archaios --------------------");
        System.out.println("1. Arqueólogos");
        System.out.println("2. Habilidades");
        System.out.println("3. Licencias");
        System.out.println("4. Atlas");
        System.out.println("5. Catálogo de tesoros");
        System.out.println("6. Excavar");
        System.out.println("7. Estadísticas");
        System.out.println("0. Salir");
    }

     /**
     * Bucle del menú principal.
     */
    private void run() {
        int op;
        do {
            menu();
            op = InputHelper.leerEntero("Opción: ");
            switch (op) {
                case 1: arqueologosOp.operation(); break;
                case 2: habilidades.operation(); break;
                case 3: licencias.operation(); break;
                case 4: atlas.operation(); break;
                case 5: catalogo.operation(); break;
                case 6: excavar.operation(); break;
                case 7: estadisticas.operation(); break;
                case 67: menuPruebas(); break;
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
        //  MENÚ DE PRUEBAS (oculto, opción 67)

    /** Atajos para probar el juego sin tener que excavar. No aparece en el menú. */
    private void menuPruebas() {
        int op;
        do {
            System.out.println("==================== PRUEBAS ====================");
            System.out.println("Puntos: " + puntos + " | Arqueólogos: " + arqueologos.size());
            System.out.println("1. Sumar puntos");
            System.out.println("2. Dar experiencia a un arqueólogo");
            System.out.println("3. Dar una habilidad a un arqueólogo");
            System.out.println("4. Meter tesoros de prueba en estadísticas");
            System.out.println("0. Volver");
            op = InputHelper.leerEntero("Opción: ");

            switch (op) {
                case 1: pruebaPuntos(); break;
                case 2: pruebaExperiencia(); break;
                case 3: pruebaHabilidad(); break;
                case 4: pruebaEstadisticas(); break;
                default: break;
            }
        } while (op != 0);
    }

    private void pruebaPuntos() {
        int cantidad = InputHelper.leerEntero("Puntos a sumar: ");
        addPuntos(cantidad);
        System.out.println("Ahora tienes " + puntos + " puntos.");
    }

    private void pruebaExperiencia() {
        Archeologist a = pruebaElegirArqueologo();
        if (a == null) {
            return;
        }
        int exp = InputHelper.leerEntero("Experiencia a dar: ");
        int subidos = a.addExperiencia(exp);
        System.out.println(a.getNombre() + " sube " + subidos + " nivel(es). Ahora es nivel " + a.getNivel() + ".");

        // Una habilidad por cada nivel subido
        for (int i = 0; i < subidos; i++) {
            pruebaDarHabilidad(a);
        }
        // Especialización al llegar a nivel 5
        if (subidos > 0 && a.getNivel() == Archeologist.NIVEL_MAX && a.getEspecializacion() == null) {
            List<Region> regiones = RegionRepository.getTodas();
            for (int i = 0; i < regiones.size(); i++) {
                System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
            }
            int r;
            do {
                r = InputHelper.leerEntero("Especialización: ");
            } while (r < 1 || r > regiones.size());
            a.setEspecializacion(regiones.get(r - 1));
        }
    }

    private void pruebaHabilidad() {
        Archeologist a = pruebaElegirArqueologo();
        if (a != null) {
            pruebaDarHabilidad(a);
        }
    }

    /** Lista las habilidades que puede aprender (dependencias e incompatibilidades) y le da la elegida. */
    private void pruebaDarHabilidad(Archeologist a) {
        List<Skill> posibles = new ArrayList<>();
        for (Skill s : SkillRepository.getTodas()) {
            if (a.puedeAprender(s)) {
                posibles.add(s);
            }
        }
        if (posibles.isEmpty()) {
            System.out.println("No puede aprender más habilidades.");
            return;
        }
        for (int i = 0; i < posibles.size(); i++) {
            System.out.println((i + 1) + ". " + posibles.get(i).getNombre());
        }
        System.out.println("0. Ninguna");
        int op = InputHelper.leerEntero("Habilidad: ");
        if (op >= 1 && op <= posibles.size()) {
            a.addHabilidad(posibles.get(op - 1));
            System.out.println(a.getNombre() + " ha aprendido " + posibles.get(op - 1).getNombre() + ".");
        }
    }

    /** Registra 5 tesoros al azar de una región, como si se hubieran excavado. */
    private void pruebaEstadisticas() {
        List<Region> regiones = RegionRepository.getTodas();
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
        }
        int op = InputHelper.leerEntero("Región: ");
        if (op < 1 || op > regiones.size()) {
            return;
        }
        Region r = regiones.get(op - 1);
        List<String> codigos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            codigos.add(r.getTesoroAleatorio().getCodigoConParte());
        }
        stats.registrar(r, codigos);
        System.out.println("Registrados: " + codigos);
    }

    private Archeologist pruebaElegirArqueologo() {
        for (int i = 0; i < arqueologos.size(); i++) {
            Archeologist a = arqueologos.get(i);
            System.out.println((i + 1) + ". " + a.getNombre() + " (nivel " + a.getNivel() + ")");
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Arqueólogo: ");
        if (op < 1 || op > arqueologos.size()) {
            return null;
        }
        return arqueologos.get(op - 1);
    }
}