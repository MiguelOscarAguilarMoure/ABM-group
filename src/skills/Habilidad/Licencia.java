package skills.Habilidad;

import excavation.TerrainShape;
import java.util.List;
import skills.ESkillType;
import skills.Skill;

/**
 * Licencia A o B: habilidad modificadora de terreno. No da bonos,
 * desbloquea nuevas formas de terreno. Son incompatibles entre sí.
 */
public class Licencia extends Skill {

    protected final int nivel;   // 1 = A, 2 = B

    public Licencia(String nombre, String descripcion, ESkillType tipo, int nivel) {
        super(nombre, descripcion, tipo);
        this.nivel = nivel;
    }

    /**
     * Formas de terreno que desbloquea (las añade Archeologist.addHabilidad).
     * @return A: fila y columna. B: horizontal y vertical.
     */
    @Override
    public List<TerrainShape> getTerrenos() {
        if (nivel == 1) {
            return List.of(TerrainShape.ROW, TerrainShape.COLUMN);          // Licencia A
        }
        return List.of(TerrainShape.HORINZOTAL, TerrainShape.VERTICAL);     // Licencia B
    }

    /** Un arqueólogo solo puede tener una Licencia. */
    @Override
    public boolean esIncompatible(Skill otra) {
        return otra instanceof Licencia && otra != this;
    }
}