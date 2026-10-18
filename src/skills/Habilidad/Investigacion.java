package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Investigación: en cada ronda de excavación muestra cuántos tesoros
 * de cada rareza quedan en la rejilla.
 */
public class Investigacion extends Skill {

    public Investigacion(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }

    /** Activa la información de tesoros en la excavación (la comprueba Excavation.mostrarTurno). */
    @Override
    public boolean isInvestigacion() {
        return true;
    }
}