import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculadoraLogicaTest {

    private CalculadoraLogica calc;

    @BeforeEach
    void setUp() {
        calc = new CalculadoraLogica();
    }

    @Test
    void testValorInicialEsCero() {
        assertEquals("0", calc.getEntradaActual());
    }

    @Test
    void testIngresarDigito() {
        calc.ingresarDigito("5");
        assertEquals("5", calc.getEntradaActual());
    }

    @Test
    void testIngresarVariosDigitos() {
        calc.ingresarDigito("1");
        calc.ingresarDigito("2");
        calc.ingresarDigito("3");
        assertEquals("123", calc.getEntradaActual());
    }

    @Test
    void testSumarDosNumeros() {
        calc.ingresarDigito("2");
        calc.establecerOperador("+");
        calc.ingresarDigito("3");
        calc.calcular();
        assertEquals("5", calc.getEntradaActual());
    }

    @Test
    void testRestarNumeros() {
        calc.ingresarDigito("10");
        calc.establecerOperador("-");
        calc.ingresarDigito("4");
        calc.calcular();
        assertEquals("6", calc.getEntradaActual());
    }

    @Test
    void testMultiplicar() {
        calc.ingresarDigito("4");
        calc.establecerOperador("*");
        calc.ingresarDigito("5");
        calc.calcular();
        assertEquals("20", calc.getEntradaActual());
    }

    @Test
    void testDividir() {
        calc.ingresarDigito("15");
        calc.establecerOperador("/");
        calc.ingresarDigito("3");
        calc.calcular();
        assertEquals("5", calc.getEntradaActual());
    }

    @Test
    void testDividirPorCero() {
        calc.ingresarDigito("10");
        calc.establecerOperador("/");
        calc.ingresarDigito("0");
        calc.calcular();
        assertTrue(calc.getEntradaActual().contains("No se puede dividir"));
    }

    @Test
    void testModulo() {
        calc.ingresarDigito("10");
        calc.establecerOperador("%");
        calc.ingresarDigito("3");
        calc.calcular();
        assertEquals("1", calc.getEntradaActual());
    }

    @Test
    void testCambiarSigno() {
        calc.ingresarDigito("7");
        calc.cambiarSigno();
        assertEquals("-7", calc.getEntradaActual());
    }

    @Test
    void testPorcentaje() {
        calc.ingresarDigito("200");
        calc.porcentaje();
        assertEquals("2", calc.getEntradaActual());
    }

    @Test
    void testRetroceso() {
        calc.ingresarDigito("1");
        calc.ingresarDigito("2");
        calc.ingresarDigito("3");
        calc.retroceso();
        assertEquals("12", calc.getEntradaActual());
    }

    @Test
    void testLimpiarTodo() {
        calc.ingresarDigito("5");
        calc.establecerOperador("+");
        calc.ingresarDigito("3");
        calc.limpiarTodo();
        assertEquals("0", calc.getEntradaActual());
        assertEquals("", calc.getHistorial());
    }

    @Test
    void testOperacionEncadenada() {
        calc.ingresarDigito("5");
        calc.establecerOperador("+");
        calc.ingresarDigito("3");
        calc.calcular();
        calc.establecerOperador("*");
        calc.ingresarDigito("2");
        calc.calcular();
        assertEquals("16", calc.getEntradaActual());
    }

    @ParameterizedTest
    @CsvSource({
        "2, 3, 5",
        "0, 0, 0",
        "-1, 1, 0",
        "100, 200, 300"
    })
    void testSumaParametrizada(int a, int b, int esperado) {
        calc.ingresarDigito(String.valueOf(a));
        calc.establecerOperador("+");
        calc.ingresarDigito(String.valueOf(b));
        calc.calcular();
        assertEquals(String.valueOf(esperado), calc.getEntradaActual());
    }
}
