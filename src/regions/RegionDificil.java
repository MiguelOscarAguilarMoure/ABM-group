package regions;

import java.util.Map;

import excavation.ETerreno;

/**
 * Región difícil de una expansión (Egipto, Petra, Sumeria...).
 * <p>
 * Funciona igual que una región básica, pero además guarda la expansión a la
 * que
 * pertenece y la proporción de cada terreno, que usa la excavación para generar
 * la rejilla.
 * Como todas las regiones difíciles solo se diferencian en sus datos, se usa
 * esta única
 * clase en lugar de una por región.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see IRegionDificil
 */
public class RegionDificil extends Region implements IRegionDificil {
    /** Expansión a la que pertenece la región. */
    private final String expansion;
    /** Porcentaje de la rejilla que ocupa cada terreno. */

    /**
     * Crea una región difícil.
     *
     * @param nombre       Nombre de la región (por ejemplo "Egipto").
     * @param codigo       Código de la región (por ejemplo "EGP").
     * @param expansion    Expansión a la que pertenece.
     * @param proporciones Porcentaje de cada terreno en la rejilla (deben sumar
     *                     100).
     */
    private final Map<ETerreno, Integer> proporciones;

    public RegionDificil(String nombre, String codigo, String expansion, Map<ETerreno, Integer> proporciones) {
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
