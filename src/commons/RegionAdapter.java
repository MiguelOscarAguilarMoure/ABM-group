package commons;

import com.google.gson.*;
import commons.repositories.RegionRepository;
import regions.Region;

import java.lang.reflect.Type;

/**
 * Adaptador GSON para serializar y deserializar la clase {@link regions.Region}.
 *
 * @author Alberto
 * @version 1.0
 */
public class RegionAdapter implements JsonSerializer<Region>, JsonDeserializer<Region> {

    /**
     * Serializa una región a su nombre en formato JSON.
     */
    @Override
    public JsonElement serialize(Region region, Type typeOfSrc, JsonSerializationContext context) {
        if (region == null) {
            return new JsonPrimitive("");
        }
        return new JsonPrimitive(region.getNombre());
    }

    /**
     * Deserializa una región buscando por nombre o código en el repositorio.
     */
    @Override
    public Region deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String nombreRegion = json.getAsString();
        if (nombreRegion == null || nombreRegion.isEmpty()) {
            return null;
        }
        for (Region r : RegionRepository.getTodas()) {
            if (r.getNombre().equalsIgnoreCase(nombreRegion) || r.getCodigo().equalsIgnoreCase(nombreRegion)) {
                return r;
            }
        }
        return null;
    }
}