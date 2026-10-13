package Habilidades.Habilidad;

import Habilidades.Skill;
import Habilidades.SkyllType;

public class Conocimiento extends Skill {
    protected String nombre = "conocimiento";
    protected String descripcion =
            "Aumenta las Rarezas de los tesoros en el terreno";
    protected int nivel;

    public Conocimiento(SkyllType tipo, String dependencia, String nombre) {
        super(tipo, dependencia);
        this.nombre = nombre;
    }
}