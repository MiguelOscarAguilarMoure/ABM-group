package commons.operations;

import skills.ESkillType;
import skills.Skill;
import java.util.ArrayList;
import java.util.Scanner;

import static skills.Skill.mostrarHabilidad;

/**
 * Opción Habilidades del menú principal.
 */
public class SkillsOperation implements IOperation {

    @Override
    public String getName() {
        return "Lista de habilidades";
    }

    @Override
    public void operation() {
        boolean repetir2 = true;
        ESkillType tipoFiltrar = devolverTipo(); // Selecciona el tipo
        if (tipoFiltrar == null) {
            return;
        }

        ArrayList<Skill> listaFiltrada = listaFiltrada(Skill.getSkills(), tipoFiltrar); // filtra por tipo
        Scanner sc = new Scanner(System.in); // ¡Mejor crear el Scanner aquí fuera!

        do {
            System.out.println("\nHabilidades del tipo seleccionado: ");
            mostrarListaHabilidades(listaFiltrada); // muestra lista filtrada
            System.out.println("0. Volver atrás\n");

            System.out.print("Seleccione una opción: ");
            int seleccionar = sc.nextInt();

            if (seleccionar > 0 && seleccionar <= listaFiltrada.size()) {
                mostrarHabilidad(listaFiltrada.get(seleccionar - 1));
            } else if (seleccionar == 0) {
                // Si pulsa 0 de forma explícita, salimos del bucle
                repetir2 = false;
            } else {
                // Cualquier otro número (negativo o superior al tamaño) es inválido
                System.out.println("Introduzca un número válido");
            }

        } while (repetir2);
    }


    //======= Operaciones Externas ================================================


    /**
     * Solicita al usuario mediante la consola un número para seleccionar y devolver
     * un tipo de habilidad específico.
     * <p>
     * El metodo asocia las opciones numéricas con las siguientes constantes:
     * <ul>
     *   <li>1 -> {@link ESkillType#TREASURE} (tesoro)</li>
     *   <li>2 -> {@link ESkillType#EXCAVATION} (excavación)</li>
     *   <li>3 -> {@link ESkillType#TERRAIN} (terreno)</li>
     *   <li>4 -> {@link ESkillType#EXPERT} (especialista)</li>
     * </ul>
     * </p>
     *
     * @return El {@link ESkillType} seleccionado por el usuario, o {@code null} si
     * el usuario introduce 0 para salir o una opción no válida.
     */
    public ESkillType devolverTipo() {
        Scanner sc = new Scanner(System.in);
        int seleccionarNum;

        do {
            System.out.println("Introduzca el numero del tipo para seleccionarlo");
            System.out.println("0. SALIR");
            System.out.println("1. Tesoros");
            System.out.println("2. Excavación");
            System.out.println("3. Terreno");
            System.out.println("4. Especializacion");
            seleccionarNum = sc.nextInt();

            switch (seleccionarNum) {
                case 0 -> {
                    return null;
                }
                case 1 -> {
                    return ESkillType.TREASURE;
                }
                case 2 -> {
                    return ESkillType.EXCAVATION;
                }
                case 3 -> {
                    return ESkillType.TERRAIN;
                }
                case 4 -> {
                    return ESkillType.EXPERT;
                }
                default -> System.out.println("¡Opción inválida! Inténtelo de nuevo.\n");
            }
        } while (true);
    }

    /**
     * Devuelve la lista de habilidades filtrada por tipo
     *
     * @param habilidades lista de habilidades generica
     * @param tipo        tipo de habilidad
     * @return lista filtrada de habilidades
     */
    public ArrayList<Skill> listaFiltrada(ArrayList<Skill> habilidades, ESkillType tipo) {
        ArrayList<Skill> listaFiltrada = new ArrayList<>();
        for (Skill habilidad : habilidades) {
            if (habilidad.getTipo() == tipo) {
                listaFiltrada.add(habilidad);
            }
        }
        return listaFiltrada;
    }


    /**
     * Imprime la lista de habilidades con su nodo
     *
     * @param lista lista de habilidades
     */
    public void mostrarListaHabilidades(ArrayList<Skill> lista) {
        int contador = 0;
        for (Skill habilidad : lista) {
            contador++;
            System.out.println(contador + ". " + habilidad.getNombre());
        }
    }

}
