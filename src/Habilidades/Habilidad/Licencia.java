package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Licencia extends Skill {
    protected int nivel;
    protected String nombre = "Licencia";
    protected String descripcion =
            "Extiende el Terreno \n (A) Vertical o Horizontalmente \n (B) Ambas dimensiones";

    public Licencia(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}

