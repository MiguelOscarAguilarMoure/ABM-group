package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Habilidades de especialista: Especialista U, R y M.
 * <p>
 * Aumentan la probabilidad de que los tesoros de la excavación sean infrecuentes o raros:
 * </p>
 * <ul>
 *   <li>Nivel 1 (Especialista U): +10 % infrecuentes.</li>
 *   <li>Nivel 2 (Especialista R): +10 % raros.</li>
 *   <li>Nivel 3 (Especialista M): +5 % infrecuentes y +5 % raros.</li>
 * </ul>
 * <p>
 * Son incompatibles entre sí: un arqueólogo solo puede tener un Especialista.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getProbabilidadExtra(ETreasureRarity)
 * @see Skill#esIncompatible(Skill)
 */
public class Especialista extends Skill {
    /** Nivel de la habilidad (1 = U, 2 = R, 3 = M). */
    protected final int nivel;   
     /**
     * Crea una habilidad de especialista.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = U, 2 = R, 3 = M).
     */
    public Especialista(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Probabilidad extra de cada rareza según el nivel.
     *
     * @return Array con los puntos porcentuales extra: [infrecuente, raro].
     */
    public int[] efecto() {
        switch (nivel) {
            case 1:
                return new int[]{10, 0};   // Especialista U
            case 2:
                return new int[]{0, 10};   // Especialista R
            case 3:
                return new int[]{5, 5};    // Especialista M
            default:
                return new int[]{0, 0};
        }
    }

    /**
     * Probabilidad extra de que un tesoro sea de una rareza, según el nivel.
     * Lo suma {@code Archeologist} y lo usa {@code Excavation} en {@code tirarRareza}.
     *
     * @param rareza La rareza de los tesoros.
     * @return Puntos porcentuales extra (0 para los comunes).
     */
    @Override
    public int getProbabilidadExtra(ETreasureRarity rareza) {
        int[] e = efecto();
        if (rareza == ETreasureRarity.UNCOMMON) {
            return e[0];
        }
        if (rareza == ETreasureRarity.RARE) {
            return e[1];
        }
        return 0;   // los comunes no tienen probabilidad extra
    }

     /**
     * Indica si es incompatible con otra habilidad.
     * Un arqueólogo solo puede tener un Especialista.
     *
     * @param otra La habilidad con la que se compara.
     * @return {@code true} si la otra también es un Especialista (y no es esta misma).
     */
    @Override
    public boolean esIncompatible(Skill otra) {
        return otra instanceof Especialista && otra != this;
    }
}