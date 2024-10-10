import java.util.Map;

public class ConvertidorMoneda {
    private Map<String, Double> tasas;


    public ConvertidorMoneda(Map<String, Double> tasas) {
        this.tasas = tasas;
    }

    public double convertir(String monedaOrigen, String monedaDestino, double cantidad) {

        if (tasas == null || tasas.isEmpty()) {
            throw new IllegalStateException("Las tasas de cambio no están disponibles.");
        }

        if (!tasas.containsKey(monedaOrigen)) {
            throw new IllegalArgumentException("La moneda origen no es válida.");
        }

        if (!tasas.containsKey(monedaDestino)) {
            throw new IllegalArgumentException("La moneda destino no es válida.");
        }

        double tasaOrigen = tasas.get(monedaOrigen);
        double tasaDestino = tasas.get(monedaDestino);

        return (cantidad / tasaOrigen) * tasaDestino;
    }
}