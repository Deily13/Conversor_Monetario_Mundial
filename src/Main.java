import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String apiKey = "ba196c4e017a10ca63edc19c";  // Tu API Key

            ServicioApi servicioApi = new ServicioApi(apiKey);  // Crear instancia del servicio API con la clave
            Map<String, Double> tasas = servicioApi.obtenerTasas();  // Obtener las tasas

            if (tasas == null || tasas.isEmpty()) {
                throw new IllegalStateException("No se pudieron obtener las tasas de cambio.");
            }

            ConvertidorMoneda convertidor = new ConvertidorMoneda(tasas);  // Inicializar el convertidor con las tasas

            Scanner scanner = new Scanner(System.in);
            boolean salir = false;

            while (!salir) {
                System.out.println("1. Convertir Moneda");
                System.out.println("2. Salir");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese moneda origen (ej: USD): ");
                        String monedaOrigen = scanner.next().toUpperCase();
                        System.out.println("Ingrese moneda destino (ej: EUR): ");
                        String monedaDestino = scanner.next().toUpperCase();
                        System.out.println("Ingrese la cantidad: ");
                        double cantidad = scanner.nextDouble();

                        double resultado = convertidor.convertir(monedaOrigen, monedaDestino, cantidad);
                        System.out.println(cantidad + " " + monedaOrigen + " = " + resultado + " " + monedaDestino);
                        break;
                    case 2:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
