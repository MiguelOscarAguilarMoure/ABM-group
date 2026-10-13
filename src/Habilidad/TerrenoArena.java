package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoArena extends Skill {
    public TerrenoArena(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        super(tipo.Especialista, nombre, descripcion, dependencia);
    }
}
