import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.Map;

public class ServicioApi {
    private static final String URL_API = "https://v6.exchangerate-api.com/v6/";
    private String apiKey;

    // Constructor que recibe la API Key
    public ServicioApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Map<String, Double> obtenerTasas() throws Exception {
        // Construir la URL con la API Key
        String urlConApiKey = URL_API + apiKey + "/latest/USD";  // URL completa con la clave de API

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlConApiKey))
                .build();

        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        // Verificar el código de estado HTTP
        if (respuesta.statusCode() == 200) {
            // Imprimir el contenido de la respuesta para depuración
            System.out.println("Respuesta de la API: " + respuesta.body());

            // Procesar el JSON y obtener las tasas de cambio
            Gson gson = new Gson();
            RespuestaApi datos = gson.fromJson(respuesta.body(), RespuestaApi.class);
            return datos.getTasas();
        } else {
            // Manejar el error si no fue exitoso
            System.out.println("Error al conectar con la API: " + respuesta.body());
            throw new RuntimeException("Error en la API: " + respuesta.statusCode());
        }
    }
}
