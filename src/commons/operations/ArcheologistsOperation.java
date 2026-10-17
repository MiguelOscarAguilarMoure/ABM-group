package commons.operations;

import commons.Archaios;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import people.Archeologist;
import skills.Skill;
import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository;

public class ArcheologistsOperation implements IOperation {
    private final static int COSTE_CONTRATAR = 50;
    private Archaios juego;
    private final Console console = System.console();
    private final Random random = new Random();

    public ArcheologistsOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Arqueólogos";
    }

    @Override
    public void operation() {

        int op;
        do {
            System.out.println("1. Ver arqueólogos");
            System.out.println("2. Contratar");
            System.out.println("3. Licenciar");
            System.out.println("0. Volver");
            op = Integer.parseInt(console.readLine("Opción: "));

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

    private void contratar() {
        System.out.println("Tienes " + juego.getPuntos() + " puntos.");
        System.out.println("Contratar un arqueólogo cuesta " + COSTE_CONTRATAR + " puntos.");

        if (juego.getPuntos() < COSTE_CONTRATAR) {
            System.out.println("No tienes puntos suficientes.");
            return;
        }
        String resp = console.readLine("¿Contratar? (s/n): ");
        if (!resp.equalsIgnoreCase("s")) {
            return;
        }
        int numHabilidades = generarNumHabilidades();
        List<Skill> iniciales = generarHabilidades(numHabilidades);

        // 3. Mostrar rareza y habilidades
        System.out.println("Ha salido un arqueólogo " + nombreRareza(iniciales.size()) + ".");
        for (Skill s : iniciales) {
            System.out.println("  -" + s.getNombre() + ":");
            System.out.println("   " + s.getDescripcion());
        }

        // 4. Pedir el nombre, crearlo y cobrar
        String nombre = console.readLine("Nombre del arqueólogo: ");
        Archeologist nuevo = new Archeologist(nombre); 
        for (Skill s : iniciales) {
            nuevo.addHabilidad(s);
        }

        juego.getArqueologos().add(nuevo);
        juego.addPuntos(-COSTE_CONTRATAR);
        System.out.println(nombre + " se ha unido al equipo.");
    }

    

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
            System.out.println("Licenciar a " + a.getNombre() + " te da " + puntos + " puntos.");
            String resp = console.readLine("¿Confirmar? (s/n): ");

            if (resp.equalsIgnoreCase("s")) {
                juego.getArqueologos().remove(a);
                juego.addPuntos(puntos);
                System.out.println(a.getNombre() + " ha sido licenciado.");
                return; // vuelve al menú de arqueólogos
            }
            // si dice que no, vuelve a mostrar la lista
        }
    }

    // Metedo auxiliar para listar arqueologos
    private Archeologist eligirArqueologo() {
        List<Archeologist> lista = juego.getArqueologos();
        for (int i = 0; i < lista.size(); i++) {
            Archeologist a = lista.get(i);
            System.out.println((i + 1) + ". " + a.getNombre() + " (nivel " + a.getNivel() + ")");
        }
        System.out.println("0. Volver");
        int op = Integer.parseInt(console.readLine("Arqueólogo: "));
        if (op < 1 || op > lista.size()) {
            return null;
        }
        return lista.get(op - 1);
    }

    private int generarNumHabilidades() {
        int tirada = random.nextInt(100);
        if (tirada < 70)
            return 0;
        if (tirada < 90)
            return 1;
        return 2;
    }

    private String nombreRareza(int numHabilidades) {
        if (numHabilidades == 1)
            return "infrecuente";
        if (numHabilidades == 2)
            return "raro";
        return "común";
    }

    private List<Skill> generarHabilidades(int cantidad) {
        // 1. Candidatas: las que no dependen de ninguna otra
        List<Skill> candidatas = new ArrayList<>();
        for (Skill s : SkillRepository.getTodas()) {
            if (s.getDependencias().isEmpty()) {
                candidatas.add(s);
            }
        }

        // 2. Elegir al azar hasta tener las que tocan
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

        // 3. Devolver las elegidas
        return elegidas;
    }
}
