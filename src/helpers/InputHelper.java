package helpers;

import java.io.Console;
import java.util.Scanner;

/**
 * Lectura de datos por teclado sin que el programa se caiga si el usuario se
 * equivoca.
 * <p>
 * Toda la lectura del proyecto pasa por esta clase. Usa {@link Console} si el
 * programa
 * se ejecuta desde una terminal y {@link Scanner} si no hay consola (por
 * ejemplo,
 * con el botón Run del IDE, donde {@code System.console()} devuelve
 * {@code null}).
 * Nunca se usan los dos a la vez.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 */
public class InputHelper {
    /** Consola del sistema ({@code null} si no se ejecuta desde una terminal). */
    private static final Console console = System.console();
    /** Lector alternativo para cuando no hay consola. */
    private static final Scanner scanner = new Scanner(System.in);

    /** Clase de métodos estáticos: no se crean objetos de ella. */
    private InputHelper() {
    }

    /**
     * Lee una línea. Usa Console si hay terminal y Scanner si no (Run del IDE).
     * Si se cierra la entrada (Ctrl+D / Ctrl+Z), termina el programa
     * en vez de quedarse en un bucle infinito.
     *
     * @param mensaje El texto que se muestra antes de leer.
     * @return Lo que ha escrito, sin espacios al principio ni al final.
     */
    private static String leer(String mensaje) {
        String texto;

        if (console != null) {
            texto = console.readLine(mensaje);
        } else {
            System.out.print(mensaje);
            if (scanner.hasNextLine()) {
                texto = scanner.nextLine();
            } else {
                texto = null;
            }
        }

        if (texto == null) {
            System.out.println();
            System.out.println("Se ha cerrado la entrada. ¡Hasta pronto!");
            System.exit(0);
        }
        return texto.trim();
    }

    /**
     * Pide un número entero. Si escribe algo que no es un número, vuelve a
     * preguntar.
     * 
     * @param mensaje El texto que se muestra (por ejemplo "Opción: ").
     * @return El número escrito.
     */
    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                numero = Integer.parseInt(leer(mensaje));
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número.");
            }
        }
        return numero;
    }

    /**
     * Lee una línea que puede estar vacía. La usa la excavación en cada turno,
     * que ya comprueba por su cuenta si lo escrito es válido.
     *
     * @return Lo que ha escrito, sin espacios al principio ni al final.
     */
    public static String leerLinea() {
        return leer("");
    }

    /**
     * Pide un texto que no puede estar vacío.
     * 
     * @param mensaje El texto que se muestra.
     * @return Lo que ha escrito, sin espacios al principio ni al final.
     */
    public static String leerTexto(String mensaje) {
        String texto = leer(mensaje);
        while (texto.isEmpty()) {
            System.out.println("No puede estar vacío.");
            texto = leer(mensaje);
        }
        return texto;
    }

    /**
     * Pregunta de sí o no. Repite hasta que responda s o n.
     * 
     * @param mensaje La pregunta (sin el "(s/n)").
     * @return true si responde s; false si responde n.
     */
    public static boolean confirmar(String mensaje) {
        String resp = leer(mensaje + " (s/n): ");
        while (!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n")) {
            System.out.println("Responde s o n.");
            resp = leer(mensaje + " (s/n): ");
        }
        return resp.equalsIgnoreCase("s");
    }
}