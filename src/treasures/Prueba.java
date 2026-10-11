package treasures;

public class Prueba {

    public static void main(String[] args) {

        
        Treasure vasija = new Treasure("GRC_CER_001", "Vasija prueba", "Prueba de vasija",
                ETreasureRarity.COMMON, ETreasureType.CERAMIC, 10, 1);

        
        System.out.println("Nombre: " + vasija.getNombre());
        System.out.println("Descripción: " + vasija.getDescripcion());
        System.out.println("Código: " + vasija.getCodigo());
        System.out.println("Rareza: " + vasija.getRarezaTesoro());
        System.out.println("Tipo: " + vasija.getTipoTesoro());
        System.out.println("Partes: " + vasija.getPartes());
        System.out.println("Puntos: " + vasija.getPuntos());

       
        System.out.println("\nCódigos con parte:");
        for (int i = 0; i < 10; i++) {
            System.out.println(vasija.getCodigoConParte());
        }

        
        Treasure moneda = new Treasure("GRC_MON_001", "Calco griego", "Moneda de cobre",
                ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1);
        System.out.println("\nMoneda:");
        for (int i = 0; i < 3; i++) {
            System.out.println(moneda.getCodigoConParte());
        }
    }
}