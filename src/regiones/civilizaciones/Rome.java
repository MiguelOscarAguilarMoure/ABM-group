package regiones.civilizaciones;

import regiones.Region;
import tesoros.ETreasureRarity;
import tesoros.ETreasureType;
import tesoros.Treasure;

public class Rome extends Region {
    public static final String CODIGO = "ROM";
    public Rome() {
        super("ROM", "Roma");
         this.treasures.add(new Treasure("As", "ROM", ETreasureType.COIN, "001", 1, 1, ETreasureRarity.COMMON, "La moneda de la Antigua Roma de menor valor. Hecha de cobre."));
      this.treasures.add(new Treasure("Dupondio", "ROM", ETreasureType.COIN, "002", 1, 1, ETreasureRarity.COMMON, "Moneda de bronce de poco valor."));
      this.treasures.add(new Treasure("Sestercio", "ROM", ETreasureType.COIN, "003", 1, 1, ETreasureRarity.COMMON, "Moneda de bronce que se utilizaba como medida estándar para medir valores."));
      this.treasures.add(new Treasure("Denario", "ROM", ETreasureType.COIN, "004", 1, 1, ETreasureRarity.COMMON, "Moneda de plata de gran valor que equivale a diez ases."));
      this.treasures.add(new Treasure("Áureo", "ROM", ETreasureType.COIN, "005", 1, 2, ETreasureRarity.UNCOMMON, "Moneda de oro de gran valor equivalente a veinticinco denarios."));
      this.treasures.add(new Treasure("Cuenta de nácar", "ROM", ETreasureType.BEAD, "001", 1, 1, ETreasureRarity.COMMON, "Perlas y conchas perforadas utilizadas para confeccionar collares y pulseras."));
      this.treasures.add(new Treasure("Cuenta de gema", "ROM", ETreasureType.BEAD, "002", 1, 1, ETreasureRarity.COMMON, "Piedras semipreciosas talladas para confeccionar adornos, pulseras y collares."));
      this.treasures.add(new Treasure("Cuenta de metal precioso", "ROM", ETreasureType.BEAD, "003", 1, 1, ETreasureRarity.COMMON, "Pequeñas piezas de oro, plata o bronce para confeccionar collares y pulseras."));
      this.treasures.add(new Treasure("Pulseras de esferas", "ROM", ETreasureType.JEWELRY, "001", 4, 2, ETreasureRarity.UNCOMMON, "Juego de pulseras de oro formadas por pares de semiesferas en fila."));
      this.treasures.add(new Treasure("Brazalete de serpiente", "ROM", ETreasureType.JEWELRY, "002", 5, 3, ETreasureRarity.UNCOMMON, "Brazalete con forma de serpiente enroscada."));
      this.treasures.add(new Treasure("Ceñidor", "ROM", ETreasureType.JEWELRY, "003", 6, 5, ETreasureRarity.RARE, "Cadena decorada que se ponía sobre la ropa para ajustarla al pecho."));
      this.treasures.add(new Treasure("Fíbula de ballesta", "ROM", ETreasureType.JEWELRY, "004", 4, 3, ETreasureRarity.UNCOMMON, "Hebilla para sujetar la ropa con forma de ballesta."));
      this.treasures.add(new Treasure("Lúnula", "ROM", ETreasureType.JEWELRY, "005", 5, 2, ETreasureRarity.UNCOMMON, "Colgante con forma de luna que llevaban las mujeres hasta el día de su boda."));
      this.treasures.add(new Treasure("Jarra en terra sigillata", "ROM", ETreasureType.CERAMIC, "001", 20, 1, ETreasureRarity.COMMON, "Jarra creada mediante terra sigillata, cerámica estampada en un molde para decorarla."));
      this.treasures.add(new Treasure("Vaso en terra sigilliata", "ROM", ETreasureType.CERAMIC, "002", 12, 1, ETreasureRarity.COMMON, "Vaso creado mediante terra sigillata, cerámica estampada en un molde para decorarla."));
      this.treasures.add(new Treasure("Ritón", "ROM", ETreasureType.CERAMIC, "003", 10, 5, ETreasureRarity.RARE, "Vaso con un agujero para realizar libaciones rituales."));
      this.treasures.add(new Treasure("Lucerna", "ROM", ETreasureType.CERAMIC, "004", 5, 1, ETreasureRarity.COMMON, "Pequeña lámpara de aceite confeccionada en masa durante el Imperio."));
      this.treasures.add(new Treasure("Ánfora vinaria", "ROM", ETreasureType.CERAMIC, "005", 22, 1, ETreasureRarity.COMMON, "Ánfora romana para el transporte del vino."));
      this.treasures.add(new Treasure("Gladius", "ROM", ETreasureType.EQUIP, "001", 18, 3, ETreasureRarity.UNCOMMON, "Espada emblemática de las legiones romanas basada en la utilizada por los celtíberos."));
      this.treasures.add(new Treasure("Pilum", "ROM", ETreasureType.EQUIP, "002", 11, 3, ETreasureRarity.UNCOMMON, "Lanza de madera y hierro que formaba parte del equipo básico del legionario."));
      this.treasures.add(new Treasure("Lorica Hamata", "ROM", ETreasureType.EQUIP, "003", 37, 4, ETreasureRarity.UNCOMMON, "Cota de malla usada por las legiones desde la República hasta el Imperio."));
      this.treasures.add(new Treasure("Lorica Squamata", "ROM", ETreasureType.EQUIP, "004", 21, 6, ETreasureRarity.RARE, "Armadura de escamas utilizada durante la República por las legiones."));
      this.treasures.add(new Treasure("Lorica Segmentata", "ROM", ETreasureType.EQUIP, "005", 34, 3, ETreasureRarity.UNCOMMON, "Armadura de placas de gran protección utilizada por las legiones durante el Imperio. Hecha de hierro y acero."));
      this.treasures.add(new Treasure("Scutum", "ROM", ETreasureType.EQUIP, "006", 54, 3, ETreasureRarity.UNCOMMON, "Escudo rectangular de grandes dimensiones característico de las legiones."));
      this.treasures.add(new Treasure("Gálea", "ROM", ETreasureType.EQUIP, "007", 13, 4, ETreasureRarity.UNCOMMON, "Casco utilizado por los legionarios durante el Imperio."));
      this.treasures.add(new Treasure("Mosaico geométrico", "ROM", ETreasureType.TILE, "001", 625, 1, ETreasureRarity.COMMON, "Mosaico de estilo geométrico con diversos patrones y formas. Inspirado en los de Carmona."));
    }

}
