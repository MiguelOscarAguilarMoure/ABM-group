package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoOceano extends Skill {
    public TerrenoOceano(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        super(tipo.Especialista, nombre, descripcion, dependencia);
    }
}
