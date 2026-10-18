package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Especialista U, R o M: aumenta la probabilidad de que salgan tesoros
 * infrecuentes y/o raros. Son incompatibles entre sí.
 */
public class Especialista extends Skill {

    protected final int nivel;   // 1 = U, 2 = R, 3 = M

    public Especialista(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Probabilidad extra según el nivel.
     * @return [infrecuente, raro]
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
     * Probabilidad extra de que salga un tesoro de esa rareza.
     * Lo usa Archeologist (y Excavation en tirarRareza).
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

    /** Un arqueólogo solo puede tener un Especialista. */
    @Override
    public boolean esIncompatible(Skill otra) {
        return otra instanceof Especialista && otra != this;
    }
}