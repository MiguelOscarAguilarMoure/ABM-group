package treasures;

public class Tesoro {
    private String codigo;
    private  String nombre;
    private  String descripcion;
    private  ETreasureRarity rarezaTesoro;
    private  ETreasureType tipoTesoro;
    private  int puntos;
    private  int partes;
    

    public Tesoro(String codigo, String nombre, String descripcion, ETreasureRarity rarezaTesoro,
            ETreasureType tipoTesoro, int puntos, int partes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rarezaTesoro = rarezaTesoro;
        this.tipoTesoro = tipoTesoro;
        this.puntos = puntos;
        this.partes = partes;
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

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartes() {
        return partes;
    }

    public void setPartes(int partes) {
        this.partes = partes;
    }
    
}
