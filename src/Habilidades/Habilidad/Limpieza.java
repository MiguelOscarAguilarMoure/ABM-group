package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Limpieza extends Skill {
    protected int nivel;

    protected String nombre = "Limpieza";
    protected String descripcion =
            "Excava automaticamente zonas del terreno";

    public Limpieza(SkyllType tipo, String dependencia, int nivel) {
        super(tipo, dependencia);
        this.nivel = nivel;
    }
}
