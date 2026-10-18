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

/**
 * Coordina el ciclo de vida y las operaciones principales de una partida de Archaios.
 *
 * @author Borja Abal
 * @author Alberto Ameneiros
 * @since 2026-10-18
 * @version 1.0
 */
public class Archaios {
    /** Código identificador del equipo. */
    private static final String CODIGO_GRUPO = "2DAM2627";

    /** Identificador del usuario de la partida. */
    private String uid;

    /** Nombre de la partida actual. */
    private String nombrePartida;

    /** Puntos acumulados durante la partida. */
    private int puntos;

    /** Arqueólogos que participan en la partida. */
    private List<Archeologist> arqueologos = new ArrayList<>();

    /** Estadísticas acumuladas de la partida. */
    private Stats stats = new Stats();   // una sola para toda la partida

    /** Operaciones de gestión de arqueólogos. */
    private ArcheologistsOperation arqueologosOp;

    /** Operaciones de gestión de habilidades. */
    private SkillsOperation habilidades;

    /** Operaciones de gestión de licencias. */
    private LicenseOperation licencias;

    /** Operaciones de consulta del atlas. */
    private AtlasOperation atlas;

    /** Operaciones del catálogo de tesoros. */
    private CatalagoTesoros catalogo;

    /** Operaciones de excavación. */
    private ExcavateOperation excavar;

    /** Operaciones de consulta de estadísticas. */
    private StatsOperation estadisticas;

    /**
     * Inicia, ejecuta y finaliza una partida.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Archaios juego = new Archaios();
        juego.preInit();
        juego.init();
        juego.run();
        juego.end();
    }

    /**
     * Registra las regiones y carga las habilidades antes de iniciar la partida.
     */
    private void preInit() {
        RegionRepository.registrar(new Greece());
        RegionRepository.registrar(new Iberia());
        RegionRepository.registrar(new Rome());
        SkillRepository.inicializar();
    }

    /**
     * Devuelve el código identificador del grupo desarrollador.
     *
     * @return código del grupo
     */
    public static String getCodigoGrupo() {
        return CODIGO_GRUPO;
    }


    /**
     * Devuelve el identificador del usuario de la partida.
     *
     * @return identificador del usuario
     */
    public String getUid() {
        return uid;
    }

    /**
     * Devuelve el nombre de la partida.
     *
     * @return nombre de la partida
     */
    public String getNombrePartida() {
        return nombrePartida;
    }

    /**
     * Devuelve los puntos acumulados.
     *
     * @return la puntuación actual
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Devuelve la lista de arqueólogos de la partida.
     *
     * @return arqueólogos participantes
     */
    public List<Archeologist> getArqueologos() {
        return arqueologos;
    }

    /**
     * Solicita los datos iniciales, crea el primer arqueólogo y prepara las operaciones.
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

    /**
     * Muestra las opciones disponibles en el menú principal.
     */
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
     * Ejecuta el menú principal hasta que se elige salir.
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

    /**
     * Reserva el punto de extensión para guardar el estado de la partida (futura implementacion).
     */
    private void save() {

    }

    /**
     * Muestra el mensaje de despedida al terminar la partida.
     */
    private void end() {
        System.out.println("¡Hasta pronto!");
    }

    /**
     * Devuelve las operaciones de gestión de arqueólogos.
     *
     * @return operaciones de arqueólogos
     */
    public ArcheologistsOperation getArqueologosOp() {
        return arqueologosOp;
    }

    /**
     * Devuelve las operaciones de gestión de habilidades.
     *
     * @return operaciones de habilidades
     */
    public SkillsOperation getHabilidades() {
        return habilidades;
    }

    /**
     * Devuelve las operaciones del atlas.
     *
     * @return operaciones del atlas
     */
    public AtlasOperation getAtlas() {
        return atlas;
    }

    /**
     * Devuelve las operaciones del catálogo de tesoros.
     *
     * @return operaciones del catálogo
     */
    public CatalagoTesoros getCatalogo() {
        return catalogo;
    }

    /**
     * Devuelve las estadísticas de la partida.
     *
     * @return estadísticas acumuladas
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Añade puntos a la puntuación actual.
     *
     * @param cantidad puntos que se añadirán
     */
    public void addPuntos(int cantidad) {
        puntos += cantidad;
    }

    /**
     * Muestra las opciones auxiliares de prueba, accesibles desde la opción 67.
     */
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

    /**
     * Solicita una cantidad de puntos y la suma a la partida.
     */
    private void pruebaPuntos() {
        int cantidad = InputHelper.leerEntero("Puntos a sumar: ");
        addPuntos(cantidad);
        System.out.println("Ahora tienes " + puntos + " puntos.");
    }

    /**
     * Añade experiencia a un arqueólogo y permite asignarle habilidades o especialización.
     */
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

    /**
     * Permite seleccionar un arqueólogo y asignarle una habilidad.
     */
    private void pruebaHabilidad() {
        Archeologist a = pruebaElegirArqueologo();
        if (a != null) {
            pruebaDarHabilidad(a);
        }
    }

    /**
     * Muestra las habilidades válidas para el arqueólogo y asigna la elegida.
     *
     * @param a arqueólogo que aprenderá la habilidad
     */
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

    /**
     * Registra cinco tesoros aleatorios de una región en las estadísticas de prueba.
     */
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

    /**
     * Muestra los arqueólogos disponibles y solicita cuál seleccionar.
     *
     * @return arqueólogo seleccionado, o {@code null} si se vuelve al menú
     */
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