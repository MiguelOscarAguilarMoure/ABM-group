package people;

import excavation.TerrainShape;
import java.util.ArrayList;
import javax.swing.plaf.synth.Region;
import skills.Skill;
import treasures.ETreasureRarity;

public class Archeologist {
    public static final int NIVEL_MAX = 5;
    private String nombre;
    private int experiencia;
    private int nivel;
    private ArrayList<Skill> listaHabilidades = new ArrayList<>();
    private ETreasureRarity rareza;
    private ArrayList<TerrainShape> tipoTerreno = new ArrayList<>();
    private Region especializacion = null;

    public Archeologist(String nombre, int experiencia, int nivel, ArrayList<Skill> listaHabilidades,
            ETreasureRarity rareza, ArrayList<TerrainShape> tipoTerreno, Region especializacion) {
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.listaHabilidades = listaHabilidades;
        this.rareza = rareza;
        this.tipoTerreno = tipoTerreno;
        this.especializacion = especializacion;
    }

    public static int getNivelMax() {
        return NIVEL_MAX;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public ArrayList<Skill> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(ArrayList<Skill> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public ETreasureRarity getRareza() {
        return rareza;
    }

    public void setRareza(ETreasureRarity rareza) {
        this.rareza = rareza;
    }

    public ArrayList<TerrainShape> getTipoTerreno() {
        return tipoTerreno;
    }

    public void setTipoTerreno(ArrayList<TerrainShape> tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    public Region getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(Region especializacion) {
        this.especializacion = especializacion;
    }

    public int getExpNecesaria() {
        return 50 + 50 * nivel;
    }

    public boolean addExperiencia(int exp) {
        experiencia += exp;
        if (nivel < NIVEL_MAX && experiencia >= getExpNecesaria()) {
            experiencia -= getExpNecesaria();
            nivel++;
            return true;
        }
        return false;
    }
    
}
