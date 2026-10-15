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

    public Skill(String nombre, String descripcion, ESkillType tipo, ArrayList<Skill> dependecias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        Dependecias = dependecias;
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
     * Instancia y agrega a una lista todas las habilidades del programa
     *
     */
    public void InicializadosSkills() {
        //EXTRA
        Extra Extra1 = new Extra("Extra I", "Tesoros adicionales en el terreno", ESkillType.TREASURE, null, 1);
        Extra Extra2 = new Extra("Extra II", "Tesoros adicionales en el terreno", ESkillType.TREASURE, null, 2);
        Extra Extra3 = new Extra("Extra III", "Tesoros adicionales en el terreno", ESkillType.TREASURE, null, 3);
        Extra2.Dependecias.add(Extra1);
        Extra3.Dependecias.add(Extra2);
        Skills.add(Extra1);
        Skills.add(Extra2);
        Skills.add(Extra3);

        //Aprendiz, Experto, Maestro
        Conocimiento aprendiz = new Conocimiento("Aprendiz", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 1);
        Conocimiento experto = new Conocimiento("Experto", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 2);
        Conocimiento maestro = new Conocimiento("Maetro", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 3);
        experto.Dependecias.add(aprendiz);
        maestro.Dependecias.add(experto);
        Skills.add(aprendiz);
        Skills.add(experto);
        Skills.add(maestro);

        //Especialista (Incompatibles)
        Especialista especialistaU = new Especialista("EspecialistaU", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 1);
        Especialista especialistaR = new Especialista("EspecialistaR", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 2);
        Especialista especialistaM = new Especialista("EspecialistaM", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 3);
        Skills.add(especialistaM);
        Skills.add(especialistaR);
        Skills.add(especialistaU);

        //Excavacion
        Excavaciones excavacion1 = new Excavaciones("Excavacion I", "Se pueden excarvar más veces", ESkillType.EXCAVATION, null, 5);
        Excavaciones excavacion2 = new Excavaciones("Excavacion II", "Se pueden excarvar más veces", ESkillType.EXCAVATION, null, 10);
        Excavaciones excavacion3 = new Excavaciones("Excavacion III", "Se pueden excarvar más veces", ESkillType.EXCAVATION, null, 15);
        excavacion2.Dependecias.add(excavacion1);
        excavacion3.Dependecias.add(excavacion2);
        Skills.add(excavacion1);
        Skills.add(excavacion2);
        Skills.add(excavacion3);

        //Experiencia
        Experiencia experiencia1 = new Experiencia("Experiencia I", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, null, 1);
        Experiencia experiencia2 = new Experiencia("Experiencia II", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, null, 2);
        experiencia2.Dependecias.add(experiencia1);
        Skills.add(experiencia1);
        Skills.add(experiencia2);

        //Limpieza
        Limpieza limpieza1 = new Limpieza("Limpieza I", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, null, 1);
        Limpieza limpieza2 = new Limpieza("Limpieza II", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, null, 2);
        limpieza2.Dependecias.add(limpieza1);
        Skills.add(limpieza1);
        Skills.add(limpieza2);

        //Escaneo
        Escaneo escaneo = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, null, 1);
        Skills.add(escaneo);

        //Licencia (Incompatibles)
        Licencia licenciaA = new Licencia("Licencia A", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, null, 1);
        Licencia licenciaB = new Licencia("Licencia B", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, null, 1);
        Skills.add(licenciaA);
        Skills.add(licenciaB);

        //Especialista
        TerrenoAgua agua = new TerrenoAgua("Agua", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, null);
        TerrenoAguaProf aguaProf = new TerrenoAguaProf("Agua Profunda", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, null);
        TerrenoArena arena = new TerrenoArena("Arena", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, null);
        TerrenoOceano oceano1 = new TerrenoOceano("Oceano I", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, null, 1);
        TerrenoOceano oceano2 = new TerrenoOceano("OCEANO II", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, null, 2);
        oceano2.Dependecias.add(oceano1);
        Skills.add(agua);
        Skills.add(aguaProf);
        Skills.add(arena);
        Skills.add(oceano1);
        Skills.add(oceano2);
    }

    public Skill ElegirHabilidad(ArrayList<Skill> habilidadesArqueologo) {
        final int MAXIMO_A_ELEGIR = 3;
        Boolean tieneEspecializacion = false;
        Boolean tieneLicencia = false;
        ArrayList<Skill> SeleccionDeHabilidades = new ArrayList<>();
        ArrayList<Skill> ListaPromocionDeHabilidad = new ArrayList<>();
        ArrayList<Skill> HabilidadesRand = new ArrayList<>();

        // Si el Arqueologo tiene habilidades se inicializa la busqueda de su subida de nivel
        if (!habilidadesArqueologo.isEmpty()) {

            for (Skill SkillArqueologo : habilidadesArqueologo) {
                if (SkillArqueologo instanceof Licencia) {
                    tieneLicencia = true;
                }
                if (SkillArqueologo instanceof Especialista) {
                    tieneEspecializacion = true;
                }

                for (Skill SkillGeneral : Skills) {
                    if (SkillArqueologo.equals(SkillGeneral.Dependecias.getFirst())) {
                        ListaPromocionDeHabilidad.add(SkillGeneral);
                    }
                }
            }
            //Selecciona aleatoriamente una de las selecciones que cumplen y han sido añadidas a las lista de mejoras
            int randomPromocion = (int) (Math.random() * ListaPromocionDeHabilidad.size());
            SeleccionDeHabilidades.add(ListaPromocionDeHabilidad.get(randomPromocion));
        }

        //SELECCION DE HABILIDADES RANDOM

        for (Skill SkillGeneral : Skills) {

            if (!(SkillGeneral instanceof Especialista) && !tieneEspecializacion) { //Si no es una instancia Especializacion y las habilidades del arquitecto no lo tiene
                if (!(SkillGeneral instanceof Licencia) && !tieneLicencia) { //Si no es una instancia licencia y las habilidades del arquitecto no lo tiene
                    if (!habilidadesArqueologo.contains(SkillGeneral) && SkillGeneral.Dependecias.isEmpty()) {
                        HabilidadesRand.add(SkillGeneral);
                    }
                }
            }

            do {
                int randomPromocion = (int) (Math.random() * HabilidadesRand.size());
                SeleccionDeHabilidades.add(HabilidadesRand.get(randomPromocion));
            } while (SeleccionDeHabilidades.size() >= MAXIMO_A_ELEGIR);

            do {


            System.out.println("Seleccione habilidad (seleccione por numero)");
            System.out.println("1 - " + SeleccionDeHabilidades.get(0).getNombre());
            System.out.println("2 - " + SeleccionDeHabilidades.get(1).getNombre());
            System.out.println("3 - " + SeleccionDeHabilidades.get(2).getNombre());

            Scanner sc = new Scanner(System.in);
            int seleccionar = sc.nextInt();

            switch (seleccionar) {
                case 1 -> {
                    System.out.println("Habilidad seleccionada");
                    return SeleccionDeHabilidades.get(0);}
                case 2 -> {System.out.println("Habilidad seleccionada");
                    System.out.println("Habilidad seleccionada");
                    return SeleccionDeHabilidades.get(1);}
                case 3 -> {return SeleccionDeHabilidades.get(2);}
                default -> System.out.println("Entrada Erronea, por favor use un numero");
            }

            } while ()
        }

    }
}