package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

/**
 * Extra I, II o III: añade tesoros aleatorios al terreno.
 */
public class Extra extends Skill {

    protected final int nivel;   // 1, 2 o 3

    public Extra(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    /**
     * Tesoros aleatorios adicionales según el nivel.
     * @return 3, 7 o 10.
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

    /** Tesoros aleatorios extra (lo suma Archeologist.getTesorosExtra). */
    @Override
    public int getTesorosExtra() {
        return efecto();
    }
}