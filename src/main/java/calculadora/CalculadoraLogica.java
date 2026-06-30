package calculadora;

public class CalculadoraLogica {

    private double primerOperando = 0;
    private String operador = "";
    private boolean nuevaEntrada = true;
    private boolean hayError = false;

    private String entradaActual = "0";
    private String historial = "";

    // ── Getters ──────────────────────────────────────────────────

    public String getEntradaActual() { return entradaActual; }
    public String getHistorial()     { return historial; }

    // ── Acciones ─────────────────────────────────────────────────

    /**
     * Agrega un dígito o el punto decimal a la entrada actual.
     */
    public void ingresarDigito(String digito) {
        if (hayError) return;

        if (nuevaEntrada) {
            entradaActual = digito.equals(".") ? "0." : digito;
            nuevaEntrada = false;
        } else {
            if (digito.equals(".") && entradaActual.contains(".")) return;
            if (entradaActual.equals("0") && !digito.equals("."))
                entradaActual = digito;
            else
                entradaActual += digito;
        }
    }

    /**
     * Guarda el primer operando y el operador seleccionado.
     */
    public void establecerOperador(String op) {
        if (hayError) return;

        // Si hay operación pendiente, calculamos primero
        if (!nuevaEntrada && !operador.isEmpty()) {
            calcular();
        }

        primerOperando = Double.parseDouble(entradaActual);
        operador = op;
        nuevaEntrada = true;
        historial = formatear(primerOperando) + " " + simbolo(op);
    }

    /**
     * Realiza el cálculo con los dos operandos y el operador.
     */
    public void calcular() {
        if (hayError || operador.isEmpty()) return;

        double segundoOperando = Double.parseDouble(entradaActual);
        historial = formatear(primerOperando) + " " + simbolo(operador)
                  + " " + formatear(segundoOperando) + " =";

        double resultado;
        switch (operador) {
            case "+" -> resultado = primerOperando + segundoOperando;
            case "-" -> resultado = primerOperando - segundoOperando;
            case "*" -> resultado = primerOperando * segundoOperando;
            case "/" -> {
                if (segundoOperando == 0) {
                    establecerError("No se puede dividir por 0");
                    return;
                }
                resultado = primerOperando / segundoOperando;
            }
            case "%" -> resultado = primerOperando % segundoOperando;
            default  -> { return; }
        }

        entradaActual = formatear(resultado);
        primerOperando = resultado;
        operador = "";
        nuevaEntrada = true;
    }

    /**
     * Cambia el signo del número actual (positivo ↔ negativo).
     */
    public void cambiarSigno() {
        if (hayError) return;
        double valor = Double.parseDouble(entradaActual);
        entradaActual = formatear(-valor);
    }

    /**
     * Convierte el número actual a porcentaje (/100).
     */
    public void porcentaje() {
        if (hayError) return;
        double valor = Double.parseDouble(entradaActual);
        entradaActual = formatear(valor / 100);
        nuevaEntrada = true;
    }

    /**
     * Borra el último dígito ingresado.
     */
    public void retroceso() {
        if (hayError || nuevaEntrada) return;
        if (entradaActual.length() > 1)
            entradaActual = entradaActual.substring(0, entradaActual.length() - 1);
        else
            entradaActual = "0";
    }

    /**
     * Limpia solo la entrada actual (CE).
     */
    public void limpiarEntrada() {
        entradaActual = "0";
        nuevaEntrada = false;
        hayError = false;
    }

    /**
     * Reinicia toda la calculadora (C).
     */
    public void limpiarTodo() {
        entradaActual = "0";
        historial = "";
        primerOperando = 0;
        operador = "";
        nuevaEntrada = true;
        hayError = false;
    }

    /**
     * Pone la calculadora en estado de error.
     */
    public void establecerError(String mensaje) {
        entradaActual = mensaje;
        historial = "";
        hayError = true;
        nuevaEntrada = true;
    }

    // ── Helpers privados ─────────────────────────────────────────

    private String formatear(double numero) {
        // Muestra entero si no tiene decimales relevantes
        if (numero == Math.floor(numero) && !Double.isInfinite(numero)
                && Math.abs(numero) < 1e15) {
            return String.valueOf((long) numero);
        }
        return String.valueOf(numero);
    }

    private String simbolo(String op) {
        return switch (op) {
            case "+" -> "+";
            case "-" -> "−";
            case "*" -> "×";
            case "/" -> "÷";
            case "%" -> "%";
            default  -> op;
        };
    }
}
