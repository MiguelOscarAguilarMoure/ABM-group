package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
/**
 * Habilidades de escaneo: Escaneo A, B, C y D.
 * <p>
 * Cada escaneo marca con '?' tres casillas al empezar la excavación:
 * una con tesoro y dos sin él. Tener varios escaneos suma más casillas marcadas.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getSuposicion()
 */
public class Escaneo extends Skill {
     /** Nivel de la habilidad (1 = A, 2 = B, 3 = C, 4 = D). */
    protected int nivel;
     /** Casillas que marca cada escaneo con '?'. */
     private static final int CASILLAS = 3;
     /**
     * Crea una habilidad de escaneo.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = A, 2 = B, 3 = C, 4 = D).
     */
    public Escaneo(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
    }

    /**
     * Indica 3 casillas, donde 1 de ellas tiene un tesoro
     * @return
     */
    public int efecto() {
        return 3;
    }
    public int getLimpieza() {return 0; }
    /** Cada escaneo marca 3 casillas con '?' (lo suma Archeologist.getSuposicion). */
    @Override
    public int getSuposicion() {
        return CASILLAS;
    }
}

