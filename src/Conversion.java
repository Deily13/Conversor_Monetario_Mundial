public class Conversion {
    private String monedaOrigen;
    private String monedaDestino;
    private double cantidad;
    private double resultado;
    private String fecha;

    // Constructor
    public Conversion(String monedaOrigen, String monedaDestino, double cantidad, double resultado, String fecha) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.resultado = resultado;
        this.fecha = fecha;
    }

}
