package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Habilidades de tesoros extra: Extra I, II y III.
 * <p>
 * Añaden tesoros aleatorios al terreno de la excavación según su nivel:
 * </p>
 * <ul>
 * <li>Nivel 1 (Extra I): +3 tesoros.</li>
 * <li>Nivel 2 (Extra II): +7 tesoros.</li>
 * <li>Nivel 3 (Extra III): +10 tesoros.</li>
 * </ul>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getTesorosExtra()
 */
public class Extra extends Skill {
    /** Nivel de la habilidad (1, 2 o 3). */
    protected final int nivel;

    /**
     * Crea una habilidad de tesoros extra.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = I, 2 = II, 3 = III).
     */
    public Extra(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    /**
     * Tesoros aleatorios extra según el nivel.
     *
     * @return 3, 7 o 10 tesoros (0 si el nivel no es válido).
     */
    public int efecto() {
        switch (nivel) {
            case 1:
                return 3;
            case 2:
                return 7;
            case 3:
                return 10;
            default:
                return 0;
        }
    }

    /**
     * Tesoros aleatorios extra según el nivel.
     * Lo suma {@code Archeologist.getTesorosExtra()} con el resto de habilidades.
     *
     * @return Número de tesoros extra.
     */
    @Override
    public int getTesorosExtra() {
        return efecto();
    }
}