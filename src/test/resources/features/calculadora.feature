Feature: Operaciones basicas de la calculadora

  Scenario: Sumar dos numeros positivos
    Given que tengo los numeros 3 y 5
    When selecciono la operacion suma
    Then el resultado debe ser 8

  Scenario: Restar dos numeros
    Given que tengo los numeros 10 y 4
    When selecciono la operacion resta
    Then el resultado debe ser 6

  Scenario: Multiplicar dos numeros
    Given que tengo los numeros 6 y 7
    When selecciono la operacion multiplicacion
    Then el resultado debe ser 42

  Scenario: Dividir por cero debe mostrar error
    Given que tengo los numeros 10 y 0
    When selecciono la operacion division
    Then debe mostrar un mensaje de error
