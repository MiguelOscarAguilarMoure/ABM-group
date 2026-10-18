package skills.Habilidad;

import excavation.TerrainShape;
import java.util.List;
import skills.ESkillType;
import skills.Skill;

/**
 * Habilidades modificadoras de terreno: Licencia A y B.
 * <p>
 * No dan bonos a la excavación: desbloquean nuevas formas de terreno.
 * </p>
 * <ul>
 *   <li>Nivel 1 (Licencia A): fila y columna.</li>
 *   <li>Nivel 2 (Licencia B): horizontal y vertical.</li>
 * </ul>
 * <p>
 * Son incompatibles entre sí: un arqueólogo solo puede tener una Licencia.
 * </p>
 *
 * @author Borja Abal Dios
 * @author Alberto Ameneiros
 * @version 1.0
 * @see Skill#getTerrenos()
 * @see Skill#esIncompatible(Skill)
 */
public class Licencia extends Skill {
    /** Nivel de la habilidad (1 = A, 2 = B). */
    protected final int nivel;   
     /**
     * Crea una habilidad de licencia.
     *
     * @param nombre      Nombre de la habilidad.
     * @param descripcion Descripción de la habilidad.
     * @param tipo        Tipo de la habilidad.
     * @param nivel       Nivel de la habilidad (1 = A, 2 = B).
     */
    public Licencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Formas de terreno que desbloquea (las añade Archeologist.addHabilidad).
     * Las añade {@code Archeologist.addHabilidad()} a los terrenos del arqueólogo.
     * 
     * @return A: fila y columna. B: horizontal y vertical.
     */
    @Override
    public List<TerrainShape> getTerrenos() {
        if (nivel == 1) {
            return List.of(TerrainShape.ROW, TerrainShape.COLUMN);          // Licencia A
        }
        return List.of(TerrainShape.HORIZONTAL, TerrainShape.VERTICAL);     // Licencia B
    }

     /**
     * Indica si es incompatible con otra habilidad.
     * Un arqueólogo solo puede tener una Licencia.
     *
     * @param otra La habilidad con la que se compara.
     * @return {@code true} si la otra también es una Licencia (y no es esta misma).
     */
    @Override
    public boolean esIncompatible(Skill otra) {
        return otra instanceof Licencia && otra != this;
    }
}