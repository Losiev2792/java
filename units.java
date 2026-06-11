import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testMetersToKm() {
        double result = App.metersToKm(1500.0);
        assertEquals(1.5, result, 0.001);
    }

    @Test
    public void testKgToGrams() {
        double result = App.kgToGrams(2.5);
        assertEquals(2500.0, result, 0.001);
    }

    @Test
    public void testCelsiusToFahrenheit() {
        double zeroCelsius = App.celsiusToFahrenheit(0.0);
        double boilingCelsius = App.celsiusToFahrenheit(100.0);

        assertEquals(32.0, zeroCelsius, 0.001);
        assertEquals(212.0, boilingCelsius, 0.001);
    }
}