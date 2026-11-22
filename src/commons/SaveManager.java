package commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logs.Registro;
import regions.Region;
import skills.Skill;

/**
 * Guardado y carga de partidas en formato JSON (ficheros .save).
 * <p>
 * Los registros (inicio de partida, tesoros y errores) los escribe
 * {@link logs.Registro}.
 * </p>
 *
 * @author Alberto Ameneiros
 * @version 2.1
 */
public class SaveManager {

    /** Carpeta donde se guardan las partidas. */
    private static final String SAVE_DIR = "Saves" + File.separator + "Save";

    /** Extensión de los ficheros de partida. */
    private static final String EXTENSION = ".save";

    /** Gson con los adaptadores para guardar habilidades y regiones por su nombre o código. */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Skill.class, new SkillAdapter())
            .registerTypeAdapter(Region.class, new RegionAdapter())
            .create();

    /**
     * Guarda el estado de la partida en formato JSON, sobrescribiendo el guardado anterior.
     *
     * @param archaios Partida actual a guardar.
     */
    public static void guardarPartida(Archaios archaios) {
        if (archaios == null || archaios.getNombrePartida() == null) {
            return;
        }
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, archaios.getNombrePartida() + EXTENSION);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(archaios, writer);
        } catch (Exception e) {
            Registro.registrarError("No se ha podido guardar la partida " + archaios.getNombrePartida() + ".", e);
        }
    }

    /**
     * Carga una partida existente desde su fichero .save.
     *
     * @param nombrePartida Nombre de la partida.
     * @return La partida restaurada, o {@code null} si no existe o no se ha podido leer.
     */
    public static Archaios cargarPartida(String nombrePartida) {
        File file = new File(SAVE_DIR, nombrePartida + EXTENSION);
        if (!file.exists()) {
            return null;
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Archaios.class);
        } catch (Exception e) {
            Registro.registrarError("No se ha podido cargar la partida " + nombrePartida + ".", e);
            return null;
        }
    }

    /**
     * Devuelve los nombres de las partidas guardadas, es decir, los ficheros
     * .save de la carpeta de guardado.
     *
     * @return Los nombres ordenados alfabéticamente, sin la extensión;
     *         una lista vacía si no hay ninguna.
     */
    public static List<String> listarPartidas() {
        List<String> nombres = new ArrayList<>();
        File[] ficheros = new File(SAVE_DIR).listFiles();
        if (ficheros == null) {
            return nombres;   // la carpeta todavía no existe
        }
        for (File f : ficheros) {
            String nombre = f.getName();
            if (f.isFile() && nombre.endsWith(EXTENSION)) {
                nombres.add(nombre.substring(0, nombre.length() - EXTENSION.length()));
            }
        }
        Collections.sort(nombres);
        return nombres;
    }
}