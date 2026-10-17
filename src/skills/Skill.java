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

    public static ArrayList<Skill> getSkills() {
        return Skills;
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




    //=======================================================================================================

    /**
     * Muestra la información de una habilidad.
     *
     * El método imprime el nombre, la descripción y, si existen, la lista de habilidades
     * necesarias (dependencias) requeridas para su desbloqueo. Adicionalmente, evalúa si
     * la habilidad pertenece a un tipo restrictivo para advertir sobre su incompatibilidad.
     *
     *
     * @param habilidad La instancia de {@link Skill} de la cual se desea visualizar la información.
     */
    public static void mostrarHabilidad(Skill habilidad) {
        System.out.println("------------------ " + habilidad.getNombre() + " ------------------");
        System.out.println(habilidad.getDescripcion());

        // Verificación segura contra Null y si contiene elementos
        if (habilidad.getDependecias() != null && !habilidad.getDependecias().isEmpty()) {
            System.out.println("---- Dependencias ----");
            for (Skill dependencia : habilidad.getDependecias()) {
                System.out.println("- " + dependencia.getNombre());
            }
        }

        if ((habilidad instanceof Licencia) || habilidad instanceof Especialista) {
            // Imprime el nombre limpio de la clase (Licencia o Especialista)
            System.out.println("Incompatible con las habilidades " + habilidad.getClass().getSimpleName());
        }
    }
}
