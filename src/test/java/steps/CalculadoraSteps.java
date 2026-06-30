package steps;

import calculadora.CalculadoraLogica;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraSteps {

    private CalculadoraLogica calc;

    @Given("que tengo los numeros {int} y {int}")
    public void queTengoLosNumeros(int a, int b) {
        calc = new CalculadoraLogica();
        calc.ingresarDigito(String.valueOf(a));
        this.a = a;
        this.b = b;
    }

    private int a, b;

    @When("selecciono la operacion suma")
    public void seleccionoSuma() {
        calc.establecerOperador("+");
        calc.ingresarDigito(String.valueOf(b));
    }

    @When("selecciono la operacion resta")
    public void seleccionoResta() {
        calc.establecerOperador("-");
        calc.ingresarDigito(String.valueOf(b));
    }

    @When("selecciono la operacion multiplicacion")
    public void seleccionoMultiplicacion() {
        calc.establecerOperador("*");
        calc.ingresarDigito(String.valueOf(b));
    }

    @When("selecciono la operacion division")
    public void seleccionoDivision() {
        calc.establecerOperador("/");
        calc.ingresarDigito(String.valueOf(b));
    }

    @Then("el resultado debe ser {int}")
    public void elResultadoDebeSer(int esperado) {
        calc.calcular();
        assertEquals(String.valueOf(esperado), calc.getEntradaActual());
    }

    @Then("debe mostrar un mensaje de error")
    public void debeMostrarError() {
        calc.calcular();
        assertTrue(calc.getEntradaActual().contains("No se puede dividir"));
    }
}
