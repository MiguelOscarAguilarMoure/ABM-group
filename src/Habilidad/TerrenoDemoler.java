package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoDemoler extends Skill {
    public TerrenoDemoler(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        super(tipo.Especialista, nombre, descripcion, dependencia);
    }
}
