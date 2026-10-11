package regions;

import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

public class Grecia extends Region {
    public static final String CODE = "GRC";

    public Grecia() {
        super("Grecia");
        listaTesoros.add(new Treasure(CODE + "_MON_001", "Calco griego",
                "Moneda griega de cobre que tenía el menor valor.",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
        
    }

    @Override
    public String getCodigo() {
        return CODE;
    }
}
