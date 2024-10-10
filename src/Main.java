import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
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
                System.out.println("2. Mostrar historial de busqueda");
                System.out.println("3. Salir....");

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

                            // Crear un objeto Conversion y guardar en el archivo
                            Conversion conversion = new Conversion(monedaOrigen, monedaDestino, cantidad, resultado, fechaFormateada);
                            guardarConversionEnArchivo(conversion);

                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    case 2:
                        mostrarHistorialConversiones();
                        break;

                    case 3:
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


    private static void guardarConversionEnArchivo(Conversion conversion) {
        Gson gson = new Gson();  // Usamos Gson sin pretty printing
        File file = new File("conversiones.json");

        try (FileWriter writer = new FileWriter(file, true)) {

            String json = gson.toJson(conversion);
            writer.write(json + "\n");
            System.out.println("|-------------------------------------------------------------|");
            System.out.println("|La conversión se ha guardado en el archivo conversiones.json.|");
            System.out.println("'-------------------------------------------------------------'");
        } catch (IOException e) {
            System.out.println("Error al guardar la conversión: " + e.getMessage());
        }
    }


    private static void mostrarHistorialConversiones() {
        File file = new File("conversiones.json");


        if (!file.exists()) {
            System.out.println("No hay historial disponible.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Historial de conversiones:");
            while (scanner.hasNextLine()) {
                String jsonLinea = scanner.nextLine().trim();

                if (jsonLinea.isEmpty()) {
                    continue;  // Si la línea está vacía, la salta
                }

                try {
                    Gson gson = new Gson();
                    Conversion conversion = gson.fromJson(jsonLinea, Conversion.class);

                    System.out.println(conversion.getCantidad() + " " + conversion.getMonedaOrigen() + " (" + conversion.getMonedaOrigen() + ") equivalen a " +
                            conversion.getResultado() + " " + conversion.getMonedaDestino() + " (" + conversion.getMonedaDestino() + ") - Fecha: " + conversion.getFecha());

                } catch (com.google.gson.JsonSyntaxException e) {
                    System.out.println("Línea ignorada (no es un JSON válido): " + jsonLinea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
        }
    }


}
