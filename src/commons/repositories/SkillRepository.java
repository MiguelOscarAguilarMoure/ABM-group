package commons.repositories;

import java.util.ArrayList;
import java.util.List;

import skills.ESkillType;
import skills.Skill;

/**
 * Repositorio de todas las habilidades del sistema.
 * Las habilidades se registran una sola vez en preInit y se obtienen siempre de aquí.
 */
public class SkillRepository {

    /** Lista con todas las habilidades registradas. */
    private static final List<Skill> habilidades = new ArrayList<>();

    /**
     * Registra una habilidad en el repositorio.
     * @param s La habilidad a registrar.
     */
    public static void registrar(Skill s) {
        habilidades.add(s);
    }

    /**
     * Devuelve todas las habilidades registradas.
     * @return Una copia de la lista de habilidades.
     */
    public static List<Skill> getTodas() {
        return List.copyOf(habilidades);
    }

    /**
     * Devuelve las habilidades de un tipo, ordenadas alfabéticamente.
     * @param tipo El tipo de habilidad.
     * @return La lista de habilidades de ese tipo.
     */
    public static List<Skill> getPorTipo(ESkillType tipo) {
        List<Skill> lista = new ArrayList<>();
        for (Skill s : habilidades) {
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
        for (Skill s : habilidades) {
            if (s.getNombre().equals(nombre)) {
                return s;
            }
        }
        return null;
    }
}