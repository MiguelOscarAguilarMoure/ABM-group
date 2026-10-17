package skills.Habilidad;

import skills.ESkillType;
import skills.Skill;
import treasures.ETreasureRarity;
import treasures.Treasure;

import java.util.ArrayList;

public class Investigacion extends Skill {
    public Investigacion(String nombre, String descripcion, ESkillType tipo) {
        super(nombre, descripcion, tipo);
    }


    public void efecto(ArrayList<Treasure> tesoros) {
        int comun = 0;
        int pocoComun = 0;
        int raro = 0;

        /**
         * Procesa una lista de tesoros, cuenta cuántos hay de cada rareza
         * (comunes, poco comunes y raros) e imprime los resultados por pantalla.
         *
         * @param tesoros la lista de objetos {@link Treasure} a evaluar.
         */
        for (Treasure tesoro : tesoros) {
            ETreasureRarity tipo = tesoro.getRarezaTesoro();

            if (tipo == ETreasureRarity.COMMON) {
                comun++;
            } else if (tipo == ETreasureRarity.UNCOMMON) {
                pocoComun++;
            } else if (tipo == ETreasureRarity.RARE) {
                raro++;
            }
        }

        System.out.println("Tesoros comunes: " + comun);
        System.out.println("Tesoros poco comunes: " + pocoComun);
        System.out.println("Tesoros raros: " + raro);
    }

    @Override
    public Boolean isInvestigacion() {
        return false;
    }
}
