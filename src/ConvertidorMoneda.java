import java.util.Map;

public class ConvertidorMoneda {
    private Map<String, Double> tasas;

    // Constructor que recibe las tasas de cambio
    public ConvertidorMoneda(Map<String, Double> tasas) {
        this.tasas = tasas;
    }

    public double convertir(String monedaOrigen, String monedaDestino, double cantidad) {
        // Verificar si las tasas fueron correctamente cargadas
        if (tasas == null || tasas.isEmpty()) {
            throw new IllegalStateException("Las tasas de cambio no están disponibles.");
        }

        // Verificar si las monedas están presentes en el mapa de tasas
        if (!tasas.containsKey(monedaOrigen)) {
            throw new IllegalArgumentException("La moneda origen no es válida.");
        }

        if (!tasas.containsKey(monedaDestino)) {
            throw new IllegalArgumentException("La moneda destino no es válida.");
        }

        // Obtener las tasas de cambio
        double tasaOrigen = tasas.get(monedaOrigen);
        double tasaDestino = tasas.get(monedaDestino);

        // Realizar la conversión
        return (cantidad / tasaOrigen) * tasaDestino;
    }
}
