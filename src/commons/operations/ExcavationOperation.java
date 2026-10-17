package commons.operations;

import helpers.CodeHelper;
import excavation.ETerrainSize;
import excavation.Excavation;
import people.Archeologist;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Opción Excavar del menú principal.
 * Pide la región, el arqueólogo y el terreno, realiza la excavación y muestra el resultado.
 */
public class ExcavationOperation implements IOperation {

    private final Scanner sc;
    private final List<Archeologist> arqueologos; // la lista de la partida (compartida, no una copia)
    private final List<Region> regiones;

    public ExcavationOperation(Scanner sc, List<Archeologist> arqueologos, List<Region> regiones) {
        if (sc == null || arqueologos == null || regiones == null) {
            throw new IllegalArgumentException("El Scanner y las listas de arqueólogos y regiones no pueden ser nulos.");
        }
        this.sc = sc;
        this.arqueologos = arqueologos;
        this.regiones = regiones;
    }

    @Override
    public String getName() {
        return "Excavar";
    }

    @Override
    public void operation() {
        Region region = elegirRegion();
        if (region == null) {
            return;
        }
        Archeologist arqueologo = elegirArqueologo();
        if (arqueologo == null) {
            return;
        }
        ETerrainSize terreno = elegirTerreno(arqueologo);
        if (terreno == null) {
            return;
        }

        Excavation excavacion;
        try {
            excavacion = new Excavation(terreno, region, arqueologo);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("No se puede realizar la excavación: " + e.getMessage());
            return;
        }
        List<String> codigos = excavacion.ejecutar(sc);

        mostrarTesoros(region, codigos);
        // TODO Enviar los códigos al museo con Zeraf (librería Terra) y mostrar el mensaje que devuelva.
        mostrarPuntosYExperiencia(excavacion);
        // TODO Sumar los puntos obtenidos a los del jugador (pendiente de saber dónde se guardan).
        ganarExperiencia(arqueologo, excavacion.getExperienciaObtenida());
    }

    // ===== Menús ==========================================================

    private Region elegirRegion() {
        if (regiones.isEmpty()) {
            System.out.println("No hay regiones disponibles.");
            return null;
        }
        List<String> opciones = new ArrayList<>();
        for (Region region : regiones) {
            opciones.add(region.getNombre());
        }
        int indice = elegir("¿Dónde quieres excavar?", opciones);
        return (indice < 0) ? null : regiones.get(indice);
    }

    private Archeologist elegirArqueologo() {
        if (arqueologos.isEmpty()) {
            System.out.println("No hay arqueólogos disponibles.");
            return null;
        }
        List<String> opciones = new ArrayList<>();
        for (Archeologist arqueologo : arqueologos) {
            opciones.add(arqueologo.getNombre() + " (nivel " + arqueologo.getNivel() + ")");
        }
        int indice = elegir("¿Qué arqueólogo excava?", opciones);
        return (indice < 0) ? null : arqueologos.get(indice);
    }

    private ETerrainSize elegirTerreno(Archeologist arqueologo) {
        List<ETerrainSize> terrenos = arqueologo.getTerrenosExcavables();
        if (terrenos.isEmpty()) {
            System.out.println(arqueologo.getNombre() + " no tiene ningún terreno disponible.");
            return null;
        }
        List<String> opciones = new ArrayList<>();
        for (ETerrainSize terreno : terrenos) {
            opciones.add(terreno.getTamanho() + " " + terreno.getNombre()
                    + " (" + terreno.getFil() + "x" + terreno.getCol() + ")");
        }
        int indice = elegir("¿Qué terreno quieres excavar?", opciones);
        return (indice < 0) ? null : terrenos.get(indice);
    }

    /**
     * Muestra un menú numerado con la opción 0 para volver.
     *
     * @return El índice de la opción elegida o -1 si se vuelve atrás.
     */
    private int elegir(String titulo, List<String> opciones) {
        System.out.println();
        System.out.println(titulo);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + opciones.get(i));
        }
        System.out.println("0. Volver");
        return leerOpcion(opciones.size()) - 1;
    }

    private int leerOpcion(int max) {
        String aviso = "Introduce un número entre 0 y " + max + ".";
        while (true) {
            System.out.print("Opción: ");
            try {
                int opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion >= 0 && opcion <= max) {
                    return opcion;
                }
                System.out.println(aviso);
            } catch (NumberFormatException e) {
                System.out.println(aviso);
            }
        }
    }

    // ===== Resultado ======================================================

    private void mostrarTesoros(Region region, List<String> codigos) {
        System.out.println();
        if (codigos.isEmpty()) {
            System.out.println("No has encontrado ningún tesoro.");
            return;
        }
        System.out.println("Tesoros encontrados (" + codigos.size() + "):");
        for (String codigo : codigos) {
            Treasure tesoro = region.getTesoro(CodeHelper.getFullTreasure(codigo));
            if (tesoro == null) {
                System.out.println();
                System.out.println("  " + codigo + " (tesoro desconocido)");
            } else {
                mostrarTesoro(tesoro, codigo);
            }
        }
    }

    /** Nombre, descripción, código, tipo, rareza y partes, con la concordancia de género. */
    private void mostrarTesoro(Treasure tesoro, String codigo) {
        boolean femenino = esFemenino(tesoro.getTipoTesoro());
        String frase = "Es " + (femenino ? "una " : "un ") + tesoro.getTipoTesoro()
                + " " + getRareza(tesoro.getRarezaTesoro(), femenino);
        if (tesoro.getPartes() > 1) {
            frase += (femenino ? " dividida" : " dividido") + " en " + tesoro.getPartes() + " partes";
        }
        System.out.println();
        System.out.println("  " + tesoro.getNombre());
        System.out.println("    " + tesoro.getDescripcion());
        System.out.println("    Código: " + codigo);
        System.out.println("    " + frase + ".");
    }

    private boolean esFemenino(ETreasureType tipo) {
        return tipo != ETreasureType.EQUIP && tipo != ETreasureType.FRAGMENT;
    }

    private String getRareza(ETreasureRarity rareza, boolean femenino) {
        switch (rareza) {
            case COMMON:
                return "común";
            case UNCOMMON:
                return "infrecuente";
            default: // RARE
                return femenino ? "rara" : "raro";
        }
    }

    private void mostrarPuntosYExperiencia(Excavation excavacion) {
        System.out.println();
        System.out.println("Puntos obtenidos: " + excavacion.getPuntosObtenidos());
        System.out.println("Experiencia obtenida: " + excavacion.getExperienciaObtenida());
    }

    private void ganarExperiencia(Archeologist arqueologo, int experiencia) {
        int niveles = arqueologo.addExperiencia(experiencia);
        if (niveles > 0) {
            System.out.println();
            System.out.println("¡" + arqueologo.getNombre() + " sube al nivel " + arqueologo.getNivel() + "!");
            // TODO Por cada nivel subido, elegir una habilidad entre tres (sin opción de salir).
            // TODO Al llegar al nivel máximo, elegir la región de especialización.
        }
    }
}



