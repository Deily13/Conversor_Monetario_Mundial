public class Conversion {
    private String monedaOrigen;
    private String monedaDestino;
    private double cantidad;
    private double resultado;
    private String fecha;


    public Conversion(String monedaOrigen, String monedaDestino, double cantidad, double resultado, String fecha) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getResultado() {
        return resultado;
    }

    public String getFecha() {
        return fecha;
    }
}
