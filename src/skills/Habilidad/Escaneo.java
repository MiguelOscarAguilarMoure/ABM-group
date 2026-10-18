package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;

public class Escaneo extends Skill {
    protected int nivel;
     private static final int CASILLAS = 3;

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

