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
        super("Roma",CODIGO);
        this.treasures.add(new Treasure("As", CODIGO, ETreasureType.COIN, "001", 1, 1,
                ETreasureRarity.COMMON, "La moneda de la Antigua Roma de menor valor. Hecha de cobre."));
        this.treasures.add(new Treasure("Dupondio", CODIGO, ETreasureType.COIN, "002", 1, 1,
                ETreasureRarity.COMMON, "Moneda de bronce de poco valor."));
        this.treasures.add(new Treasure("Sestercio", CODIGO, ETreasureType.COIN, "003", 1, 1,
                ETreasureRarity.COMMON, "Moneda de bronce que se utilizaba como medida estándar para medir valores."));
        this.treasures.add(new Treasure("Denario", CODIGO, ETreasureType.COIN, "004", 1, 1,
                ETreasureRarity.COMMON, "Moneda de plata de gran valor que equivale a diez ases."));
        this.treasures.add(new Treasure("Áureo", CODIGO, ETreasureType.COIN, "005", 1, 2,
                ETreasureRarity.UNCOMMON, "Moneda de oro de gran valor equivalente a veinticinco denarios."));
        this.treasures.add(new Treasure("Cuenta de nácar", CODIGO, ETreasureType.BEAD, "001", 1, 1,
                ETreasureRarity.COMMON, "Perlas y conchas perforadas utilizadas para confeccionar collares y pulseras."));
        this.treasures.add(new Treasure("Cuenta de gema", CODIGO, ETreasureType.BEAD, "002", 1, 1,
                ETreasureRarity.COMMON, "Piedras semipreciosas talladas para confeccionar adornos, pulseras y collares."));
        this.treasures.add(new Treasure("Cuenta de metal precioso", CODIGO, ETreasureType.BEAD, "003", 1, 1,
                ETreasureRarity.COMMON, "Pequeñas piezas de oro, plata o bronce para confeccionar collares y pulseras."));
        this.treasures.add(new Treasure("Pulseras de esferas", CODIGO, ETreasureType.JEWELRY, "001", 4, 2,
                ETreasureRarity.UNCOMMON, "Juego de pulseras de oro formadas por pares de semiesferas en fila."));
        this.treasures.add(new Treasure("Brazalete de serpiente", CODIGO, ETreasureType.JEWELRY, "002", 5, 3,
                ETreasureRarity.UNCOMMON, "Brazalete con forma de serpiente enroscada."));
        this.treasures.add(new Treasure("Ceñidor", CODIGO, ETreasureType.JEWELRY, "003", 6, 5,
                ETreasureRarity.RARE, "Cadena decorada que se ponía sobre la ropa para ajustarla al pecho."));
        this.treasures.add(new Treasure("Fíbula de ballesta", CODIGO, ETreasureType.JEWELRY, "004", 4, 3,
                ETreasureRarity.UNCOMMON, "Hebilla para sujetar la ropa con forma de ballesta."));
        this.treasures.add(new Treasure("Lúnula", CODIGO, ETreasureType.JEWELRY, "005", 5, 2,
                ETreasureRarity.UNCOMMON, "Colgante con forma de luna que llevaban las mujeres hasta el día de su boda."));
        this.treasures.add(new Treasure("Jarra en terra sigillata", CODIGO, ETreasureType.CERAMIC, "001", 20, 1,
                ETreasureRarity.COMMON, "Jarra creada mediante terra sigillata, cerámica estampada en un molde para decorarla."));
        this.treasures.add(new Treasure("Vaso en terra sigilliata", CODIGO, ETreasureType.CERAMIC, "002", 12, 1,
                ETreasureRarity.COMMON, "Vaso creado mediante terra sigillata, cerámica estampada en un molde para decorarla."));
        this.treasures.add(new Treasure("Ritón", CODIGO, ETreasureType.CERAMIC, "003", 10, 5,
                ETreasureRarity.RARE, "Vaso con un agujero para realizar libaciones rituales."));
        this.treasures.add(new Treasure("Lucerna", CODIGO, ETreasureType.CERAMIC, "004", 5, 1,
                ETreasureRarity.COMMON, "Pequeña lámpara de aceite confeccionada en masa durante el Imperio."));
        this.treasures.add(new Treasure("Ánfora vinaria", CODIGO, ETreasureType.CERAMIC, "005", 22, 1,
                ETreasureRarity.COMMON, "Ánfora romana para el transporte del vino."));
        this.treasures.add(new Treasure("Gladius", CODIGO, ETreasureType.EQUIP, "001", 18, 3,
                ETreasureRarity.UNCOMMON, "Espada emblemática de las legiones romanas basada en la utilizada por los celtíberos."));
        this.treasures.add(new Treasure("Pilum", CODIGO, ETreasureType.EQUIP, "002", 11, 3,
                ETreasureRarity.UNCOMMON, "Lanza de madera y hierro que formaba parte del equipo básico del legionario."));
        this.treasures.add(new Treasure("Lorica Hamata", CODIGO, ETreasureType.EQUIP, "003", 37, 4,
                ETreasureRarity.UNCOMMON, "Cota de malla usada por las legiones desde la República hasta el Imperio."));
        this.treasures.add(new Treasure("Lorica Squamata", CODIGO, ETreasureType.EQUIP, "004", 21, 6,
                ETreasureRarity.RARE, "Armadura de escamas utilizada durante la República por las legiones."));
        this.treasures.add(new Treasure("Lorica Segmentata", CODIGO, ETreasureType.EQUIP, "005", 34, 3,
                ETreasureRarity.UNCOMMON, "Armadura de placas de gran protección utilizada por las legiones durante el Imperio. Hecha de hierro y acero."));
        this.treasures.add(new Treasure("Scutum", CODIGO, ETreasureType.EQUIP, "006", 54, 3,
                ETreasureRarity.UNCOMMON, "Escudo rectangular de grandes dimensiones característico de las legiones."));
        this.treasures.add(new Treasure("Gálea", CODIGO, ETreasureType.EQUIP, "007", 13, 4,
                ETreasureRarity.UNCOMMON, "Casco utilizado por los legionarios durante el Imperio."));
        this.treasures.add(new Treasure("Mosaico geométrico", CODIGO, ETreasureType.TILE, "001", 625, 1,
                ETreasureRarity.COMMON, "Mosaico de estilo geométrico con diversos patrones y formas. Inspirado en los de Carmona."));
    }

    @Override
    public String getCodigo() {
        return CODIGO;
    }
}