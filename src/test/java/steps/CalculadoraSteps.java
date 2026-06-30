package steps;

import io.cucumber.java.es.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraSteps {

    private CalculadoraLogica calc;
    private int a, b;
    private String operador;

    @Dado("que ingreso el numero {int}")
    public void queIngresoElNumero(int numero) {
        if (calc == null) {
            calc = new CalculadoraLogica();
        }
        if (operador == null) {
            a = numero;
            calc.ingresarDigito(String.valueOf(numero));
        } else {
            b = numero;
            calc.ingresarDigito(String.valueOf(numero));
        }
    }

    @Cuando("selecciono la operacion suma")
    public void seleccionoSuma() {
        calc.establecerOperador("+");
        operador = "+";
    }

    @Cuando("selecciono la operacion resta")
    public void seleccionoResta() {
        calc.establecerOperador("-");
        operador = "-";
    }

    @Cuando("selecciono la operacion multiplicacion")
    public void seleccionoMultiplicacion() {
        calc.establecerOperador("*");
        operador = "*";
    }

    @Cuando("selecciono la operacion division")
    public void seleccionoDivision() {
        calc.establecerOperador("/");
        operador = "/";
    }

    @Entonces("el resultado debe ser {int}")
    public void elResultadoDebeSer(int esperado) {
        calc.calcular();
        assertEquals(String.valueOf(esperado), calc.getEntradaActual());
    }

    @Entonces("debe mostrar un mensaje de error")
    public void debeMostrarError() {
        calc.calcular();
        assertTrue(calc.getEntradaActual().contains("No se puede dividir"));
    }
}
