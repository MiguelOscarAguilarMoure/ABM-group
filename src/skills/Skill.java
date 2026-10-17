package skills;


import skills.Habilidad.*;
import treasures.ETreasureRarity;

import java.util.ArrayList;
import java.util.Scanner;

import static commons.repositories.SkillRepository.Skills;

public abstract class Skill {

    final protected String nombre;
    final protected String descripcion;
    final protected ESkillType tipo;
    public ArrayList<Skill> dependecias = new ArrayList<>();

    /**
     *
     * @param nombre      nombre de la habilidad
     * @param descripcion descripcion de la habilidad
     * @param tipo        Tipo de habilidad
     */
    public Skill(String nombre, String descripcion, ESkillType tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }


    //== GETTERS & SETTERS ==================================================================================

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ESkillType getTipo() {
        return tipo;
    }

    public ArrayList<Skill> getDependecias() {
        return dependecias;
    }

    public int getTesorosExtra() {
        return 0;
    }

    public int getTesorosExtra(ETreasureRarity rareza) {
        return 0;
    }

    public int getProbabilidadExtrar(ETreasureRarity rareza) {
        return 0;
    }

    public int getAccionesExtra() {
        return 0;
    }

    public int getPorcentajeAcciones() {
        return 0;
    }

    public int getLimpieza() {
        return 0;
    }

    public int getSuposicion() {
        return 0;
    }

    public Boolean isInvestigacion() {
        return false;
    }

    public Object getDependencias() {
        return this.dependecias;
    }


    //=======================================================================================================


}
