package commons.repositories;

import java.util.ArrayList;
import java.util.List;

import skills.ESkillType;
import skills.Habilidad.*;
import skills.Skill;

/**
 * Repositorio de todas las habilidades del sistema.
 * Las habilidades se registran una sola vez en preInit y se obtienen siempre de aquí.
 */
public class SkillRepository {

    /**
     * Instancias de las clases hijas a Skill y agregar a una lista todas las habilidades del programa. al igual que sus
     * dependencias
     *
     */
    public static void InicializadosSkills() {

        //EXTRA
        Extra Extra1 = new Extra("Extra I", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 1);
        Extra Extra2 = new Extra("Extra II", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 2);
        Extra Extra3 = new Extra("Extra III", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 3);
        addDependencia(Extra2, Extra1);
        addDependencia(Extra3, Extra2);
        registrar(Extra1);
        registrar(Extra2);
        registrar(Extra3);

        //Aprendiz, Experto, Maestro
        Conocimiento aprendiz = new Conocimiento("Aprendiz", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Conocimiento experto = new Conocimiento("Experto", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Conocimiento maestro = new Conocimiento("Maetro", "Aumenta las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        addDependencia(experto, aprendiz);
        addDependencia(maestro, experto);
        registrar(aprendiz);
        registrar(experto);
        registrar(maestro);

        //Especialista (Incompatibles)
        Especialista especialistaU = new Especialista("EspecialistaU", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Especialista especialistaR = new Especialista("EspecialistaR", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Especialista especialistaM = new Especialista("EspecialistaM", "Aumenta mucho las Rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        registrar(especialistaM);
        registrar(especialistaR);
        registrar(especialistaU);

        //Excavacion
        Excavaciones excavacion1 = new Excavaciones("Excavacion I", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 5);
        Excavaciones excavacion2 = new Excavaciones("Excavacion II", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 10);
        Excavaciones excavacion3 = new Excavaciones("Excavacion III", "Se pueden excarvar más veces", ESkillType.EXCAVATION, 15);
        addDependencia(excavacion2, excavacion1);
        addDependencia(excavacion3, excavacion1);
        registrar(excavacion1);
        registrar(excavacion2);
        registrar(excavacion3);

        //Experiencia
        Experiencia experiencia1 = new Experiencia("Experiencia I", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, 1);
        Experiencia experiencia2 = new Experiencia("Experiencia II", "Proabilidad de que una accion de excavar sea gratis", ESkillType.EXCAVATION, 2);
        addDependencia(excavacion2, excavacion1);
        registrar(experiencia1);
        registrar(experiencia2);

        //Limpieza
        Limpieza limpieza1 = new Limpieza("Limpieza I", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, 1);
        Limpieza limpieza2 = new Limpieza("Limpieza II", "Excava automaticamente zonas del terreno", ESkillType.EXCAVATION, 2);
        addDependencia(limpieza2,limpieza1);
        registrar(limpieza1);
        registrar(limpieza2);

        //Escaneo
        Escaneo escaneoA = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, 1);
        Escaneo escaneoB = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, 1);
        Escaneo escaneoC = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, 1);
        Escaneo escaneoD = new Escaneo("Escaneo", "Marca casillas con \"?\" indicando que puede haber un tesoro ", ESkillType.EXCAVATION, 1);
        registrar(escaneoA);
        registrar(escaneoB);
        registrar(escaneoC);
        registrar(escaneoD);

        //Licencia (Incompatibles)
        Licencia licenciaA = new Licencia("Licencia A", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, 1);
        Licencia licenciaB = new Licencia("Licencia B", "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones", ESkillType.TERRAIN, 1);
        registrar(licenciaA);
        registrar(licenciaB);

        //Especialista
        TerrenoAgua agua = new TerrenoAgua("Agua", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoAguaProf aguaProf = new TerrenoAguaProf("Agua Profunda", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoArena arena = new TerrenoArena("Arena", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT);
        TerrenoOceano oceano1 = new TerrenoOceano("Oceano I", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, 1);
        TerrenoOceano oceano2 = new TerrenoOceano("OCEANO II", "Permite excarvar en este tipo de terreno", ESkillType.EXPERT, 2);
        addDependencia(oceano2, oceano1);
        registrar(agua);
        registrar(aguaProf);
        registrar(arena);
        registrar(oceano1);
        registrar(oceano2);
    }

    /** Lista con todas las habilidades registradas. */
    public static ArrayList<Skill> Skills = new ArrayList<>();

    public static ArrayList<Skill> getSkills() {
        return Skills;
    }

    /**
     * Registra una habilidad en el repositorio.
     * @param s La habilidad a registrar.
     */
    public static void registrar(Skill s) {
        Skills.add(s);
    }

    /**
     * Añade las clases que necesita una habilidad antes de ser seleccionada
     * @param anfitrion clase que adquiere la habilidad a depender
     * @param dependeDe clase del que depende
     */
    public static void addDependencia(Skill anfitrion, Skill dependeDe) {
        anfitrion.dependecias.add(dependeDe);
    }

    /**
     * Devuelve todas las habilidades registradas.
     * @return Una copia de la lista de habilidades.
     */
    public static List<Skill> getTodas() {
        return List.copyOf(Skills);
    }

    /**
     * Devuelve las habilidades de un tipo, ordenadas alfabéticamente.
     * @param tipo El tipo de habilidad.
     * @return La lista de habilidades de ese tipo.
     */
    public static List<Skill> getPorTipo(ESkillType tipo) {
        List<Skill> lista = new ArrayList<>();
        for (Skill s : Skills) {
            if (s.getTipo() == tipo) {
                lista.add(s);
            }
        }
        lista.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));
        return lista;
    }

    /**
     * Busca una habilidad por su nombre.
     *
     * @param nombre El nombre de la habilidad, por ejemplo "Extra I".
     * @return La habilidad con ese nombre, o null si no existe.
     */
    public static Skill get(String nombre) {
        return null;
    }
}