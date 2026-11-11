package commons.operations;

import java.util.ArrayList;
import java.util.List;
import regions.Region;
import commons.Archaios;
import commons.repositories.RegionRepository;
import helpers.InputHelper;
import people.Archeologist;

/**
 * Opción "Licencias" del menú principal.
 * <p>
 * Permite comprar a un arqueólogo una licencia para excavar en una región
 * difícil.
 * La primera licencia de cada arqueólogo cuesta {@value #COSTE_PRIMERA} puntos
 * y cada
 * una de las siguientes {@value #COSTE_EXTRA} puntos más por cada licencia que
 * ya tenga.
 * </p>
 *
 * @author Borja Abal Dios
 * @version 2.0
 * @see people.Archeologist#addLicencia(String)
 * @see commons.repositories.RegionRepository#getDificiles()
 */
public class LicenseOperation implements IOperation {
    /** Coste de la primera licencia de un arqueólogo. */
    private static final int COSTE_PRIMERA = 20;
    /** Coste extra por cada licencia que ya tenga. */
    private static final int COSTE_EXTRA = 10;
    /** Partida en curso (arqueólogos y puntos). */
    private final Archaios juego;

    /**
     * Crea la operación de licencias.
     *
     * @param juego La partida en curso.
     */
    public LicenseOperation(Archaios juego) {
        this.juego = juego;
    }

    @Override
    public String getName() {
        return "Licencias";
    }

    /**
     * Pide un arqueólogo, muestra las regiones difíciles para las que no tiene
     * licencia
     * y, si hay puntos suficientes y se confirma, le compra la licencia de la
     * elegida.
     */
    @Override
    public void operation() {
        System.out.println("-------------------- Licencias --------------------");
        Archeologist a = eligirArqueologo();
        if (a == null) {
            return;
        }
        List<Region> disponibles = regionSinLicencia(a);
        if (disponibles.isEmpty()) {
            System.out.println(a.getNombre() + " ya tiene licencia para todas las regiones difíciles.");
            return;
        }

        int coste = getCoste(a);
        System.out.println("Tienes " + juego.getPuntos() + " puntos.");
        System.out.println("Una licencia para " + a.getNombre() + " cuesta " + coste + " puntos.");
        if (juego.getPuntos() < coste) {
            System.out.println("No tienes puntos suficientes");
            return;
        }

        Region r = elegirRegion(disponibles);
        if (r == null) {
            return;
        }
        if (!InputHelper.confirmar("¿Comprar la licencia de " + r.getNombre() + " por " + coste + " puntos?")) {
            return;
        }

        a.addLicencia(r.getCodigo());
        juego.addPuntos(-coste);
        System.out.println(a.getNombre() + " ya puede excavar en " + r.getNombre() + ".");
    }

    // Auxiliares

    /**
     * Calcula el coste de la siguiente licencia de un arqueólogo.
     *
     * @param a El arqueólogo.
     * @return 20 si no tiene ninguna; 10 más por cada una que ya tenga.
     */
    private int getCoste(Archeologist a) {
        return COSTE_PRIMERA + COSTE_EXTRA * a.getLicencias().size();
    }

    /**
     * Devuelve las regiones difíciles para las que el arqueólogo aún no tiene
     * licencia.
     *
     * @param a El arqueólogo.
     * @return Una lista nueva con esas regiones (vacía si ya las tiene todas).
     */
    private List<Region> regionSinLicencia(Archeologist a) {
        List<Region> lista = new ArrayList<>();
        for (Region r : RegionRepository.getDificiles()) {
            if (!a.getLicencias().contains(r.getCodigo())) {
                lista.add(r);
            }
        }
        return lista;
    }

    /**
     * Muestra los arqueólogos con su número de licencias y pide uno.
     *
     * @return El arqueólogo elegido, o {@code null} si elige 0.
     */
    private Archeologist eligirArqueologo() {
        List<Archeologist> lista = juego.getArqueologos();
        for (int i = 0; i < lista.size(); i++) {
            Archeologist a = lista.get(i);
            System.out.println((i + 1) + ". " + a.getNombre() + " (" + a.getLicencias().size() + " licencias)");

        }
        System.out.println("0. Volver");
        int op = InputHelper.leerOpcion("Arqueologo", lista.size());
        if (op == 0) {
            return null;
        }
        return lista.get(op - 1);
    }

    /**
     * Muestra las regiones disponibles y pide una.
     *
     * @param regiones Las regiones entre las que elegir.
     * @return La región elegida, o {@code null} si elige 0.
     */
    private Region elegirRegion(List<Region> regiones) {
        for (int i = 0; i < regiones.size(); i++) {
            System.out.println((i + 1) + ". " + regiones.get(i).getNombre());
        }
        System.out.println("0. Volver");
        int op = InputHelper.leerOpcion("Region: ", regiones.size());
        if (op == 0) {
            return null;
        }
        return regiones.get(op - 1);
    }
}
