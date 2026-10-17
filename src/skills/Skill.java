package skills;


import skills.Habilidad.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Skill {

    protected String nombre;
    protected String descripcion;
    protected ESkillType tipo;
    protected static ArrayList<Skill> Skills = new ArrayList<>();
    protected ArrayList<Skill> Dependecias = new ArrayList<>();

    /**
     *
     * @param nombre      nombre de la habilidad
     * @param descripcion descripcion de la habilidad
     * @param tipo        Tipo de habilidad
     */
    public Skill(String nombre, String descripcion, ESkillType tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }


    //== GETTERS & SETTERS ==================================================================================

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ESkillType getTipo() {
        return tipo;
    }

    public static ArrayList<Skill> getSkills() {
        return Skills;
    }

    public ArrayList<Skill> getDependecias() {
        return Dependecias;
    }

    //=======================================================================================================


    /**
     * Instancias de las clases hijas a Skill y agregar a una lista todas las habilidades del programa. al igual que sus
     * dependencias
     *
     */
    public void InicializadosSkills() {
        //EXTRA
        Extra Extra1 = new Extra("Extra I", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 1);
        Extra Extra2 = new Extra("Extra II", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 2);
        Extra Extra3 = new Extra("Extra III", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 3);
        Extra2.Dependecias.add(Extra1);
        Extra3.Dependecias.add(Extra2);
        Skills.add(Extra1);
        Skills.add(Extra2);
        Skills.add(Extra3);

        //Aprendiz, Experto, Maestro
        Conocimiento aprendiz = new Conocimiento("Aprendiz", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Conocimiento experto = new Conocimiento("Experto", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Conocimiento maestro = new Conocimiento("Maetro", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        experto.Dependecias.add(aprendiz);
        maestro.Dependecias.add(experto);
        Skills.add(aprendiz);
        Skills.add(experto);
        Skills.add(maestro);

        //Especialista (Incompatibles)
        Especialista especialistaU = new Especialista("EspecialistaU", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Especialista especialistaR = new Especialista("EspecialistaR", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Especialista especialistaM = new Especialista("EspecialistaM", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        Skills.add(especialistaM);
        Skills.add(especialistaR);
        Skills.add(especialistaU);

        //Excavacion
        Excavaciones excavacion1 = new Excavaciones("Excavacion I", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 5);
        Excavaciones excavacion2 = new Excavaciones("Excavacion II", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 10);
        Excavaciones excavacion3 = new Excavaciones("Excavacion III", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 15);
        excavacion2.Dependecias.add(excavacion1);
        excavacion3.Dependecias.add(excavacion2);
        Skills.add(excavacion1);
        Skills.add(excavacion2);
        Skills.add(excavacion3);

        //Experiencia
        Experiencia experiencia1 = new Experiencia("Experiencia I", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, 1);
        Experiencia experiencia2 = new Experiencia("Experiencia II", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, 2);
        experiencia2.Dependecias.add(experiencia1);
        Skills.add(experiencia1);
        Skills.add(experiencia2);

        //Limpieza
        Limpieza limpieza1 = new Limpieza("Limpieza I", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, 1);
        Limpieza limpieza2 = new Limpieza("Limpieza II", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, 2);
        limpieza2.Dependecias.add(limpieza1);
        Skills.add(limpieza1);
        Skills.add(limpieza2);

        //Escaneo
        Escaneo escaneo = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, 1);
        Skills.add(escaneo);

        //Licencia (Incompatibles)
        Licencia licenciaA = new Licencia("Licencia A", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, 1);
        Licencia licenciaB = new Licencia("Licencia B", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, 1);
        Skills.add(licenciaA);
        Skills.add(licenciaB);

        //Especialista
        TerrenoAgua agua = new TerrenoAgua("Agua", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoAguaProf aguaProf = new TerrenoAguaProf("Agua Profunda", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoArena arena = new TerrenoArena("Arena", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoOceano oceano1 = new TerrenoOceano("Oceano I", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, 1);
        TerrenoOceano oceano2 = new TerrenoOceano("OCEANO II", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, 2);
        oceano2.Dependecias.add(oceano1);
        Skills.add(agua);
        Skills.add(aguaProf);
        Skills.add(arena);
        Skills.add(oceano1);
        Skills.add(oceano2);
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

                for (Skill SkillGeneral : Skills) {
                    // Validación para evitar NullPointerException si Dependecias está vacío
                    if (SkillGeneral.Dependecias != null && !SkillGeneral.Dependecias.isEmpty()) {
                        if (SkillArqueologo.equals(SkillGeneral.Dependecias.getFirst())) {
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
        for (Skill SkillGeneral : Skills) {
            boolean esEspecialista = SkillGeneral instanceof Especialista;
            boolean esLicencia = SkillGeneral instanceof Licencia;

            if ((!esEspecialista || tieneEspecializacion) &&
                    (!esLicencia || tieneLicencia) &&
                    !habilidadesArqueologo.contains(SkillGeneral) &&
                    SkillGeneral.Dependecias != null &&
                    SkillGeneral.Dependecias.isEmpty() &&
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

    /**
     * Muestra la información de una habilidad.
     *
     * El método imprime el nombre, la descripción y, si existen, la lista de habilidades
     * necesarias (dependencias) requeridas para su desbloqueo. Adicionalmente, evalúa si
     * la habilidad pertenece a un tipo restrictivo para advertir sobre su incompatibilidad.
     *
     *
     * @param habilidad La instancia de {@link Skill} de la cual se desea visualizar la información.
     */
    public static void mostrarHabilidad(Skill habilidad) {
        System.out.println("------------------ " + habilidad.getNombre() + " ------------------");
        System.out.println(habilidad.getDescripcion());

        // Verificación segura contra Null y si contiene elementos
        if (habilidad.getDependecias() != null && !habilidad.getDependecias().isEmpty()) {
            System.out.println("---- Dependencias ----");
            for (Skill dependencia : habilidad.getDependecias()) {
                System.out.println("- " + dependencia.getNombre());
            }
        }

        if ((habilidad instanceof Licencia) || habilidad instanceof Especialista) {
            // Imprime el nombre limpio de la clase (Licencia o Especialista)
            System.out.println("Incompatible con las habilidades " + habilidad.getClass().getSimpleName());
        }
    }
}
