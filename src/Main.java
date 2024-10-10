import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String apiKey = "ba196c4e017a10ca63edc19c";

            ServicioApi servicioApi = new ServicioApi(apiKey);
            Map<String, Double> tasas = servicioApi.obtenerTasas();

            if (tasas == null || tasas.isEmpty()) {
                throw new IllegalStateException("No se pudieron obtener las tasas de cambio.");
            }

            ConvertidorMoneda convertidor = new ConvertidorMoneda(tasas);
            Map<String, String> nombresMonedas = NombresMonedas.obtenerNombresMonedas();

            Scanner scanner = new Scanner(System.in);
            boolean salir = false;

            while (!salir) {
                System.out.println("1. Convertir Moneda");
                System.out.println("2. Salir");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        String monedaOrigen;
                        String monedaDestino;

                        try {
                            System.out.println("Ingrese moneda origen (ej: USD): ");
                            monedaOrigen = scanner.next().toUpperCase();
                            validarMoneda(tasas, monedaOrigen);

                            System.out.println("Ingrese moneda destino (ej: EUR): ");
                            monedaDestino = scanner.next().toUpperCase();
                            validarMoneda(tasas, monedaDestino);

                            System.out.println("Ingrese la cantidad: ");
                            double cantidad = scanner.nextDouble();
                            double resultado = convertidor.convertir(monedaOrigen, monedaDestino, cantidad);

                            String nombreOrigen = nombresMonedas.getOrDefault(monedaOrigen, monedaOrigen);
                            String nombreDestino = nombresMonedas.getOrDefault(monedaDestino, monedaDestino);

                            LocalDateTime fechaHoraActual = LocalDateTime.now();
                            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            String fechaFormateada = fechaHoraActual.format(formatoFecha);

                            System.out.println("En este preciso momento " + fechaFormateada);
                            System.out.println(cantidad + " " + nombreOrigen + " (" + monedaOrigen + ") equivalen a " + resultado + " " + nombreDestino + " (" + monedaDestino + ")");


                            guardarConversionEnArchivo(monedaOrigen, monedaDestino, cantidad, resultado, fechaFormateada);

                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

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


    private static void validarMoneda(Map<String, Double> tasas, String moneda) {
        if (!tasas.containsKey(moneda)) {
            throw new IllegalArgumentException( moneda + " no es una moneda válida.");
        }
    }


    private static void guardarConversionEnArchivo(String monedaOrigen, String monedaDestino, double cantidad, double resultado, String fecha) {
        // Crear un objeto de conversión
        Conversion conversion = new Conversion(monedaOrigen, monedaDestino, cantidad, resultado, fecha);

        // Serializar el objeto a JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(conversion);

        // Escribir el JSON en un archivo
        try (FileWriter writer = new FileWriter("conversiones.json", true)) {
            writer.write(json + "\n");
            System.out.println("La conversión se ha guardado en el archivo conversiones.json.");
        } catch (IOException e) {
            System.out.println("Error al guardar la conversión en el archivo: " + e.getMessage());
        }
    }

}
