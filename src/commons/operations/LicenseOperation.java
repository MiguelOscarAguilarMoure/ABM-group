package commons.operations;
/**
 * Opción Licencias del menú principal.
 */
public class LicenseOperation implements IOperation {

    @Override
    public String getName() {
        return "Licencias";
    }

    @Override
    public void operation() {
        System.out.println("-------------------- Licencias --------------------");
        System.out.println("Las licencias no están disponibles en esta entrega.");
    }
    
}
