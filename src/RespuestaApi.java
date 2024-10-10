import java.util.Map;

public class RespuestaApi {
    private String result;
    private String base_code;
    private Map<String, Double> conversion_rates;

    // Getters
    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return base_code;
    }

    public Map<String, Double> getTasas() {
        return conversion_rates;
    }
}
