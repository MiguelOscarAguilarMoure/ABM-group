package commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import regions.Region;
import skills.Skill;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gestor de persistencia y registro de eventos mediante GSON en Saves > Save y Saves > log.
 *
 * @author Alberto
 * @version 1.0
 */
public class SaveManager {

    private static final String SAVE_DIR = "Saves" + File.separator + "Save";
    private static final String LOG_DIR = "Saves" + File.separator + "log";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Skill.class, new SkillAdapter())
            .registerTypeAdapter(Region.class, new RegionAdapter())
            .create();

    /**
     * Guarda el estado de la partida en formato JSON y registra el evento en el log.
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

            registrarLog(archaios.getNombrePartida(), "Guardado de partida realizado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga una partida existente desde su archivo JSON.
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
            registrarLog(nombrePartida, "Partida cargada exitosamente.");
            return archaios;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra un mensaje con marca de tiempo en el archivo log de la partida.
     *
     * @param nombrePartida Nombre de la partida.
     * @param mensaje       Mensaje a registrar.
     */
    public static void registrarLog(String nombrePartida, String mensaje) {
        try {
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = LOG_DIR + File.separator + nombrePartida + ".log";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = timestamp + " " + mensaje + System.lineSeparator();

            Files.write(Paths.get(filePath), logEntry.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}