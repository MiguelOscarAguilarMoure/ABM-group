package commons;

import com.google.gson.*;
import commons.repositories.SkillRepository;
import skills.Skill;

import java.lang.reflect.Type;

/**
 * Adaptador GSON para serializar y deserializar la clase abstracta {@link skills.Skill}.
 *
 * @author Alberto
 * @version 1.0
 */
public class SkillAdapter implements JsonSerializer<Skill>, JsonDeserializer<Skill> {

    /**
     * Serializa una habilidad a su nombre en formato JSON.
     */
    @Override
    public JsonElement serialize(Skill skill, Type typeOfSrc, JsonSerializationContext context) {
        if (skill == null) {
            return new JsonPrimitive("");
        }
        return new JsonPrimitive(skill.getNombre());
    }

    /**
     * Deserializa una habilidad buscando su coincidencia en el repositorio.
     */
    @Override
    public Skill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String nombreSkill = json.getAsString();
        if (nombreSkill == null || nombreSkill.isEmpty()) {
            return null;
        }
        for (Skill s : SkillRepository.getTodas()) {
            if (s.getNombre().equalsIgnoreCase(nombreSkill)) {
                return s;
            }
        }
        return null;
    }
}