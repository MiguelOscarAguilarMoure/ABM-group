package treasures;

import java.util.Random;
/**
 * @author Borja Abal
 * Treasure
 */
public class Treasure {
    private static final Random random = new Random();
    // los hacemos final porque oslo se puden asignar una vez

    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final ETreasureRarity rarezaTesoro;
    private final ETreasureType tipoTesoro;
    private final int puntos;
    private final int partes;

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

    public Treasure(String nombre, String codigoRegion, ETreasureType tipoTesoro,
            String numero, int partes, int puntos, ETreasureRarity rarezaTesoro,
            String descripcion) {
        this(codigoRegion + "_" + tipoTesoro.getCode() + "_" + numero, nombre,
                descripcion, rarezaTesoro, tipoTesoro, partes, puntos);
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

    // devuelve el codigo y genera partes de forma aleatoria
    public String getCodigoConParte() {
        int parte = random.nextInt(partes) + 1;
        return String.format("%s_%04d", codigo, parte);
    }

}