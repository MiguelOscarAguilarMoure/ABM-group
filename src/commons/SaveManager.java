package commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import regions.Region;
import skills.Skill;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gestor de persistencia, guardado de partidas (.save) y registros (logs de tesoros y errores).
 *
 * @author Alberto
 * @version 2.0
 */
public class SaveManager {

    private static final String SAVE_DIR = "Saves" + File.separator + "Save";
    private static final String REGISTER_LOG_DIR = "log" + File.separator + "register";
    private static final String GLOBAL_LOG_DIR = "log";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Skill.class, new SkillAdapter())
            .registerTypeAdapter(Region.class, new RegionAdapter())
            .create();

    /**
     * Guarda el estado de la partida en formato JSON con extensión .save.
     *
     * @param archaios Partida actual a guardar.
     */
    public static void guardarPartida(Archaios archaios) {
        if (archaios == null || archaios.getNombrePartida() == null) {
            return;
        }
        try {
            File dir = new File(SAVE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = archaios.getNombrePartida() + ".save";
            File file = new File(dir, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(archaios, writer);
            }

            registrarLogGeneral(archaios.getNombrePartida(), "Guardado de partida realizado correctamente.");
        } catch (Exception e) {
            registrarError(e);
        }
    }

    /**
     * Carga una partida existente desde su archivo .save.
     *
     * @param nombrePartida Nombre de la partida.
     * @return Instancia de {@link Archaios} restaurada, o {@code null} si no existe.
     */
    public static Archaios cargarPartida(String nombrePartida) {
        String filePath = SAVE_DIR + File.separator + nombrePartida + ".save";
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Archaios archaios = gson.fromJson(reader, Archaios.class);
            registrarLogGeneral(nombrePartida, "Partida cargada exitosamente.");
            return archaios;
        } catch (Exception e) {
            registrarError(e);
            return null;
        }
    }

    /**
     * Inicializa el registro de una nueva partida creando el fichero en log/register/[partida].log
     * y añadiendo la línea de inicio.
     *
     * @param nombrePartida Nombre de la partida.
     */
    public static void inicializarRegistroPartida(String nombrePartida) {
        registrarLogGeneral(nombrePartida, "Inicio de la partida.");
    }

    /**
     * Registra un tesoro encontrado en el fichero de la partida: [yyyy-mm-dd hh:mm:ss] RRR_TTT_NNN_PPPP
     *
     * @param nombrePartida Nombre de la partida.
     * @param codigoTesoro  Código completo del tesoro encontrado (ej. RRR_TTT_NNN_PPPP).
     */
    public static void registrarTesoro(String nombrePartida, String codigoTesoro) {
        try {
            File dir = new File(REGISTER_LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = REGISTER_LOG_DIR + File.separator + nombrePartida + ".log";
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String logEntry = "[" + timestamp + "] " + codigoTesoro + System.lineSeparator();

            Files.write(Paths.get(filePath), logEntry.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            registrarError(e);
        }
    }

    /**
     * Registra un evento general/informativo en el log de la partida dentro de log/register.
     *
     * @param nombrePartida Nombre de la partida.
     * @param mensaje       Mensaje descriptivo.
     */
    public static void registrarLogGeneral(String nombrePartida, String mensaje) {
        try {
            File dir = new File(REGISTER_LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = REGISTER_LOG_DIR + File.separator + nombrePartida + ".log";
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String logEntry = "[" + timestamp + "] " + mensaje + System.lineSeparator();

            Files.write(Paths.get(filePath), logEntry.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            registrarError(e);
        }
    }

    /**
     * Registra una excepción y su pila de trazabilidad en el archivo global log/errors.log.
     *
     * @exception e La excepción ocurrida.
     */
    public static void registrarError(Exception e) {
        try {
            File dir = new File(GLOBAL_LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = GLOBAL_LOG_DIR + File.separator + "errors.log";
            String timestamp = LocalDateTime.now().format(FORMATTER);

            // Obtener la pila de trazabilidad (stack trace) como texto
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            String logEntry = "[" + timestamp + "] " + e.getMessage() + System.lineSeparator()
                    + stackTrace + System.lineSeparator();

            Files.write(Paths.get(filePath), logEntry.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}