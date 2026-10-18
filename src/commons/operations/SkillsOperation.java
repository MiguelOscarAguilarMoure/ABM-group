package commons.operations;

import commons.repositories.SkillRepository;
import helpers.InputHelper;
import java.util.ArrayList;
import java.util.List;
import skills.ESkillType;
import skills.Skill;

/**
 * Opción Habilidades del menú principal.
 */
public class SkillsOperation implements IOperation {

    @Override
    public String getName() {
        return "Habilidades";
    }

    @Override
    public void operation() {
        boolean seguir = true;
        while (seguir) {
            ESkillType tipo = elegirTipo();
            if (tipo == null) {
                seguir = false; // 0: vuelve al menú principal
            } else {
                Skill s = elegirHabilidad(tipo);
                if (s != null) {
                    mostrarHabilidad(s);
                    seguir = InputHelper.confirmar("¿Ver otra habilidad?"); // s: tipos; n: menú
                }
                // si eligió 0 en la lista de habilidades, vuelve a los tipos
            }
        }
    }

       /** Muestra nombre, descripción, tipo, dependencias e incompatibilidades. */
    private void mostrarHabilidad(Skill habilidad) {
        System.out.println("-------------------- " + habilidad.getNombre() + " --------------------");
        System.out.println(habilidad.getDescripcion());
        System.out.println("Tipo: " + habilidad.getTipo());

        if (!habilidad.getDependencias().isEmpty()) {
            System.out.println("Dependencias:");
            for (Skill d : habilidad.getDependencias()) {
                System.out.println("  -" + d.getNombre());
            }
        }

        List<Skill> incompatibles = new ArrayList<>();
        for (Skill otra : SkillRepository.getTodas()) {
            if (habilidad.esIncompatible(otra)) {
                incompatibles.add(otra);
            }
        }
        if (!incompatibles.isEmpty()) {
            System.out.println("Incompatibilidades:");
            for (Skill otra : incompatibles) {
                System.out.println("  -" + otra.getNombre());
            }
        }
    }

    // ======= Operaciones Externas ================================================

    /**
     * Solicita al usuario mediante la consola un número para seleccionar y devolver
     * un tipo de habilidad específico.
     * <p>
     * El metodo asocia las opciones numéricas con las siguientes constantes:
     * <ul>
     * <li>1 -> {@link ESkillType#TREASURE} (tesoro)</li>
     * <li>2 -> {@link ESkillType#EXCAVATION} (excavación)</li>
     * <li>3 -> {@link ESkillType#TERRAIN} (terreno)</li>
     * <li>4 -> {@link ESkillType#EXPERT} (especialista)</li>
     * </ul>
     * </p>
     *
     * @return El {@link ESkillType} seleccionado por el usuario, o {@code null} si
     *         el usuario introduce 0 para salir o una opción no válida.
     */
        /** Muestra los tipos y devuelve el elegido, o null si elige 0. */
    private ESkillType elegirTipo() {
        System.out.println("-------------------- Habilidades --------------------");
        System.out.println("1. Tesoros");
        System.out.println("2. Excavación");
        System.out.println("3. Terreno");
        System.out.println("0. Volver");
        int op = InputHelper.leerEntero("Tipo: ");

        switch (op) {
            case 1:
                return ESkillType.TREASURE;
            case 2:
                return ESkillType.EXCAVATION;
            case 3:
                return ESkillType.TERRAIN;
            default:
                return null;
        }
    }

        /** Muestra las habilidades del tipo por orden alfabético y devuelve la elegida, o null si elige 0. */
    private Skill elegirHabilidad(ESkillType tipo) {
        List<Skill> lista = SkillRepository.getPorTipo(tipo);

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNombre());
        }
        System.out.println("0. Volver");

        int op = InputHelper.leerEntero("Habilidad: ");
        if (op < 1 || op > lista.size()) {
            return null;
        }
        return lista.get(op - 1);
    }

 
}