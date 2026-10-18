package commons.repositories;

import java.util.ArrayList;
import java.util.List;
import skills.ESkillType;
import skills.Habilidad.*;
import skills.Skill;

/**
 * Repositorio de todas las habilidades del sistema.
 * Las habilidades se crean y registran una sola vez en preInit y se obtienen siempre de aquí.
 */
public class SkillRepository {

    /** Lista con todas las habilidades registradas. */
    private static final ArrayList<Skill> skills = new ArrayList<>();

    /**
     * Crea todas las habilidades de la Base, enlaza sus dependencias y las registra.
     * Se llama una vez desde Archaios.preInit().
     */
    public static void inicializar() {
        // Extra
        Extra extra1 = new Extra("Extra I", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 1);
        Extra extra2 = new Extra("Extra II", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 2);
        Extra extra3 = new Extra("Extra III", "Tesoros adicionales en el terreno", ESkillType.TREASURE, 3);
        extra2.addDependencia(extra1);
        extra3.addDependencia(extra2);
        registrar(extra1);
        registrar(extra2);
        registrar(extra3);

        // Aprendiz, Experto, Maestro
        Conocimiento aprendiz = new Conocimiento("Aprendiz", "Aumenta las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Conocimiento experto = new Conocimiento("Experto", "Aumenta las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Conocimiento maestro = new Conocimiento("Maestro", "Aumenta las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        experto.addDependencia(aprendiz);
        maestro.addDependencia(experto);
        registrar(aprendiz);
        registrar(experto);
        registrar(maestro);

        // Especialista (incompatibles entre sí)
        Especialista especialistaU = new Especialista("Especialista U", "Aumenta mucho las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 1);
        Especialista especialistaR = new Especialista("Especialista R", "Aumenta mucho las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 2);
        Especialista especialistaM = new Especialista("Especialista M", "Aumenta mucho las rarezas de los tesoros en el terreno", ESkillType.TREASURE, 3);
        registrar(especialistaU);
        registrar(especialistaR);
        registrar(especialistaM);

        // Excavación
        Excavaciones excavacion1 = new Excavaciones("Excavación I", "Se puede excavar más veces", ESkillType.EXCAVATION, 1);
        Excavaciones excavacion2 = new Excavaciones("Excavación II", "Se puede excavar más veces", ESkillType.EXCAVATION, 2);
        Excavaciones excavacion3 = new Excavaciones("Excavación III", "Se puede excavar más veces", ESkillType.EXCAVATION, 3);
        excavacion2.addDependencia(excavacion1);
        excavacion3.addDependencia(excavacion2);
        registrar(excavacion1);
        registrar(excavacion2);
        registrar(excavacion3);

        // Experiencia
        Experiencia experiencia1 = new Experiencia("Experiencia I", "Aumenta en un porcentaje las acciones de excavación", ESkillType.EXCAVATION, 1);
        Experiencia experiencia2 = new Experiencia("Experiencia II", "Aumenta en un porcentaje las acciones de excavación", ESkillType.EXCAVATION, 2);
        experiencia2.addDependencia(experiencia1);
        registrar(experiencia1);
        registrar(experiencia2);

        // Limpieza
        Limpieza limpieza1 = new Limpieza("Limpieza I", "Excava automáticamente zonas vacías del terreno", ESkillType.EXCAVATION, 1);
        Limpieza limpieza2 = new Limpieza("Limpieza II", "Excava automáticamente zonas vacías del terreno", ESkillType.EXCAVATION, 2);
        limpieza2.addDependencia(limpieza1);
        registrar(limpieza1);
        registrar(limpieza2);

        // Escaneo
        String descEscaneo = "Marca casillas con \"?\" indicando que puede haber un tesoro";
        registrar(new Escaneo("Escaneo A", descEscaneo, ESkillType.EXCAVATION, 1));
        registrar(new Escaneo("Escaneo B", descEscaneo, ESkillType.EXCAVATION, 1));
        registrar(new Escaneo("Escaneo C", descEscaneo, ESkillType.EXCAVATION, 1));
        registrar(new Escaneo("Escaneo D", descEscaneo, ESkillType.EXCAVATION, 1));

        // Licencia (incompatibles entre sí)
        Licencia licenciaA = new Licencia("Licencia A", "Añade los terrenos en fila y en columna", ESkillType.TERRAIN, 1);
        Licencia licenciaB = new Licencia("Licencia B", "Añade los terrenos horizontal y vertical", ESkillType.TERRAIN, 2);
        registrar(licenciaA);
        registrar(licenciaB);
          // Investigación
        registrar(new Investigacion("Investigación",
                "Hace un desglose de cuántos tesoros de cada rareza hay", ESkillType.EXCAVATION));

        // Los especialistas de terreno (agua, arena, océano...) son de las ampliaciones
    }

    /**
     * Registra una habilidad en el repositorio.
     * @param s La habilidad a registrar.
     */
    public static void registrar(Skill s) {
        skills.add(s);
    }

    /**
     * Devuelve todas las habilidades registradas.
     * @return Una copia de la lista de habilidades.
     */
    public static List<Skill> getTodas() {
        return List.copyOf(skills);
    }

    /**
     * Devuelve las habilidades de un tipo, ordenadas alfabéticamente.
     * @param tipo El tipo de habilidad.
     * @return La lista de habilidades de ese tipo.
     */
    public static List<Skill> getPorTipo(ESkillType tipo) {
        List<Skill> lista = new ArrayList<>();
        for (Skill s : skills) {
            if (s.getTipo() == tipo) {
                lista.add(s);
            }
        }
        lista.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));
        return lista;
    }

    /**
     * Busca una habilidad por su nombre.
     * @param nombre El nombre de la habilidad, por ejemplo "Extra I".
     * @return La habilidad con ese nombre, o null si no existe.
     */
    public static Skill get(String nombre) {
        for (Skill s : skills) {
            if (s.getNombre().equals(nombre)) {
                return s;
            }
        }
        return null;
    }
}