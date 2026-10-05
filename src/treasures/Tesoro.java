package treasures;

public class Tesoro {
    private String codigo;
    private  String nombre;
    private  String descripcion;
    private  ETreasureRarity rarezaTesoro;
    private  ETreasureType tipoTesoro;
    public Tesoro(String codigo, String nombre, String descripcion, ETreasureRarity rarezaTesoro,
            ETreasureType tipoTesoro) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rarezaTesoro = rarezaTesoro;
        this.tipoTesoro = tipoTesoro;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ETreasureRarity getRarezaTesoro() {
        return rarezaTesoro;
    }

    public void setRarezaTesoro(ETreasureRarity rarezaTesoro) {
        this.rarezaTesoro = rarezaTesoro;
    }

    public ETreasureType getTipoTesoro() {
        return tipoTesoro;
    }

    public void setTipoTesoro(ETreasureType tipoTesoro) {
        this.tipoTesoro = tipoTesoro;
    }
    
}
