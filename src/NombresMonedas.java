import java.util.HashMap;
import java.util.Map;

public class NombresMonedas {
    public static Map<String, String> obtenerNombresMonedas() {
        Map<String, String> nombresMonedas = new HashMap<>();

        nombresMonedas.put("COP", "Pesos colombianos");
        nombresMonedas.put("KWD", "Dinar kuwaití");
        nombresMonedas.put("XAF", "Franco CFA");
        nombresMonedas.put("SDG", "Lira de Sudán");
        nombresMonedas.put("SAR", "Riyal saudí");
        nombresMonedas.put("USD", "Dólar estadounidense");
        nombresMonedas.put("EUR", "Euro");
        nombresMonedas.put("GBP", "Libra esterlina");
        nombresMonedas.put("JPY", "Yen japonés");
        nombresMonedas.put("AUD", "Dólar australiano");
        nombresMonedas.put("CAD", "Dólar canadiense");
        nombresMonedas.put("MXN", "Peso mexicano");


        return nombresMonedas;
    }
}
