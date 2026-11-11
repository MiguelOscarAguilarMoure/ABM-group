package regions;

import excavation.ETerreno;
import java.util.Map;

/**
 * Base de las regiones difíciles de las expansiones (Egipto, Petra, Sumeria...).
 * <p>
 * Funciona igual que una región básica, pero además guarda la expansión a la
 * que pertenece y la proporción de cada terreno, que usa la excavación para
 * generar la rejilla.
 * </p>
 * <p>
 * Es abstracta: cada región difícil es una subclase con su código estático
 * (por ejemplo {@code Egypt}). Así {@link #getExpansion()} y
 * {@link #getProporciones()} se escriben una sola vez.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see IRegionDificil
 */
public abstract class RegionDificil extends Region implements IRegionDificil {

    /** Expansión a la que pertenece la región. */
    private final String expansion;

    /** Porcentaje de la rejilla que ocupa cada terreno. */
    private final Map<ETerreno, Integer> proporciones;

    /**
     * Crea una región difícil. Solo lo usan las subclases de cada región.
     *
     * @param nombre       Nombre de la región (por ejemplo "Egipto").
     * @param codigo       Código de la región (por ejemplo "EGP").
     * @param expansion    Expansión a la que pertenece.
     * @param proporciones Porcentaje de cada terreno en la rejilla (deben sumar 100).
     */
    protected RegionDificil(String nombre, String codigo, String expansion,
            Map<ETerreno, Integer> proporciones) {
        super(nombre, codigo);
        this.expansion = expansion;
        this.proporciones = proporciones;
    }

    @Override
    public Map<ETerreno, Integer> getProporciones() {
        return proporciones;
    }

    @Override
    public String getExpansion() {
        return expansion;
    }
}