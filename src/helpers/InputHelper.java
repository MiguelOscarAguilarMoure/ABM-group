package helpers;

import java.io.Console;

/**
 * Lectura de datos por consola sin que el programa se caiga si el usuario se
 * equivoca.
 */
public class InputHelper {

    private static final Console console = System.console();

    private InputHelper() {
    }

    /**
     * Lee una línea. Si no hay entrada (Ctrl+D / Ctrl+Z), devuelve "" en vez de
     * null.
     */
    /**
     * Lee una línea. Si no hay entrada (Ctrl+D / Ctrl+Z), cierra el programa
     * en vez de quedarse en un bucle infinito.
     */
    private static String leer(String mensaje) {
        String texto = console.readLine(mensaje);
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