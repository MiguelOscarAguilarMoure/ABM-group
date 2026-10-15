package regions.civilizaciones;

import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

public class Greece extends Region {
    public static final String CODIGO = "GRC";

    public Greece() {
        super("Grecia");
         this.treasures.add(new Treasure("Calco griego", CODIGO, ETreasureType.COIN, "001", 1, 1, ETreasureRarity.COMMON, "Moneda griega de cobre que tenía el menor valor."));
      this.treasures.add(new Treasure("Óbolo griego", CODIGO, ETreasureType.COIN, "002", 1, 1, ETreasureRarity.COMMON, "Moneda griega de plata de poco valor."));
      this.treasures.add(new Treasure("Dracma griego", CODIGO, ETreasureType.COIN, "003", 1, 1, ETreasureRarity.COMMON, "Moneda griega de plata ampliamente usada."));
      this.treasures.add(new Treasure("Estatero", CODIGO, ETreasureType.COIN, "004", 1, 2, ETreasureRarity.UNCOMMON, "Moneda griega de gran valor hecha de electro."));
      this.treasures.add(new Treasure("Cuenta de vidrio", CODIGO, ETreasureType.BEAD, "001", 1, 1, ETreasureRarity.COMMON, "Pequeños trozos de vidrio pulido que se utilizaban en collares y pulseras."));
      this.treasures.add(new Treasure("Cuenta de cerámica", CODIGO, ETreasureType.BEAD, "002", 1, 1, ETreasureRarity.COMMON, "Pequeños trozos de cerámica pintada a mano que se utilizaban en collares y pulseras."));
      this.treasures.add(new Treasure("Fíbula omega", CODIGO, ETreasureType.JEWELRY, "001", 3, 2, ETreasureRarity.UNCOMMON, "Hebilla para sujetar la ropa similar a un imperdible."));
      this.treasures.add(new Treasure("Anillos", CODIGO, ETreasureType.JEWELRY, "002", 4, 5, ETreasureRarity.RARE, "Anillos de oro, plata y bronce adornados con grabados e incrustaciones de gemas."));
      this.treasures.add(new Treasure("Brazal", CODIGO, ETreasureType.JEWELRY, "003", 7, 3, ETreasureRarity.UNCOMMON, "Brazal de oro decorado con filigranas y gemas."));
      this.treasures.add(new Treasure("Ánfora de Nola", CODIGO, ETreasureType.CERAMIC, "001", 34, 2, ETreasureRarity.UNCOMMON, "Ánfora típica de la región de Nola con un cuello más largo y estrecho."));
      this.treasures.add(new Treasure("Ánfora nicosténica", CODIGO, ETreasureType.CERAMIC, "002", 18, 3, ETreasureRarity.UNCOMMON, "Ánfora creada en el siglo VI a.C. por Nicóstenes que imita la forma etrusca."));
      this.treasures.add(new Treasure("Ánfora panatenaica", CODIGO, ETreasureType.CERAMIC, "003", 30, 5, ETreasureRarity.RARE, "Ánfora que contenía el aceite que se le daba como premio a los ganadores de los Juegos panatenaicos."));
      this.treasures.add(new Treasure("Calpis", CODIGO, ETreasureType.CERAMIC, "004", 22, 1, ETreasureRarity.COMMON, "Vaso griego utilizado para almacenar agua."));
      this.treasures.add(new Treasure("Crátera", CODIGO, ETreasureType.CERAMIC, "005", 20, 1, ETreasureRarity.COMMON, "Vasija de gran capacidad usada para guardar la mezcla de agua y vino."));
      this.treasures.add(new Treasure("Enócoe", CODIGO, ETreasureType.CERAMIC, "006", 16, 1, ETreasureRarity.COMMON, "Vasija con un asa usada para servir el vino."));
      this.treasures.add(new Treasure("Estamno", CODIGO, ETreasureType.CERAMIC, "007", 20, 5, ETreasureRarity.RARE, "Vasija para conservar el vino con forma de globo y asas horizontales."));
      this.treasures.add(new Treasure("Hidria", CODIGO, ETreasureType.CERAMIC, "008", 16, 1, ETreasureRarity.COMMON, "Vasija para servir y guardar agua con tres asas, dos a los lados y una a modo de jarra."));
      this.treasures.add(new Treasure("Aspis", CODIGO, ETreasureType.EQUIP, "001", 28, 3, ETreasureRarity.UNCOMMON, "Escudo circular de bronce, madera y cuero utilizado por los hoplitas griegos."));
      this.treasures.add(new Treasure("Casco corintio", CODIGO, ETreasureType.EQUIP, "002", 30, 4, ETreasureRarity.UNCOMMON, "Casco de bronce que cubre toda la cabeza y el cuello."));
      this.treasures.add(new Treasure("Casco frigio", CODIGO, ETreasureType.EQUIP, "003", 21, 6, ETreasureRarity.RARE, "Casco de bronce con una protuberancia en la parte superior y dos placas laterales para la cara."));
      this.treasures.add(new Treasure("Xifos", CODIGO, ETreasureType.EQUIP, "004", 25, 4, ETreasureRarity.UNCOMMON, "Espada de bronce utilizada por los hoplitas griegos."));
      this.treasures.add(new Treasure("Mosaico de Poseidón", CODIGO, ETreasureType.TILE, "001", 625, 1, ETreasureRarity.COMMON, "Mosaico inspirado en el de la Casa de los Delfines de Delos."));
      this.treasures.add(new Treasure("Mosaico geométrico", CODIGO, ETreasureType.TILE, "002", 625, 1, ETreasureRarity.COMMON, "Mosaico geométrico en dos colores, típico de los primeros siglos de Grecia."));
    }

    @Override
    public String getCodigo() {
        return CODIGO;
    }

}