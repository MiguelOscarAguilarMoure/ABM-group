package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoAguaProf extends Skill {
    public TerrenoAguaProf(SkyllType tipo, String nombre, String descripcion, String dependencia) {
        super(tipo.Especialista, nombre, descripcion, dependencia);
    }
}
