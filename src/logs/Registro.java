package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registros en fichero del sistema: tesoros encontrados y errores.
 * <p>
 * Hay dos registros, ambos en texto y con una marca de tiempo al principio de
 * cada línea ({@code [aaaa-mm-dd hh:mm:ss]}):
 * </p>
 * <ul>
 * <li><b>Registro de tesoros</b>: uno por partida, en
 * {@code log/register/<partida>.log}. Al crear la partida se anota
 * "Inicio de la partida." y después una línea por cada tesoro encontrado
 * ({@code RRR_TTT_NNN_PPPP}).</li>
 * <li><b>Registro de errores</b>: uno solo, compartido por todas las partidas, en
 * {@code log/errors.log}. Cada excepción se anota con su descripción y, debajo,
 * su pila de llamadas.</li>
 * </ul>
 * <p>
 * Escribir en el registro nunca debe tumbar el juego: si el fichero no se puede
 * escribir, se avisa por {@code System.err} y se sigue.
 * </p>
 *
 * @author Miguel Aguilar Moure
 * @version 1.0
 * @since 2026-11-15
 */
public final class Registro {
    /** Carpeta de los registros de tesoros (uno por partida). */
    private static final String CARPETA_PARTIDAS = "log/register";
    /** Fichero compartido de errores. */
    private static final String FICHERO_ERRORES = "log/errors.log";
    /** Extensión de los ficheros de partida. */
    private static final String EXTENSION = ".log";
    /** Formato de la marca de tiempo. */
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Clase de métodos estáticos: no se crean objetos de ella. */
    private Registro() {
    }

    /**
     * Crea el registro de tesoros de una partida y anota su inicio.
     * Si el fichero ya existe, se añade la línea al final.
     *
     * @param nombrePartida El nombre de la partida (no puede estar vacío).
     * @throws IllegalArgumentException si el nombre es {@code null} o está vacío.
     */
    public static void iniciarPartida(String nombrePartida) {
        escribir(rutaPartida(nombrePartida), "Inicio de la partida.");
    }

    /**
     * Anota un tesoro encontrado en el registro de la partida.
     *
     * @param nombrePartida El nombre de la partida.
     * @param codigo        El código del tesoro con su parte (por ejemplo "GRC_CER_001_0003").
     * @throws IllegalArgumentException si el nombre de la partida o el código son {@code null} o están vacíos.
     */
    public static void registrarTesoro(String nombrePartida, String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del tesoro no puede estar vacío.");
        }
        escribir(rutaPartida(nombrePartida), codigo.trim());
    }

    /**
     * Anota una excepción en el registro de errores: la descripción y, debajo, su pila.
     *
     * @param descripcion Qué estaba pasando cuando ocurrió (si es {@code null} se usa el mensaje de la excepción).
     * @param error       La excepción (no puede ser {@code null}).
     * @throws IllegalArgumentException si la excepción es {@code null}.
     */
    public static void registrarError(String descripcion, Throwable error) {
        if (error == null) {
            throw new IllegalArgumentException("La excepción no puede ser nula.");
        }
        String texto = descripcion;
        if (texto == null || texto.isBlank()) {
            texto = (error.getMessage() != null) ? error.getMessage() : error.getClass().getName();
        }
        escribir(FICHERO_ERRORES, texto + System.lineSeparator() + pila(error));
    }

    /**
     * Anota una excepción usando su propio mensaje como descripción.
     *
     * @param error La excepción (no puede ser {@code null}).
     * @throws IllegalArgumentException si la excepción es {@code null}.
     */
    public static void registrarError(Throwable error) {
        registrarError(null, error);
    }

    // AUXILIARES

    /**
     * Calcula la ruta del registro de una partida. Los caracteres que no valen en un
     * nombre de fichero (como {@code /} o {@code :}) se cambian por {@code _}.
     *
     * @param nombrePartida El nombre de la partida.
     * @return La ruta del fichero, por ejemplo {@code log/register/Mi partida.log}.
     */
    private static String rutaPartida(String nombrePartida) {
        if (nombrePartida == null || nombrePartida.isBlank()) {
            throw new IllegalArgumentException("El nombre de la partida no puede estar vacío.");
        }
        String limpio = nombrePartida.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
        return CARPETA_PARTIDAS + "/" + limpio + EXTENSION;
    }

    /**
     * Convierte la pila de llamadas de una excepción en texto.
     *
     * @param error La excepción.
     * @return La excepción y su pila, sin salto de línea al final.
     */
    private static String pila(Throwable error) {
        StringWriter texto = new StringWriter();
        PrintWriter escritor = new PrintWriter(texto);
        error.printStackTrace(escritor);
        escritor.flush();
        return texto.toString().stripTrailing();
    }

    /**
     * Añade una línea con la marca de tiempo al final de un fichero, creando antes
     * sus carpetas si hace falta. Si falla, avisa por {@code System.err} sin lanzar
     * excepción (no se usa el propio registro de errores para no entrar en bucle).
     *
     * @param ruta  La ruta del fichero.
     * @param texto El texto de la línea (puede tener varias líneas, como la pila).
     */
    private static void escribir(String ruta, String texto) {
        File fichero = new File(ruta);
        File carpeta = fichero.getParentFile();
        if (carpeta != null && !carpeta.exists() && !carpeta.mkdirs() && !carpeta.exists()) {
            System.out.println("No se ha podido crear la carpeta de registros " + carpeta.getPath() + ".");
            return;
        }

        String linea = "[" + LocalDateTime.now().format(FORMATO_FECHA) + "] " + texto;
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fichero, true), StandardCharsets.UTF_8))) {
            escritor.write(linea);
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el registro " + ruta + ": " + e.getMessage());
        }
    }
    
}
