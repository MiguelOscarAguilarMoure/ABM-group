package commons.operations;

import commons.Archaios;
import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository;
import excavation.ETerrainSize;
import excavation.Excavation;
import helpers.CodeHelper;
import helpers.InputHelper;
import helpers.TreasureHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import people.Archeologist;
import regions.Region;
import skills.Skill;
import treasures.Treasure;

/**
 * Opción "Excavar" del menú principal.
 * <p>
 * Pide la región, el arqueólogo y el terreno (en el orden del enunciado), lanza
 * la excavación
 * y al terminar registra las estadísticas, muestra los tesoros encontrados,
 * suma los puntos
 * y la experiencia y, si el arqueólogo sube de nivel, le hace elegir una
 * habilidad nueva
 * por cada nivel y la especialización al llegar al nivel máximo.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Miguel Aguilar
 * @version 1.0
 * @see excavation.Excavation
 * @see people.Archeologist
 */
public class ExcavateOperation implements IOperation {
    /** Partida en curso (arqueólogos, puntos y estadísticas). */
    private final Archaios juego;
    /** Generador aleatorio para elegir las habilidades que se ofrecen. */
    private final Random random = new Random();

    /**
     * Crea la operación de excavar.
     *
     * @param juego La partida en curso.
     */
    public ExcavateOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Excavar";
    }

    /**
     * Ejecuta la excavación completa: elegir, excavar y repartir resultados.
     * Si en cualquier elección se pulsa 0, vuelve al menú principal sin excavar.
     */
    @Override
    public void operation() {
        // 1. Región, arqueólogo y terreno (en el orden del PDF)
        Region region = elegirRegion();
        if (region == null) {
            return;
        }
        Archeologist a = elegirArqueologo(region);
        if (a == null) {
            return;
        }
        ETerrainSize terreno = elegirTerreno(a);
        if (terreno == null) {
            return;
        }

        // 2. Excavar
        Excavation excavacion = new Excavation(terreno, region, a);
        List<String> encontrados = excavacion.ejecutar();

        // 3. Estadísticas y tesoros encontrados
        juego.getStats().registrar(region, encontrados);
        mostrarTesoros(region, encontrados);
        // TODO Zeraf (librería Terra): enviar los tesoros al museo y mostrar su mensaje

        // 4. Puntos y experiencia
        int puntos = excavacion.getPuntosObtenidos();
        int exp = excavacion.getExperienciaObtenida();
        juego.addPuntos(puntos);
        System.out.println("Puntos conseguidos: " + puntos);
        System.out.println(a.getNombre() + " ha conseguido " + exp + " puntos de experiencia.");

        // 5. Subir de nivel
        int subidos = a.addExperiencia(exp);
        subirNiveles(a, subidos);
    }

    // ========================================================== ELEGIR

    /**
     * Muestra las regiones y pide una.
     *
     * @return La región elegida, o {@code null} si elige 0 o una opción no válida.
     */
    private Region elegirRegion() {
        List<Region> regiones = RegionRepository.getTodas();
        System.out.println("-------------------- Regiones --------------------");
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Región en la que realizar la excavación: ");
        if (op < 1 || op > regiones.size()) {
            return null;
        }
        return regiones.get(op - 1);
    }

    /**
     * Muestra los arqueólogos que pueden excavar en la región, con su nombre y
     * nivel, y pide uno.
     *
     * @param region La región elegida.
     * @return El arqueólogo elegido, o {@code null} si no hay ninguno disponible o
     *         elige 0.
     */
    private Archeologist elegirArqueologo(Region region) {
        if (juego.getArqueologos().isEmpty()) {
            System.out.println("No tienes arqueólogos contratados.");
            return null;
        }
        List<Archeologist> disponibles = new ArrayList<>();
        for (Archeologist a : juego.getArqueologos()) {
            if (a.puedeExcavarEn(region)) { // por si la región pide licencia
                disponibles.add(a);
            }
        }
        if (disponibles.isEmpty()) {
            System.out.println("Ningún arqueólogo tiene licencia para excavar en " + region.getNombre() + ".");
            return null;
        }

        System.out.println("-------------------- Arqueólogos --------------------");
        for (int i = 0; i < disponibles.size(); i++) {
            Archeologist a = disponibles.get(i);
            System.out.println((i + 1) + ". " + a.getNombre() + " (nivel " + a.getNivel() + ")");
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Arqueólogo que realiza la excavación: ");
        if (op < 1 || op > disponibles.size()) {
            return null;
        }
        return disponibles.get(op - 1);
    }

    /**
     * Muestra los terrenos que puede excavar el arqueólogo (según su nivel y sus
     * formas
     * desbloqueadas) y pide uno.
     *
     * @param a El arqueólogo elegido.
     * @return El terreno elegido, o {@code null} si elige 0 o una opción no válida.
     */
    private ETerrainSize elegirTerreno(Archeologist a) {
        List<ETerrainSize> disponibles = new ArrayList<>();
        for (ETerrainSize t : ETerrainSize.values()) {
            if (a.puedeExcavar(t)) {
                disponibles.add(t);
            }
        }

        System.out.println("-------------------- Terreno --------------------");
        for (int i = 0; i < disponibles.size(); i++) {
            ETerrainSize t = disponibles.get(i);
            System.out.println((i + 1) + ". " + t.getTamanho() + " " + t.getNombre()
                    + " (" + t.getFil() + "x" + t.getCol() + ")");
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Terreno a excavar: ");
        if (op < 1 || op > disponibles.size()) {
            return null;
        }
        return disponibles.get(op - 1);
    }

    // ========================================================== RESULTADO

    /**
     * Muestra la ficha de cada tesoro encontrado, con el código de la parte
     * encontrada.
     *
     * @param region  La región excavada.
     * @param codigos Los códigos completos de los tesoros encontrados.
     */
    private void mostrarTesoros(Region region, List<String> codigos) {
        System.out.println("-------------------- Tesoros encontrados --------------------");
        if (codigos.isEmpty()) {
            System.out.println("No has encontrado ningún tesoro.");
            return;
        }
        for (String codigo : codigos) {
            Treasure t = region.getTesoro(CodeHelper.getFullTreasure(codigo));
            if (t != null) {
                TreasureHelper.mostrar(t, codigo);
                System.out.println();
            }
        }
    }

    // ========================================================== SUBIR DE NIVEL

     /**
     * Da una habilidad nueva por cada nivel subido y, si llega al nivel máximo
     * sin especialización, le hace elegirla.
     *
     * @param a              El arqueólogo.
     * @param nivelesSubidos Los niveles que ha subido en esta excavación.
     */
    private void subirNiveles(Archeologist a, int nivelesSubidos) {
        for (int i = 0; i < nivelesSubidos; i++) {
            System.out.println(a.getNombre() + " ha subido al nivel " + (a.getNivel() - nivelesSubidos + i + 1) + ".");
            elegirHabilidadNueva(a);
        }
        if (nivelesSubidos > 0 && a.getNivel() == Archeologist.NIVEL_MAX && a.getEspecializacion() == null) {
            elegirEspecializacion(a);
        }
    }

  /**
     * Ofrece hasta 3 habilidades que el arqueólogo puede aprender, la primera de ellas
     * dependiente si hay alguna, y le obliga a elegir una (no hay opción 0).
     *
     * @param a El arqueólogo que ha subido de nivel.
     */
    private void elegirHabilidadNueva(Archeologist a) {
        List<Skill> dependientes = new ArrayList<>();
        List<Skill> otras = new ArrayList<>();
        for (Skill s : SkillRepository.getTodas()) {
            if (a.puedeAprender(s)) {
                if (s.getDependencias().isEmpty()) {
                    otras.add(s);
                } else {
                    dependientes.add(s);
                }
            }
        }

        List<Skill> opciones = new ArrayList<>();
        if (!dependientes.isEmpty()) {
            opciones.add(dependientes.remove(random.nextInt(dependientes.size())));
        }
        otras.addAll(dependientes); // las dependientes que sobran también pueden salir
        while (opciones.size() < 3 && !otras.isEmpty()) {
            opciones.add(otras.remove(random.nextInt(otras.size())));
        }
        if (opciones.isEmpty()) {
            System.out.println("No quedan habilidades por aprender.");
            return;
        }

        System.out.println("-------------------- Nueva habilidad --------------------");
        for (int i = 0; i < opciones.size(); i++) {
            Skill s = opciones.get(i);
            System.out.println((i + 1) + ". " + s.getNombre() + ": " + s.getDescripcion());
        }
        int op;
        do {
            op = InputHelper.leerEntero("Habilidad: ");
        } while (op < 1 || op > opciones.size()); // obligatorio, no hay 0

        a.addHabilidad(opciones.get(op - 1));
    }

    /**
     * Al llegar al nivel máximo, le hace elegir obligatoriamente la región de especialización.
     *
     * @param a El arqueólogo que ha llegado al nivel máximo.
     */
    private void elegirEspecializacion(Archeologist a) {
        List<Region> regiones = RegionRepository.getTodas();
        System.out.println(a.getNombre() + " ha llegado al nivel máximo. Elige su especialización:");
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
        }
        int op;
        do {
            op = InputHelper.leerEntero("Región: ");
        } while (op < 1 || op > regiones.size());

        a.setEspecializacion(regiones.get(op - 1));
    }
}