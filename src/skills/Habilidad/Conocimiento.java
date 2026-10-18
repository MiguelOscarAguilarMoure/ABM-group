package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Habilidades de conocimiento: Aprendiz, Experto y Maestro.
 * <p>
 * Añaden a la excavación tesoros extra de una rareza concreta, según su nivel:
 * </p>
 * <ul>
 * <li>Nivel 1 (Aprendiz): 5 comunes.</li>
 * <li>Nivel 2 (Experto): 5 comunes y 5 infrecuentes.</li>
 * <li>Nivel 3 (Maestro): 5 infrecuentes y 2 raros.</li>
 * </ul>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getTesorosExtra(ETreasureRarity)
 */
public class Conocimiento extends Skill {
    /** Nivel de la habilidad (1, 2 o 3). */
    protected int nivel;

    /**
     * Crea una habilidad de conocimiento.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = Aprendiz, 2 = Experto, 3 =
     *                    Maestro).
     */
    public Conocimiento(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Tesoros extra de cada rareza según el nivel.
     *
     * @return Array con los tesoros extra: [común, infrecuente, raro].
     */
    public int[] efecto() {
        switch (this.nivel) {
            case 1 -> {
                return new int[] { 5, 0, 0 };
            }
            case 2 -> {
                return new int[] { 5, 5, 0 };
            }
            case 3 -> {
                return new int[] { 0, 5, 2 };
            }
            default -> {
                return new int[] { 0, 0, 0 };
            }
        }
    }

    /**
     * Tesoros extra de una rareza, según el nivel de la habilidad.
     * Lo usa {@code Archeologist} al sumar los bonos de todas sus habilidades.
     *
     * @param rareza La rareza de los tesoros.
     * @return Número de tesoros extra de esa rareza.
     */
    @Override
    public int getTesorosExtra(ETreasureRarity rareza) {
        int[] e = efecto(); // [común, infrecuente, raro]
        if (rareza == ETreasureRarity.COMMON) {
            return e[0];
        }
        if (rareza == ETreasureRarity.UNCOMMON) {
            return e[1];
        }
        return e[2]; // RARE
    }
}