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
 * Opción Excavar: elegir región, arqueólogo y terreno, excavar y repartir
 * puntos, experiencia y estadísticas.
 */
public class ExcavateOperation implements IOperation {

    private final Archaios juego;
    private final Random random = new Random();

    public ExcavateOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Excavar";
    }

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

    /** Lista las regiones y devuelve la elegida, o null si elige 0. */
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

    /** Lista los arqueólogos que pueden excavar en la región, con nombre y nivel. */
    private Archeologist elegirArqueologo(Region region) {
        List<Archeologist> disponibles = new ArrayList<>();
        for (Archeologist a : juego.getArqueologos()) {
            if (a.puedeExcavarEn(region)) {   // por si la región pide licencia
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

    /** Lista los terrenos que puede excavar el arqueólogo (por nivel y formas desbloqueadas). */
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

    /** Muestra cada tesoro encontrado: nombre, descripción, código, tipo y rareza. */
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

    /** Una habilidad nueva por cada nivel subido y especialización al llegar a 5. */
    private void subirNiveles(Archeologist a, int nivelesSubidos) {
        for (int i = 0; i < nivelesSubidos; i++) {
            System.out.println(a.getNombre() + " ha subido al nivel " + (a.getNivel() - nivelesSubidos + i + 1) + ".");
            elegirHabilidadNueva(a);
        }
        if (nivelesSubidos > 0 && a.getNivel() == Archeologist.NIVEL_MAX && a.getEspecializacion() == null) {
            elegirEspecializacion(a);
        }
    }

    /** Ofrece 3 habilidades (la primera dependiente si hay) y obliga a elegir una, sin opción 0. */
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
        otras.addAll(dependientes);   // las dependientes que sobran también pueden salir
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
            System.out.println((i + 1) + ".- " + s.getNombre() + ": " + s.getDescripcion());
        }
        int op;
        do {
            op = InputHelper.leerEntero("Habilidad: ");
        } while (op < 1 || op > opciones.size());   // obligatorio, no hay 0

        a.addHabilidad(opciones.get(op - 1));
    }

    /** Al llegar a nivel 5, elige obligatoriamente la región de especialización. */
    private void elegirEspecializacion(Archeologist a) {
        List<Region> regiones = RegionRepository.getTodas();
        System.out.println(a.getNombre() + " ha llegado al nivel máximo. Elige su especialización:");
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ".- " + regiones.get(i).getNombre());
        }
        int op;
        do {
            op = InputHelper.leerEntero("Región: ");
        } while (op < 1 || op > regiones.size());

        a.setEspecializacion(regiones.get(op - 1));
    }
}