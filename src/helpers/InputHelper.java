package helpers;

import java.io.Console;

/**
 * Lectura de datos por consola sin que el programa se caiga si el usuario se equivoca.
 */
public class InputHelper {

    private static final Console console = System.console();

    private InputHelper() {
    }

    /**
     * Pide un número entero. Si escribe algo que no es un número, vuelve a preguntar.
     * @param mensaje El texto que se muestra (por ejemplo "Opción: ").
     * @return El número escrito.
     */
    public static int leerEntero(String mensaje) {
        while (true) {
            String texto = console.readLine(mensaje);
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número.");
            }
        }
    }

    /**
     * Pide un texto que no puede estar vacío.
     * @param mensaje El texto que se muestra.
     * @return Lo que ha escrito, sin espacios al principio ni al final.
     */
    public static String leerTexto(String mensaje) {
        while (true) {
            String texto = console.readLine(mensaje).trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("No puede estar vacío.");
        }
    }

    /**
     * Pregunta de sí o no. Repite hasta que responda s o n.
     * @param mensaje La pregunta (sin el "(s/n)").
     * @return true si responde s; false si responde n.
     */
    public static boolean confirmar(String mensaje) {
        while (true) {
            String resp = console.readLine(mensaje + " (s/n): ").trim();
            if (resp.equalsIgnoreCase("s")) {
                return true;
            }
            if (resp.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Responde s o n.");
        }
    }
}