package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Habilidad de Investigación.
 * <p>
 * En cada turno de la excavación muestra cuántos tesoros de cada rareza
 * (comunes, infrecuentes y raros) quedan sin excavar en la rejilla.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#isInvestigacion()
 */
public class Investigacion extends Skill {
     /**
     * Crea la habilidad de Investigación.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     */
    public Investigacion(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }

     /**
     * Activa la información de tesoros restantes en la excavación.
     * Lo comprueba {@code Excavation.mostrarTurno()} a través de {@code Archeologist.isInvestigacion()}.
     *
     * @return Siempre {@code true}.
     */
    @Override
    public boolean isInvestigacion() {
        return true;
    }
}