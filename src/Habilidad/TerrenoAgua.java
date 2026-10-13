package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoAgua extends Skill {
    public TerrenoAgua(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        super(tipo.Especialista, nombre, descripcion, dependencia);
    }
}
