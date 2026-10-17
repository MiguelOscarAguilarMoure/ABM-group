package regiones.civilizaciones;

import regiones.Region;
import tesoros.ETreasureRarity;
import tesoros.ETreasureType;
import tesoros.Treasure;

public class Iberia extends Region {
   public static final String CODIGO = "IBR";
   public Iberia(){
    super(CODIGO, "Iberia");
    this.treasures.add(new Treasure("Denario ibérico", CODIGO, ETreasureType.COIN, "001", 1, 1, ETreasureRarity.COMMON, "Moneda de plata con influencia griega y fenicia"));
      this.treasures.add(new Treasure("Cuentas de hueso", CODIGO, ETreasureType.BEAD, "001", 1, 1, ETreasureRarity.COMMON, "Hueso pulido con diversas formas y tamaños para formar parte de un colgante."));
      this.treasures.add(new Treasure("Fíbula La Tène", CODIGO, ETreasureType.JEWELRY, "001", 3, 2, ETreasureRarity.UNCOMMON, "Hebilla para sujetar la ropa con forma de arco con un lado vuelto sobre sí mismo."));
      this.treasures.add(new Treasure("Fíbula anular", CODIGO, ETreasureType.JEWELRY, "002", 3, 2, ETreasureRarity.UNCOMMON, "Hebilla para sujetar la ropa con forma de anillo."));
      this.treasures.add(new Treasure("Fíbula de caballito", CODIGO, ETreasureType.JEWELRY, "003", 6, 6, ETreasureRarity.RARE, "Hebilla con forma de caballo que presumiblemente llevaban los guerreros."));
      this.treasures.add(new Treasure("Brazaletes", CODIGO, ETreasureType.JEWELRY, "004", 5, 2, ETreasureRarity.UNCOMMON, "Brazaletes de plata decorados con animales."));
      this.treasures.add(new Treasure("Torques Ártabro", CODIGO, ETreasureType.JEWELRY, "005", 5, 3, ETreasureRarity.UNCOMMON, "Torques de oro y plata con remates piriformes típico de Gallaecia."));
      this.treasures.add(new Treasure("Torques de carrete", CODIGO, ETreasureType.JEWELRY, "006", 5, 5, ETreasureRarity.RARE, "Torques de oro con remates en forma de carrete típico de Gallaecia."));
      this.treasures.add(new Treasure("Torques castrense", CODIGO, ETreasureType.JEWELRY, "007", 5, 6, ETreasureRarity.RARE, "Torques de oro con filigrana decorativa y terminado en formas trapezoidales. Típico de los castros de Gallaecia."));
      this.treasures.add(new Treasure("Tésera de hospitalidad", CODIGO, ETreasureType.JEWELRY, "008", 6, 6, ETreasureRarity.RARE, "Pieza metálica con forma de janalí que simbolizaba un pacto de hospitalidad entre grupos."));
      this.treasures.add(new Treasure("Kálathos Íbero", CODIGO, ETreasureType.CERAMIC, "001", 25, 1, ETreasureRarity.COMMON, "Vasija recta con forma de sombrero de copa que se usaba como recipiente para el transporte de mercancía."));
      this.treasures.add(new Treasure("Urna de orejetas", CODIGO, ETreasureType.CERAMIC, "002", 21, 1, ETreasureRarity.COMMON, "Urna emblemática de la cerámica ibérica utilizada como urna funeraria gracias a su cierre hermético."));
      this.treasures.add(new Treasure("Vaso bitrocónico", CODIGO, ETreasureType.CERAMIC, "003", 14, 2, ETreasureRarity.UNCOMMON, "Urna funearia encontrada en los túmulos de la Edad de Bronce."));
      this.treasures.add(new Treasure("Olla castreña", CODIGO, ETreasureType.CERAMIC, "004", 20, 3, ETreasureRarity.UNCOMMON, "Olla amplia adornada con relieves geométricos típica de los castros del noroeste de la península."));
      this.treasures.add(new Treasure("Ánfora ibérica", CODIGO, ETreasureType.CERAMIC, "005", 24, 1, ETreasureRarity.COMMON, "Ánfora ibérica con influencia fenicia usada para el transporte de líquidos."));
      this.treasures.add(new Treasure("Vaso campaniforme", CODIGO, ETreasureType.CERAMIC, "006", 20, 2, ETreasureRarity.UNCOMMON, "Cerámica arcaica con forma de campana y decorada con diversas líneas talladas."));
      this.treasures.add(new Treasure("Falcata", CODIGO, ETreasureType.EQUIP, "001", 23, 4, ETreasureRarity.UNCOMMON, "La espada íbera por escelencia. Filo de hierro con una característica curvatura y mango con forma de animal."));
      this.treasures.add(new Treasure("Espada de antenas", CODIGO, ETreasureType.EQUIP, "002", 25, 3, ETreasureRarity.UNCOMMON, "Espada celtíbera de la Edad de Bronce con un característico mango terminado en dos espirales o antenas."));
      this.treasures.add(new Treasure("Soliferrum", CODIGO, ETreasureType.EQUIP, "003", 10, 6, ETreasureRarity.RARE, "Lanza íbera hecha de hierro en su totalidad para una alta penetración de las protecciones del enemigo."));
      this.treasures.add(new Treasure("Cardiophylax", CODIGO, ETreasureType.EQUIP, "004", 8, 6, ETreasureRarity.RARE, "Armadura primitiva hecha de una plancha de bronze que cubre el corazón y unas correas de cuero que la sujetan."));
      this.treasures.add(new Treasure("Estela funeraria", CODIGO, ETreasureType.STELE, "001", 48, 5, ETreasureRarity.UNCOMMON, "Estela de piedra tallada con inscripciones que se ponía en los enterramientos nobles."));
      this.treasures.add(new Treasure("Ídolo cilíndrico oculado", CODIGO, ETreasureType.FRAGMENT, "001", 4, 3, ETreasureRarity.UNCOMMON, "Estatuilla con grandes ojos tallada en alabastro que representa a una deidad."));
      this.treasures.add(new Treasure("Ídolo oculado simple", CODIGO, ETreasureType.FRAGMENT, "002", 3, 3, ETreasureRarity.UNCOMMON, "Estatuilla oculada de piedra tallada con simples rasgos representando una deidad."));
      this.treasures.add(new Treasure("Ídolo de Garrovillas", CODIGO, ETreasureType.FRAGMENT, "003", 4, 6, ETreasureRarity.RARE, "Placa antropomórfica y oculada tallada en piedra que representa a una deidad antigua."));
      this.treasures.add(new Treasure("Ídolo de placa", CODIGO, ETreasureType.FRAGMENT, "004", 3, 2, ETreasureRarity.UNCOMMON, "Placa de pizarra, trapezoidal, oculada y tallada con motivos geométricos."));
      this.treasures.add(new Treasure("Mosaico punteado", CODIGO, ETreasureType.TILE, "001", 625, 1, ETreasureRarity.COMMON, "Mosaico punteado en dos colores creando formas geométricas. Inspirado en el de la Casa de Likine."));
   }
}
