package commons.operations;

import commons.repositories.SkillRepository;
import helpers.InputHelper;
import java.util.ArrayList;
import java.util.List;
import skills.ESkillType;
import skills.Skill;

/**
 * Opción "Habilidades" del menú principal.
 * <p>
 * Muestra los tipos de habilidad; al elegir uno, las habilidades de ese tipo
 * por orden alfabético; y al elegir una, sus datos: nombre, descripción, tipo,
 * dependencias e incompatibilidades. Después pregunta si se quiere ver otra:
 * si la respuesta es sí vuelve a los tipos, y si es no, al menú principal.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see commons.repositories.SkillRepository
 * @see skills.Skill
 */
public class SkillsOperation implements IOperation {

    @Override
    public String getName() {
        return "Habilidades";
    }

    /**
     * Bucle del menú de habilidades: tipo → habilidad → datos → "¿Ver otra
     * habilidad?".
     * Con 0 en la lista de tipos vuelve al menú principal;
     * con 0 en la lista de habilidades vuelve a los tipos.
     */
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

     /**
     * Muestra los datos de una habilidad: nombre, descripción, tipo y, si las tiene,
     * sus dependencias y las habilidades con las que es incompatible.
     *
     * @param habilidad La habilidad a mostrar.
     */
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

    /**
     * Muestra los tipos de habilidad y pide uno.
     *
     * @return El tipo elegido, o {@code null} si elige 0 o una opción no válida.
     */
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

     /**
     * Muestra las habilidades de un tipo por orden alfabético y pide una.
     *
     * @param tipo El tipo de habilidad elegido.
     * @return La habilidad elegida, o {@code null} si elige 0 o una opción no válida.
     */
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