package regions.civilizaciones;

import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Región de Roma.
 */
public class Rome extends Region {

    public static final String CODIGO = "ROM";

    public Rome() {
        super("Roma");
        treasures.add(new Treasure(CODIGO + "_MON_001", "As",
                "La moneda de la Antigua Roma de menor valor. Hecha de cobre.",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
        treasures.add(new Treasure(CODIGO + "_MON_002", "Dupondio",
                "Moneda de bronce de poco valor.",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
        treasures.add(new Treasure(CODIGO + "_MON_003", "Sestercio",
                "Moneda de bronce que se utilizaba como medida estándar para medir valores.",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
        treasures.add(new Treasure(CODIGO + "_MON_004", "Denario",
                "Moneda de plata de gran valor que equivale a diez ases.",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
        treasures.add(new Treasure(CODIGO + "_MON_005", "Áureo",
                "Moneda de oro de gran valor equivalente a veinticinco denarios.",
                ETreasureRarity.UNCOMMON, ETreasureType.COIN, 1, 2));
        treasures.add(new Treasure(CODIGO + "_CUE_001", "Cuenta de nácar",
                "Perlas y conchas perforadas utilizadas para confeccionar collares y pulseras.",
                ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));
        treasures.add(new Treasure(CODIGO + "_CUE_002", "Cuenta de gema",
                "Piedras semipreciosas talladas para confeccionar adornos, pulseras y collares.",
                ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));
        treasures.add(new Treasure(CODIGO + "_CUE_003", "Cuenta de metal precioso",
                "Pequeñas piezas de oro, plata o bronce para confeccionar collares y pulseras.",
                ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));
        treasures.add(new Treasure(CODIGO + "_JOY_001", "Pulseras de esferas",
                "Juego de pulseras de oro formadas por pares de semiesferas en fila.",
                ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 4, 2));
        treasures.add(new Treasure(CODIGO + "_JOY_002", "Brazalete de serpiente",
                "Brazalete con forma de serpiente enroscada.",
                ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 5, 3));
        treasures.add(new Treasure(CODIGO + "_JOY_003", "Ceñidor",
                "Cadena decorada que se ponía sobre la ropa para ajustarla al pecho.",
                ETreasureRarity.RARE, ETreasureType.JEWELRY, 6, 5));
        treasures.add(new Treasure(CODIGO + "_JOY_004", "Fíbula de ballesta",
                "Hebilla para sujetar la ropa con forma de ballesta.",
                ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 4, 3));
        treasures.add(new Treasure(CODIGO + "_JOY_005", "Lúnula",
                "Colgante con forma de luna que llevaban las mujeres hasta el día de su boda.",
                ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 5, 2));
        treasures.add(new Treasure(CODIGO + "_CER_001", "Jarra en terra sigillata",
                "Jarra creada mediante terra sigillata, cerámica estampada en un molde para decorarla.",
                ETreasureRarity.COMMON, ETreasureType.CERAMIC, 20, 1));
        treasures.add(new Treasure(CODIGO + "_CER_002", "Vaso en terra sigilliata",
                "Vaso creado mediante terra sigillata, cerámica estampada en un molde para decorarla.",
                ETreasureRarity.COMMON, ETreasureType.CERAMIC, 12, 1));
        treasures.add(new Treasure(CODIGO + "_CER_003", "Ritón",
                "Vaso con un agujero para realizar libaciones rituales.",
                ETreasureRarity.RARE, ETreasureType.CERAMIC, 10, 5));
        treasures.add(new Treasure(CODIGO + "_CER_004", "Lucerna",
                "Pequeña lámpara de aceite confeccionada en masa durante el Imperio.",
                ETreasureRarity.COMMON, ETreasureType.CERAMIC, 5, 1));
        treasures.add(new Treasure(CODIGO + "_CER_005", "Ánfora vinaria",
                "Ánfora romana para el transporte del vino.",
                ETreasureRarity.COMMON, ETreasureType.CERAMIC, 22, 1));
        treasures.add(new Treasure(CODIGO + "_EQP_001", "Gladius",
                "Espada emblemática de las legiones romanas basada en la utilizada por los celtíberos.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 18, 3));
        treasures.add(new Treasure(CODIGO + "_EQP_002", "Pilum",
                "Lanza de madera y hierro que formaba parte del equipo básico del legionario.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 11, 3));
        treasures.add(new Treasure(CODIGO + "_EQP_003", "Lorica Hamata",
                "Cota de malla usada por las legiones desde la República hasta el Imperio.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 37, 4));
        treasures.add(new Treasure(CODIGO + "_EQP_004", "Lorica Squamata",
                "Armadura de escamas utilizada durante la República por las legiones.",
                ETreasureRarity.RARE, ETreasureType.EQUIP, 21, 6));
        treasures.add(new Treasure(CODIGO + "_EQP_005", "Lorica Segmentata",
                "Armadura de placas de gran protección utilizada por las legiones durante el Imperio. Hecha de hierro y acero.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 34, 3));
        treasures.add(new Treasure(CODIGO + "_EQP_006", "Scutum",
                "Escudo rectangular de grandes dimensiones característico de las legiones.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 54, 3));
        treasures.add(new Treasure(CODIGO + "_EQP_007", "Gálea",
                "Casco utilizado por los legionarios durante el Imperio.",
                ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 13, 4));
        treasures.add(new Treasure(CODIGO + "_TES_001", "Mosaico geométrico",
                "Mosaico de estilo geométrico con diversos patrones y formas. Inspirado en los de Carmona.",
                ETreasureRarity.COMMON, ETreasureType.TILE, 625, 1));
    }

    @Override
    public String getCodigo() {
        return CODIGO;
    }
}