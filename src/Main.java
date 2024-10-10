import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            Map<String, String> nombresMonedas = NombresMonedas.obtenerNombresMonedas();  // Obtener nombres de las monedas

            Scanner scanner = new Scanner(System.in);
            boolean salir = false;

            while (!salir) {
                // Mostrar el menú de opciones
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

                        // Realizar la conversión
                        double resultado = convertidor.convertir(monedaOrigen, monedaDestino, cantidad);

                        // Obtener los nombres completos de las monedas
                        String nombreOrigen = nombresMonedas.getOrDefault(monedaOrigen, monedaOrigen);
                        String nombreDestino = nombresMonedas.getOrDefault(monedaDestino, monedaDestino);

                        // Obtener la fecha y hora actuales
                        LocalDateTime fechaHoraActual = LocalDateTime.now();
                        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        String fechaFormateada = fechaHoraActual.format(formatoFecha);

                        // Imprimir la fecha de la conversión
                        System.out.println("En este preciso momento: " + fechaFormateada);

                        // Imprimir el resultado con los nombres completos
                        System.out.println(cantidad + " " + nombreOrigen + " (" + monedaOrigen + ") equivalen a " + resultado + " " + nombreDestino + " (" + monedaDestino + ")");
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
