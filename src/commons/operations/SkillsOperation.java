package commons.operations;

import skills.ESkillType;
import skills.Habilidad.Especialista;
import skills.Habilidad.Licencia;
import skills.Skill;
import java.util.ArrayList;
import java.util.Scanner;
import commons.repositories.SkillRepository;
import static commons.repositories.SkillRepository.getSkills;

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

        ArrayList<Skill> listaFiltrada = listaFiltrada(SkillRepository.getSkills(), tipoFiltrar); // filtra por tipo
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

    private void mostrarHabilidad(Skill skill) {
        if (skill == null) {return;}
        System.out.println("========== " + skill.getNombre()+ "==========");
        System.out.println(skill.getDescripcion());
        if (skill instanceof Licencia) {
            System.out.println("Incompatible con ==============");
            System.out.println("Licencias");
        }
        if (skill instanceof Especialista) {
            System.out.println("Incompatible con ==============");
            System.out.println("Especialista");
        }
        Boolean notieneDependencia = skill.dependecias.isEmpty();
        if (!notieneDependencia) {
            System.out.println("Depende de ============");
            System.out.println(skill.getDependecias().getFirst().getNombre());
        }



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

    /**
     * Proporciona 3 habilidades a seleccionar, 1 de mejora (si esta lista tiene habilidades) y 2 (3 si no se cumple la primera condición)
     *
     * @param habilidadesArqueologo Lista de habilidades de la clase Arqueologo
     * @return una instancia de la clase hija de Skills
     */
    public Skill ElegirHabilidad(ArrayList<Skill> habilidadesArqueologo) {
        final int MAXIMO_A_ELEGIR = 3;
        boolean tieneEspecializacion = false;
        boolean tieneLicencia = false;
        ArrayList<Skill> SeleccionDeHabilidades = new ArrayList<>();
        ArrayList<Skill> ListaPromocionDeHabilidad = new ArrayList<>();
        ArrayList<Skill> HabilidadesRand = new ArrayList<>();

        // 1. Filtrar habilidades del arqueólogo y posibles promociones
        if (!habilidadesArqueologo.isEmpty()) {
            for (Skill SkillArqueologo : habilidadesArqueologo) {
                if (SkillArqueologo instanceof Licencia) {
                    tieneLicencia = true;
                }
                if (SkillArqueologo instanceof Especialista) {
                    tieneEspecializacion = true;
                }

                for (Skill SkillGeneral : SkillRepository.getSkills()) {
                    {
                        // Validación para evitar NullPointerException si Dependecias está vacío
                        if (SkillGeneral.dependecias != null && !SkillGeneral.dependecias.isEmpty()) {
                            if (SkillArqueologo.equals(SkillGeneral.dependecias.getFirst())) {
                                ListaPromocionDeHabilidad.add(SkillGeneral);
                            }
                        }
                    }
                }
                // Si hay opciones de promoción, añade una de forma aleatoria
                if (!ListaPromocionDeHabilidad.isEmpty()) {
                    int randomPromocion = (int) (Math.random() * ListaPromocionDeHabilidad.size());
                    SeleccionDeHabilidades.add(ListaPromocionDeHabilidad.get(randomPromocion));
                }
            }

            // 2. Recopilar habilidades aleatorias elegibles
            for (Skill SkillGeneral : SkillRepository.getSkills()) {
                {
                    boolean esEspecialista = SkillGeneral instanceof Especialista;
                    boolean esLicencia = SkillGeneral instanceof Licencia;

                    if (((!esEspecialista && tieneEspecializacion) ||
                            (!esLicencia && tieneLicencia)) &&
                            !habilidadesArqueologo.contains(SkillGeneral) &&
                            SkillGeneral.dependecias != null &&
                            SkillGeneral.dependecias.isEmpty() &&
                            !SeleccionDeHabilidades.contains(SkillGeneral)) {

                        HabilidadesRand.add(SkillGeneral);
                    }
                }


                // Rellenar hasta completar el MAXIMO_A_ELEGIR de forma aleatoria
                while (SeleccionDeHabilidades.size() < MAXIMO_A_ELEGIR && !HabilidadesRand.isEmpty()) {
                    int randomIndex = (int) (Math.random() * HabilidadesRand.size());
                    Skill seleccionada = HabilidadesRand.get(randomIndex);

                    if (!SeleccionDeHabilidades.contains(seleccionada)) {
                        SeleccionDeHabilidades.add(seleccionada);
                    } else {
                        // Si ya está añadida, la removemos de la lista temporal para evitar bucles infinitos
                        HabilidadesRand.remove(randomIndex);
                    }
                }

                // Validación por si acaso no hay suficientes habilidades disponibles en el juego
                if (SeleccionDeHabilidades.isEmpty()) {
                    System.out.println("No hay habilidades disponibles para elegir.");
                    return null;
                }

                // 3. Menú de selección interactivo
                Scanner sc = new Scanner(System.in);
                int seleccionar;

                do {
                    System.out.println("\nSeleccione habilidad (seleccione por numero):");
                    for (int i = 0; i < SeleccionDeHabilidades.size(); i++) {
                        System.out.println((i + 1) + " - " + SeleccionDeHabilidades.get(i).getNombre());
                    }

                    seleccionar = sc.nextInt();

                    if (seleccionar >= 1 && seleccionar <= SeleccionDeHabilidades.size()) {
                        Skill elegida = SeleccionDeHabilidades.get(seleccionar - 1);
                        System.out.println("Habilidad " + elegida.getNombre() + " seleccionada");
                        return elegida; // Retorna directamente y sale del método
                    } else {
                        System.out.println("Entrada Erronea, por favor use un numero valido (1 al " + SeleccionDeHabilidades.size() + ")");
                    }

                } while (true); // El bucle se repite hasta que el usuario elija una opción válida y haga return
            }

        }
        return null;
    }
}