package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Conocimiento extends Skill {
    protected String nombre = "conocimiento";
    protected String descripcion =
            "Aumenta las Rarezas de los tesoros en el terreno";
    protected int nivel;

    /**
     *
     * @param tipo
     * @param dependencia
     * @param nombre
     */
    public Conocimiento(SkyllType tipo, String dependencia, String nombre) {
        super(tipo.Tesoro, dependencia);
        this.nivel = nivel;
    }
    //GETTERS & SETTER (NIVEL) ===========================================


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // ==================================================================

    /**
     * Efecto de la habilidad
     * @param habilidad
     * @return
     */

    public int efecto(Conocimiento habilidad) {
        int nivel = habilidad.getNivel();

        switch (nivel) {
            case 0 -> {
                return 1;
            }
            case 1 -> {
                return 2;
            }
            case 2 -> {
                return 4;
            }
            default -> {
                return 0;
            }

        }
    }
}