package excavation.utils;

import helpers.InputHelper;
import java.util.List;
import java.util.Locale;

import excavation.Excavation;
import excavation.Position;
import logs.Registro;
import treasures.ETreasureRarity;

/**
 * Gestiona la interfaz por consola de una excavación: visualización de turnos,
 * lectura y validación de coordenadas del jugador y resumen final.
 *
 * 
 *
 * @author Miguel Aguilar
 * @version 2.0
 * @see Excavation
 * @see InputHelper
 * @see Position
 * @see ETreasureRarity
 * @see Registro
 * @since 2026-11-18
 */
public class ExcavationConsoleUI {

    /** Texto decorativo que enmarca el título de la excavación. */
    private static final String ADORNO = "::::::::::::::::::::";

    /** Línea separadora entre turnos. */
    private static final String SEPARADOR_TURNO = "----------------------------------------";

    /** Línea separadora del resumen final. */
    private static final String SEPARADOR_FIN = "::::::::::::::::::::::::::::::::::::::::";

    /** Excavación gestionada por esta interfaz. */
    private final Excavation excavacion;

    /**
     * Crea un controlador de consola asociado a una instancia de excavación.
     *
     * @param excavacion partida de excavación a ejecutar.
     * @throws IllegalArgumentException si la excavación es nula.
     */
    public ExcavationConsoleUI(Excavation excavacion) {
        if (excavacion == null) {
            throw new IllegalArgumentException("La excavación no puede ser nula.");
        }
        this.excavacion = excavacion;
    }

    /**
     * Ejecuta la interacción por consola hasta que termine la excavación o se
     * agote la entrada.
     *
     * @return lista con los códigos completos de los tesoros encontrados.
     */
    public List<String> ejecutar() {
        System.out.println(ADORNO + " Excavación en " + excavacion.getRegion().getNombre() + " " + ADORNO);
        while (!excavacion.isFinalizada()) {
            mostrarTurno();
            String linea = InputHelper.leerLinea();
            if (linea == null) {
                break;
            }
            procesarEntrada(linea);
            if (!excavacion.isFinalizada()) {
                System.out.println(SEPARADOR_TURNO);
            }
        }
        mostrarFinal();
        return excavacion.getCodigosEncontrados();
    }

    /** Muestra el estado actual y solicita las coordenadas del turno. */
    private void mostrarTurno() {
        System.out.println(excavacion);
        System.out.println("Número de acciones restantes: " + excavacion.getAccionesRestantes());
        if (excavacion.getArqueologo().isInvestigacion()) {
            System.out.println("Quedan " + excavacion.getTesorosRestantes(ETreasureRarity.COMMON) + " comunes, "
                    + excavacion.getTesorosRestantes(ETreasureRarity.UNCOMMON) + " infrecuentes y "
                    + excavacion.getTesorosRestantes(ETreasureRarity.RARE) + " raros.");
        }
        System.out.print("Conjunto de coordenadas a excavar separadas por espacios: ");
    }

    /**
     * Divide y procesa las coordenadas recibidas en una línea.
     *
     * @param linea texto introducido por el usuario.
     */
    private void procesarEntrada(String linea) {
        String entradaLimpia = linea.trim();
        if (entradaLimpia.isEmpty()) {
            System.out.println("No has introducido ninguna posición.");
            return;
        }
        String[] indicaciones = entradaLimpia.split("\\s+");
        int i = 0;
        while (i < indicaciones.length && !excavacion.isFinalizada()) {
            excavarPosicion(indicaciones[i]);
            i++;
        }
        if (i < indicaciones.length) {
            System.out.println("Se ignoran las posiciones restantes: la excavación ha terminado.");
        }
    }

    /**
     * Convierte una coordenada y ejecuta la excavación correspondiente.
     *
     * @param coordenada coordenada introducida por el usuario.
     */
    private void excavarPosicion(String coordenada) {
        try {
            Position posicion = leerPosicion(coordenada);
            excavacion.excavar(posicion);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(coordenada + ": " + e.getMessage());
            Registro.registrarError("Posición no válida (" + coordenada + "): " + e.getMessage(), e);
        }
    }

    /**
     * Convierte el texto de una coordenada en una posición.
     *
     * @param texto coordenada formada por una fila y una columna.
     * @return posición correspondiente a la coordenada.
     * @throws IllegalArgumentException si el texto no representa una posición válida.
     */
    private Position leerPosicion(String texto) {
        int i = 0;
        while (i < texto.length() && Character.isDigit(texto.charAt(i))) {
            i++;
        }
        String letras = texto.substring(i).toUpperCase(Locale.ROOT);
        if (i == 0 || letras.isEmpty()) {
            throw new IllegalArgumentException("formato no válido, usa número y letra (ej. 3B).");
        }
        try {
            int fila = Integer.parseInt(texto.substring(0, i));
            return new Position(fila, letras);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("el número de fila no es válido.");
        }
    }

    /** Muestra el estado final de la excavación y el resumen de tesoros. */
    private void mostrarFinal() {
        System.out.println(SEPARADOR_FIN);
        System.out.println("Excavación terminada");
        if (excavacion.getTesorosRestantes() == 0) {
            System.out.println("¡Has encontrado todos los tesoros!");
        }
        System.out.println(excavacion);
        System.out.println(excavacion.getRejillaDescubierta());
        System.out.println("Se han encontrado:");
        mostrarResumen(ETreasureRarity.COMMON, "comunes");
        mostrarResumen(ETreasureRarity.UNCOMMON, "infrecuentes");
        mostrarResumen(ETreasureRarity.RARE, "raros");
    }

    /**
     * Muestra los tesoros encontrados de una rareza, si los hay.
     *
     * @param rareza rareza de los tesoros que se resume.
     * @param plural nombre plural de la rareza para mostrar.
     */
    private void mostrarResumen(ETreasureRarity rareza, String plural) {
        int total = excavacion.getTotalTesoros(rareza);
        if (total > 0) {
            int encontrados = total - excavacion.getTesorosRestantes(rareza);
            System.out.println(encontrados + "/" + total + " tesoros " + plural + ".");
        }
    }
}