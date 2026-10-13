package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class TerrenoDemoler extends Skill {

    protected String nombre = "Demoler";
    protected String descripcion =
            "Permite Destruir estructuras";

    public TerrenoDemoler(SkyllType tipo, String dependencia) {
        super(tipo, dependencia);
    }
}
