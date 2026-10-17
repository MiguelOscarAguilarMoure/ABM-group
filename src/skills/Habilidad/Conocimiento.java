package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
import treasures.ETreasureRarity;

public class Conocimiento extends Skill {
    protected int nivel;

    public Conocimiento(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Devuelve la cantidad de tesoros del tipo de rareza
     * @return [COMMON,UNCOMMON, RARE]
     */
    public int[] efecto() {
        switch (this.nivel) {
            case 1 -> {
                return new int[]{5, 0, 0};
            }
            case 2 -> {
                return new int[]{5, 5, 0};
            }
            case 3 -> {
                return new int[]{0, 5, 2};
            }
            default -> {
                return new int[]{0, 0, 0};
            }
        }
    }
        /**
     * Tesoros extra de una rareza, según el nivel de la habilidad.
     * Lo usa Archeologist al sumar los bonos de todas sus habilidades.
     */
    @Override
    public int getTesorosExtra(ETreasureRarity rareza) {
        int[] e = efecto();   // [común, infrecuente, raro]
        if (rareza == ETreasureRarity.COMMON) {
            return e[0];
        }
        if (rareza == ETreasureRarity.UNCOMMON) {
            return e[1];
        }
        return e[2];   // RARE
    }
}