package commons.operations;

import helpers.CodeHelper;
import helpers.UtilsHelper;
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
 * Pide el arqueólogo, la región y el terreno, realiza la excavación y muestra el resultado.
 */
public class ExcavationOperation implements IOperation {

    private static final String ADORNO_MENU = "--------------------";

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
        return "Excavación";
    }

    @Override
    public void operation() {
        Archeologist arqueologo = elegirArqueologo();
        if (arqueologo == null) {
            return;
        }
        Region region = elegirRegion();
        if (region == null) {
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
        System.out.println("Puntos conseguidos: " + excavacion.getPuntosObtenidos());
        // TODO Sumar los puntos conseguidos a los del jugador (pendiente de saber dónde se guardan).
        System.out.println("El arqueólogo " + arqueologo.getNombre() + " ha conseguido "
                + excavacion.getExperienciaObtenida() + " puntos de experiencia.");
        // TODO addExperiencia devuelve los niveles subidos: por cada uno, elegir una habilidad entre tres
        //      (sin opción de salir) y, al llegar al nivel máximo, elegir la región de especialización.
        arqueologo.addExperiencia(excavacion.getExperienciaObtenida());
    }

    // ===== Menús ==========================================================

    private Archeologist elegirArqueologo() {
        if (arqueologos.isEmpty()) {
            System.out.println("No hay arqueólogos disponibles.");
            return null;
        }
        List<String> opciones = new ArrayList<>();
        for (Archeologist arqueologo : arqueologos) {
            opciones.add(arqueologo.getNombre());
        }
        int indice = elegir("Arqueólogos", opciones, "Arqueólogo que realiza la excavación: ");
        return (indice < 0) ? null : arqueologos.get(indice);
    }

    private Region elegirRegion() {
        if (regiones.isEmpty()) {
            System.out.println("No hay regiones disponibles.");
            return null;
        }
        List<String> opciones = new ArrayList<>();
        for (Region region : regiones) {
            opciones.add(region.getNombre());
        }
        int indice = elegir("Regiones", opciones, "Región en la que realizar la excavación: ");
        return (indice < 0) ? null : regiones.get(indice);
    }

    /** Primero el tamaño y, si el arqueólogo tiene varias formas de ese tamaño, la forma. */
    private ETerrainSize elegirTerreno(Archeologist arqueologo) {
        List<ETerrainSize> terrenos = arqueologo.getTerrenosExcavables();
        if (terrenos.isEmpty()) {
            System.out.println(arqueologo.getNombre() + " no tiene ningún terreno disponible.");
            return null;
        }
        List<String> tamanhos = new ArrayList<>();
        for (ETerrainSize terreno : terrenos) {
            if (!tamanhos.contains(terreno.getTamanho())) {
                tamanhos.add(terreno.getTamanho());
            }
        }
        int indice = elegir("Terreno", tamanhos, "Tamaño del terreno a excavar: ");
        if (indice < 0) {
            return null;
        }

        List<ETerrainSize> formas = new ArrayList<>();
        for (ETerrainSize terreno : terrenos) {
            if (terreno.getTamanho().equals(tamanhos.get(indice))) {
                formas.add(terreno);
            }
        }
        if (formas.size() == 1) {
            return formas.get(0);
        }
        List<String> nombres = new ArrayList<>();
        for (ETerrainSize forma : formas) {
            nombres.add(forma.getNombre() + " (" + forma.getFil() + "x" + forma.getCol() + ")");
        }
        int indiceForma = elegir("Tipo de terreno", nombres, "Tipo de terreno a excavar: ");
        return (indiceForma < 0) ? null : formas.get(indiceForma);
    }

    /**
     * Muestra un menú numerado con la opción 0 para volver.
     *
     * @return El índice de la opción elegida o -1 si se vuelve atrás.
     */
    private int elegir(String titulo, List<String> opciones, String peticion) {
        System.out.println(ADORNO_MENU + " " + titulo + " " + ADORNO_MENU);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ".- " + opciones.get(i));
        }
        System.out.println("0.- Volver");
        return leerOpcion(peticion, opciones.size()) - 1;
    }

    private int leerOpcion(String peticion, int max) {
        String aviso = "Opción no válida, introduce un número entre 0 y " + max + ".";
        while (true) {
            System.out.print(peticion);
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
        System.out.println(ADORNO_MENU + " Tesoros encontrados " + ADORNO_MENU);
        if (codigos.isEmpty()) {
            System.out.println("No has encontrado ningún tesoro.");
            return;
        }
        for (int i = 0; i < codigos.size(); i++) {
            if (i > 0) {
                System.out.println();
            }
            Treasure tesoro = region.getTesoro(CodeHelper.getFullTreasure(codigos.get(i)));
            if (tesoro == null) {
                System.out.println("Código: " + codigos.get(i) + " (tesoro desconocido)");
            } else {
                mostrarTesoro(tesoro, codigos.get(i));
            }
        }
    }

    /** Nombre, descripción, código, y tipo y rareza con la concordancia de género. */
    private void mostrarTesoro(Treasure tesoro, String codigo) {
        boolean femenino = esFemenino(tesoro.getTipoTesoro());
        System.out.println(tesoro.getNombre());
        System.out.println(tesoro.getDescripcion());
        System.out.println("Código: " + codigo);
        System.out.println(UtilsHelper.ucfirst(tesoro.getTipoTesoro().toString())
                + " " + getRareza(tesoro.getRarezaTesoro(), femenino));
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
}