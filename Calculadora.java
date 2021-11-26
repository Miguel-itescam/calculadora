import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Calculadora {

    public String infijoPosfija(List<String> listaSimbolos) {
        Stack<String> operador = new Stack<String>();
        List<String> listaSufija = new ArrayList<>();
        boolean esNegativo = false;
        boolean parentesis = false;
        boolean esDigito = false;

        for (String simbolo : listaSimbolos) {
            try {
                Integer.parseInt(simbolo);
                esDigito = true;
            } catch (Exception e) {
                esDigito = false;
            }

            if (esDigito) {
                if (esNegativo) {
                    esNegativo = false;
                    listaSufija.add("-" + simbolo);
                } else
                    listaSufija.add(simbolo);
                parentesis = false;
            } else if (simbolo.equals("(")) {
                operador.push(simbolo);
                parentesis = true;
            } else if (simbolo.equals(")")) {
                String simboloTope = operador.pop();
                while (!simboloTope.equals("(")) {
                    listaSufija.add(simboloTope);
                    simboloTope = operador.pop();
                }
            } else {
                if (listaSufija.size() == 0  || parentesis)
                    esNegativo = true;
                else {
                    while ((!operador.isEmpty()) && (prioridad(operador.peek()) >= prioridad(simbolo)))
                        listaSufija.add(operador.pop());
                    operador.push(simbolo);
                }
            }
        }

        while (!operador.isEmpty())
            listaSufija.add(operador.pop());
        return String.join("", listaSufija);
    }

    public int prioridad(String operador) {
        switch (operador) {
        case "*":
        case "/":
            return 3;
        case "+":
        case "-":
            return 2;
        case "(":
        case ")":
            return 1;
        default:
            return 0;
        }
    }

    public String infijoPrefija(String expresionInfija) {
        Stack<String> operador = new Stack<String>();
        List<String> listaPrefija = new ArrayList<>();
        List<String> listaSimbolos = new ArrayList<>(Arrays.asList(expresionInfija.split("")));
        Collections.reverse(listaSimbolos);

        for (String simbolo : listaSimbolos) {
            if (Character.isDigit(simbolo.charAt(0)))
                listaPrefija.add(simbolo);
            else if (simbolo.equals(")"))
                operador.push(simbolo);
            else if (simbolo.equals("(")) {
                String simboloTope = operador.pop();
                while (!simboloTope.equals(")")) {
                    listaPrefija.add(simboloTope);
                    simboloTope = operador.pop();
                }
            } else {
                while ((!operador.isEmpty()) && (prioridad(operador.peek()) >= prioridad(simbolo)))
                    listaPrefija.add(operador.pop());
                operador.push(simbolo);
            }
        }

        while (!operador.isEmpty())
            listaPrefija.add(operador.pop());
        Collections.reverse(listaPrefija);
        return String.join("", listaPrefija);
    }

    public String resultado(List<String> listaSimbolos) {
        Stack<String> operador = new Stack<String>();
        Stack<Integer> valoresResultado = new Stack<Integer>();
        boolean esNegativo = false;
        boolean parentesis = false;
        boolean esDigito = false;

        for (String simbolo : listaSimbolos) {
            try {
                Integer.parseInt(simbolo);
                esDigito = true;
            } catch (Exception e) {
                esDigito = false;
            }

            if (esDigito) {
                if (esNegativo) {
                    valoresResultado.push(Integer.parseInt("-" + simbolo));
                    esNegativo = false;
                } else
                    valoresResultado.push(Integer.parseInt(simbolo));
                parentesis = false;
            } else if (simbolo.equals("(")) {
                operador.push(simbolo);
                parentesis = true;
            } else if (simbolo.equals(")")) {
                String simboloTope = operador.pop();
                while (!simboloTope.equals("(")) {
                    valoresResultado.push(calcular(valoresResultado.pop(), valoresResultado.pop(), simboloTope));
                    simboloTope = operador.pop();
                }
            } else {
                if (valoresResultado.size() == 0 || parentesis)
                    esNegativo = true;
                else {
                    while ((!operador.isEmpty()) && (prioridad(operador.peek()) >= prioridad(simbolo)))
                        valoresResultado.push(calcular(valoresResultado.pop(), valoresResultado.pop(), operador.pop()));
                    operador.push(simbolo);
                }
            }
        }

        while (!operador.isEmpty())
            valoresResultado.push(calcular(valoresResultado.pop(), valoresResultado.pop(), operador.pop()));

        int result = valoresResultado.pop();

        return String.valueOf(result);

    }

    public int calcular(int num1, int num2, String o) {
        switch (o) {
        case "+":
            return num2 + num1;
        case "-":
            return num2 - num1;
        case "*":
            return num2 * num1;
        case "/":
            if (num1 == 0)
                throw new IllegalArgumentException("No Se Puede Dividir Entre 0");
            return num2 / num1;
        default:
            return 0;
        }
    }

    public List<String> dividirExpresion(String expresionInfija) {
        List<String> expDiv = new ArrayList<String>();
        boolean esDigito = false;
        String digito = "";

        for (String elemento : expresionInfija.split("")) {
            if (Character.isDigit(elemento.charAt(0))) {
                digito += elemento;
                esDigito = true;
            } else if (esDigito) {
                expDiv.add(digito);
                digito = "";
                esDigito = false;
            }
            if (!esDigito)
                expDiv.add(elemento);
        }

        if (!digito.isEmpty()) {
            expDiv.add(digito);
        }

        return expDiv;
    }
}
