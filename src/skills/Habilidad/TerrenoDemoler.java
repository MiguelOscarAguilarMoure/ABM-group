package skills.Habilidad;

import excavation.ETerreno;
import skills.ESkillType;
import skills.Skill;

/**
 * Habilidades "Especialista en demoliciones" (A, B...).
 * <p>
 * Estos arqueólogos están acostumbrados a quitar piedras: antes de empezar la
 * excavación rompen hasta un número de piedras aleatorias, que pasan a tierra.
 * Esto se hace antes de la limpieza y del escaneo.
 * </p>
 * <p>
 * Las demoliciones son compatibles entre sí (no sobrescriben
 * {@code esIncompatible}), así que otras expansiones pueden añadir más.
 * </p>
 *
 * @author Miguel Aguilar Moure
 * @version 1.0
 * @see Skill#getCasillasCambiadas(ETerreno)
 */
public class TerrenoDemoler extends Skill {
    /** Piedras que rompe como máximo. */
    private final int piedras;

    /**
     * Crea una habilidad de demoliciones.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param piedras     Número máximo de piedras que rompe (mayor que 0).
     * @throws IllegalArgumentException si {@code piedras} no es mayor que 0.
     */
    public TerrenoDemoler(String nombre, String descripcion, ESkillType tipo, int piedras) {
        super(nombre, descripcion, tipo);
        if (piedras <= 0) {
            throw new IllegalArgumentException("Las piedras a romper deben ser más de 0.");
        }
        this.piedras = piedras;
    }

    /**
     * Casillas que se cambian por tierra al empezar la excavación.
     * Lo suma {@code Archeologist.getCasillasCambiadas()} con el resto de habilidades.
     *
     * @param terreno El terreno que se cambia.
     * @return Las piedras que rompe si el terreno es piedra; 0 en cualquier otro.
     */
    @Override
    public int getCasillasCambiadas(ETerreno terreno) {
        return (terreno == ETerreno.PIEDRA) ? piedras : 0;
    }


    /**
     * aplica el efecto de la habilidad
     *
     * @return numero de piedras que han de romperse
     */
    public int efecto() {
        return 5;
    }
}