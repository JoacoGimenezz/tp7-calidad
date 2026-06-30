Feature: Operaciones basicas de la calculadora
  Como usuario quiero realizar operaciones aritmeticas
  Para obtener resultados correctos

  Scenario: Sumar dos numeros positivos
    Given que ingreso el numero 3
    And que ingreso el numero 5
    When selecciono la operacion suma
    Then el resultado debe ser 8

  Scenario: Restar dos numeros
    Given que ingreso el numero 10
    And que ingreso el numero 4
    When selecciono la operacion resta
    Then el resultado debe ser 6

  Scenario: Multiplicar dos numeros
    Given que ingreso el numero 6
    And que ingreso el numero 7
    When selecciono la operacion multiplicacion
    Then el resultado debe ser 42

  Scenario: Dividir por cero debe mostrar error
    Given que ingreso el numero 10
    And que ingreso el numero 0
    When selecciono la operacion division
    Then debe mostrar un mensaje de error
