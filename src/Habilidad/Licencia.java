package Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Licencia extends Skill {
    protected int nivel;

    public Licencia(SkyllType tipo, String nombre, String descripcion, String dependencia, int nivel) {
        super(tipo.Terreno, nombre, descripcion, dependencia);
        this.nivel = nivel;
    }
}
