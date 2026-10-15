package skills;

import skills.Habilidad.*;

import java.util.ArrayList;

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


    /** Instancia y agrega a una lista todas las habilidades del programa
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
        Conocimiento aprendiz = new Conocimiento("Aprendiz","Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 1);
        Conocimiento experto = new Conocimiento("Experto","Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 2);
        Conocimiento maestro = new Conocimiento("Maetro","Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, null, 3);
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
        Licencia licenciaA = new Licencia("Licencia A",  "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, null, 1);
        Licencia licenciaB = new Licencia("Licencia B",  "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, null, 1);
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

}