package commons.operations;

import commons.Archaios;
import commons.repositories.SkillRepository;
import helpers.InputHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import people.Archeologist;
import skills.Skill;

/**
 * Opción "Arqueólogos" del menú principal.
 * <p>
 * Permite ver los datos de un arqueólogo, contratar uno nuevo y licenciar a uno
 * existente.
 * </p>
 * <ul>
 * <li><b>Contratar</b> cuesta {@value #COSTE_CONTRATAR} puntos. El arqueólogo
 * sale común (70 %),
 * infrecuente (20 %, 1 habilidad) o raro (10 %, 2 habilidades). Las habilidades
 * iniciales
 * no tienen dependencias y nunca son incompatibles entre sí.</li>
 * <li><b>Licenciar</b> da puntos según el nivel y la rareza del arqueólogo.
 * No se puede licenciar al único arqueólogo.</li>
 * </ul>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see people.Archeologist
 */
public class ArcheologistsOperation implements IOperation {
    /** Puntos que cuesta contratar un arqueólogo. */
    private final static int COSTE_CONTRATAR = 50;
    /** Partida en curso (arqueólogos y puntos). */
    private Archaios juego;
    /** Generador aleatorio para la rareza y las habilidades iniciales. */
    private final Random random = new Random();

    /**
     * Crea la operación de arqueólogos.
     *
     * @param juego La partida en curso.
     */
    public ArcheologistsOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Arqueólogos";
    }

    /**
     * Menú de arqueólogos: ver, contratar o licenciar.
     * Tras cada opción vuelve a este menú; con 0 vuelve al menú principal.
     */
    @Override
    public void operation() {

        int op;
        do {
            System.out.println("-------------------- Arqueólogos --------------------");
            System.out.println("1. Ver arqueólogos");
            System.out.println("2. Contratar");
            System.out.println("3. Licenciar");
            System.out.println("0. Volver");
            op = InputHelper.leerEntero("Opción: ");
            ;

            if (op == 1)
                ver();
            if (op == 2)
                contratar();
            if (op == 3)
                licenciar();
        } while (op != 0);
    }

    private void ver() {
        Archeologist a = eligirArqueologo();
        if (a != null) {
            a.mostrar();
        }
    }

    /**
     * Contrata un arqueólogo nuevo: comprueba los puntos, pide confirmación, sortea
     * su rareza
     * y sus habilidades iniciales, las muestra, pide el nombre y cobra el coste.
     */
    private void contratar() {
        System.out.println("Tienes " + juego.getPuntos() + " puntos.");
        System.out.println("Contratar un arqueólogo cuesta " + COSTE_CONTRATAR + " puntos.");

        if (juego.getPuntos() < COSTE_CONTRATAR) {
            System.out.println("No tienes puntos suficientes.");
            return;
        }
        if (!InputHelper.confirmar("¿Contratar?")) {
            return;
        }
        int numHabilidades = generarNumHabilidades();
        List<Skill> iniciales = generarHabilidades(numHabilidades);

        
        System.out.println("Ha salido un arqueólogo " + nombreRareza(iniciales.size()) + ".");
        for (Skill s : iniciales) {
            System.out.println("  -" + s.getNombre() + ":");
            System.out.println("   " + s.getDescripcion());
        }

        
        String nombre = InputHelper.leerTexto("Nombre del arqueólogo: ");
        Archeologist nuevo = new Archeologist(nombre);
        for (Skill s : iniciales) {
            nuevo.addHabilidad(s);
        }

        juego.getArqueologos().add(nuevo);
        juego.addPuntos(-COSTE_CONTRATAR);
        System.out.println(nombre + " se ha unido al equipo.");
    }

    /**
     * Licencia un arqueólogo: lo elige, muestra los puntos que dará y pide
     * confirmación.
     * Si confirma, lo quita del equipo y suma los puntos; si no, vuelve a mostrar
     * la lista.
     * No se puede licenciar al único arqueólogo.
     */
    private void licenciar() {
        if (juego.getArqueologos().size() == 1) {
            System.out.println("No puedes licenciar a tu único arqueólogo.");
            return;
        }

        while (true) {
            Archeologist a = eligirArqueologo();
            if (a == null) {
                return; // eligió 0
            }

            int puntos = a.getPuntosLicencia();
            System.out.println("Se ha licenciado a " + a.getNombre() + ".");

            if (InputHelper.confirmar("¿Confirmar?")) {
                juego.getArqueologos().remove(a);
                juego.addPuntos(puntos);
                System.out.println(a.getNombre() + " ha sido licenciado.");
                return; // vuelve al menú de arqueólogos
            }
            // si dice que no, vuelve a mostrar la lista
        }
    }

    /**
     * Muestra los arqueólogos con su nombre y nivel y pide uno.
     *
     * @return El arqueólogo elegido, o {@code null} si elige 0 o una opción no
     *         válida.
     */
    private Archeologist eligirArqueologo() {
        List<Archeologist> lista = juego.getArqueologos();
        for (int i = 0; i < lista.size(); i++) {
            Archeologist a = lista.get(i);
            System.out.println((i + 1) + ". " + a.getNombre() + " (nivel " + a.getNivel() + ")");
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Arqueólogo: ");
        if (op < 1 || op > lista.size()) {
            return null;
        }
        return lista.get(op - 1);
    }

    /**
     * Sortea la rareza del arqueólogo, que decide cuántas habilidades iniciales
     * tiene.
     *
     * @return 0 (común, 70 %), 1 (infrecuente, 20 %) o 2 (raro, 10 %).
     */
    private int generarNumHabilidades() {
        int tirada = random.nextInt(100);
        if (tirada < 70)
            return 0;
        if (tirada < 90)
            return 1;
        return 2;
    }

    /**
     * Nombre de la rareza según el número de habilidades iniciales.
     *
     * @param numHabilidades Número de habilidades iniciales.
     * @return "común", "infrecuente" o "raro".
     */
    private String nombreRareza(int numHabilidades) {
        if (numHabilidades == 1)
            return "infrecuente";
        if (numHabilidades == 2)
            return "raro";
        return "común";
    }

    /**
     * Elige habilidades iniciales al azar. Solo valen las que no tienen
     * dependencias,
     * y se descarta cualquiera que sea incompatible con una ya elegida.
     *
     * @param cantidad Número de habilidades a elegir.
     * @return Lista con las habilidades elegidas.
     */
    private List<Skill> generarHabilidades(int cantidad) {
        // Candidatas: las que no dependen de ninguna otra
        List<Skill> candidatas = new ArrayList<>();
        for (Skill s : SkillRepository.getTodas()) {
            if (s.getDependencias().isEmpty()) {
                candidatas.add(s);
            }
        }

        List<Skill> elegidas = new ArrayList<>();
        while (elegidas.size() < cantidad && !candidatas.isEmpty()) {
            Skill s = candidatas.remove(random.nextInt(candidatas.size()));
            // Se descarta si es incompatible con alguna ya elegida
            boolean compatible = true;
            for (Skill e : elegidas) {
                if (s.esIncompatible(e)) {
                    compatible = false;
                }
            }
            if (compatible) {
                elegidas.add(s);
            }
        }
        return elegidas;
    }

}