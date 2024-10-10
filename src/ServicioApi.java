import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.Map;

public class ServicioApi {
    private static final String URL_API = "https://v6.exchangerate-api.com/v6/";
    private String apiKey;

    public ServicioApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Map<String, Double> obtenerTasas() throws Exception {
        String urlConApiKey = URL_API + apiKey + "/latest/USD";

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlConApiKey))
                .build();

        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {

            Gson gson = new Gson();
            RespuestaApi datos = gson.fromJson(respuesta.body(), RespuestaApi.class);
            return datos.getTasas();
        } else {
            System.out.println("Error al conectar con la API: " + respuesta.body());
            throw new RuntimeException("Error en la API: " + respuesta.statusCode());
        }
    }
}