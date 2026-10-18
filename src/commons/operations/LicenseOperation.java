package commons.operations;

/**
 * Opción "Licencias" del menú principal.
 * <p>
 * En esta entrega no está implementada: en la Base ninguna región pide licencia
 * ({@code Region.necesitaLicencia()} devuelve {@code false}), así que todos los
 * arqueólogos pueden excavar en todas las regiones. La opción solo muestra un
 * aviso.
 * </p>
 * <p>
 * {@code Archeologist} ya tiene preparados {@code addLicencia} y
 * {@code puedeExcavarEn}
 * para cuando se implemente en las ampliaciones.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 1.0
 * @see regions.Region#necesitaLicencia()
 * @see people.Archeologist#puedeExcavarEn(regions.Region)
 */
public class LicenseOperation implements IOperation {

    @Override
    public String getName() {
        return "Licencias";
    }
    /**
     * Muestra que las licencias no están disponibles en esta entrega.
     */
    @Override
    public void operation() {
        System.out.println("-------------------- Licencias --------------------");
        System.out.println("Las licencias no están disponibles en esta entrega.");
    }

}
