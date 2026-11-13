package regions;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import commons.repositories.RegionRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Carga dinámica de los tesoros de las regiones desde sus ficheros JSON.
 * <p>
 * Cada región tiene un fichero con su código como nombre (por ejemplo
 * {@code data/regions/EGP.json}) que contiene la lista de sus tesoros.
 * Se llama una vez desde {@code Archaios.preInit()}, después de registrar las regiones.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see TreasureData
 */
public class RegionLoader {

    /** Carpeta donde están los ficheros JSON de las regiones. */
    private static final String CARPETA = "data/regions/";

    /** Clase de métodos estáticos: no se crean objetos de ella. */
    private RegionLoader() {
    }

    /**
     * Carga los tesoros de todas las regiones registradas en {@link RegionRepository}.
     * Si el fichero de una región falta o está mal, lo avisa y sigue con las demás.
     */
    public static void cargarTesoros() {
        for (Region r : RegionRepository.getTodas()) {
            cargarRegion(r);
        }
    }

    // AUXILIARES

    /**
     * Lee el fichero JSON de una región y le añade sus tesoros.
     *
     * @param region La región a la que se añaden los tesoros.
     */
    private static void cargarRegion(Region region) {
        String ruta = CARPETA + region.getCodigo() + ".json";
        Gson gson = new Gson();

        try (Reader lector = new InputStreamReader(new FileInputStream(ruta), StandardCharsets.UTF_8)) {
            TreasureData[] datos = gson.fromJson(lector, TreasureData[].class);
            for (TreasureData d : datos) {
                region.addTesoro(crearTesoro(d));
            }
        } catch (IOException | JsonParseException e) {
            System.out.println("No se han podido cargar los tesoros de " + region.getNombre() + " (" + ruta + ").");
        }
    }

    /**
     * Convierte un tesoro leído del JSON en un {@link Treasure}.
     *
     * @param d Los datos del tesoro tal y como vienen en el JSON.
     * @return El tesoro creado.
     */
    private static Treasure crearTesoro(TreasureData d) {
        ETreasureRarity rareza = ETreasureRarity.getByCode(d.rarity);
        ETreasureType tipo = ETreasureType.getByCode(d.type);
        return new Treasure(d.code, d.name, d.description, rareza, tipo, d.parts, d.points);
    }
}