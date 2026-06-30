import calculadora.CalculadoraLogica;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Prueba de integración: verifica el flujo completo
 * de la calculadora simulando la interacción del usuario
 * a través de la lógica de negocio.
 *
 * Circuito operativo: ingreso de dígitos → operación
 * → cálculo → historial → nueva operación sobre resultado.
 */
class CalculadoraIntegrationTest {

    private CalculadoraLogica calc;

    @BeforeEach
    void setUp() {
        calc = new CalculadoraLogica();
    }

    @Test
    void testCircuitoSumaCompleto() {
        // 1) Ingresar primer operando
        calc.ingresarDigito("4");
        calc.ingresarDigito("5");
        assertEquals("45", calc.getEntradaActual());

        // 2) Establecer operador
        calc.establecerOperador("+");
        assertTrue(calc.getHistorial().contains("45"));
        assertTrue(calc.getHistorial().contains("+"));

        // 3) Ingresar segundo operando
        calc.ingresarDigito("3");
        calc.ingresarDigito("0");
        assertEquals("30", calc.getEntradaActual());

        // 4) Calcular
        calc.calcular();
        assertEquals("75", calc.getEntradaActual());
        assertTrue(calc.getHistorial().contains("="));
    }

    @Test
    void testCircuitoMultiplicacionConDecimal() {
        calc.ingresarDigito("2");
        calc.ingresarDigito(".");
        calc.ingresarDigito("5");
        calc.establecerOperador("*");
        calc.ingresarDigito("4");
        calc.calcular();
        assertEquals("10", calc.getEntradaActual());
    }

    @Test
    void testCircuitoDivisionConHistorial() {
        calc.ingresarDigito("100");
        calc.establecerOperador("/");
        calc.ingresarDigito("4");
        calc.calcular();
        assertEquals("25", calc.getEntradaActual());
        assertTrue(calc.getHistorial().contains("100"));
        assertTrue(calc.getHistorial().contains("4"));
    }

    @Test
    void testFlujoErrorRecuperacion() {
        // Causar error: dividir por cero
        calc.ingresarDigito("8");
        calc.establecerOperador("/");
        calc.ingresarDigito("0");
        calc.calcular();
        assertTrue(calc.getEntradaActual().contains("No se puede dividir"));

        // Recuperarse con CE
        calc.limpiarEntrada();
        assertEquals("0", calc.getEntradaActual());

        // Continuar operando normalmente
        calc.ingresarDigito("8");
        calc.establecerOperador("/");
        calc.ingresarDigito("2");
        calc.calcular();
        assertEquals("4", calc.getEntradaActual());
    }

    @Test
    void testCircuitoOperacionesEncadenadas() {
        // 5 + 3 = 8, luego 8 * 2 = 16
        calc.ingresarDigito("5");
        calc.establecerOperador("+");
        calc.ingresarDigito("3");
        calc.calcular();
        assertEquals("5", calc.getHistorial().substring(0, 1));

        calc.establecerOperador("*");
        calc.ingresarDigito("2");
        calc.calcular();
        assertEquals("16", calc.getEntradaActual());
    }
}
