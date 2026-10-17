package treasures;

import java.util.Random;

public class Treasure {
    private static final Random random = new Random();
    // los hacemos final porque solo se pueden asignar una vez

    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final ETreasureRarity rarezaTesoro;
    private final ETreasureType tipoTesoro;
    private final int puntos;
    private final int partes;

    /**
     * Constructor que usan las regiones: monta el código con región + tipo + número.
     
     */
    public Treasure(String nombre, String codigoRegion, ETreasureType tipoTesoro, String numero,
            int partes, int puntos, ETreasureRarity rarezaTesoro, String descripcion) {
        this(codigoRegion + "_" + tipoTesoro.getCode() + "_" + numero,
                nombre, descripcion, rarezaTesoro, tipoTesoro, partes, puntos);
    }

    /**
     * Constructor con el código ya montado.
     */
    public Treasure(String codigo, String nombre, String descripcion,
            ETreasureRarity rarezaTesoro, ETreasureType tipoTesoro,
            int partes, int puntos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rarezaTesoro = rarezaTesoro;
        this.tipoTesoro = tipoTesoro;
        this.partes = partes;
        this.puntos = puntos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ETreasureRarity getRarezaTesoro() {
        return rarezaTesoro;
    }

    public ETreasureType getTipoTesoro() {
        return tipoTesoro;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPartes() {
        return partes;
    }

    /** Devuelve el código con una parte al azar, por ejemplo "ROM_COI_001_0003". */
    public String getCodigoConParte() {
        int parte = random.nextInt(partes) + 1;
        return String.format("%s_%04d", codigo, parte);
    }
}
