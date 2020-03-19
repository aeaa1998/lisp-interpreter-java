package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

import static com.algoritmos.Utils.isDigit;


public class LogicalOperatorStack extends Stack {
    private String operator;

    /**
     *
     * @param operator se recibe un string de operador
     * @param list los dos elementos a comparar
     * @throws Exception si solo se pasa un elemento a comprar
     */
    public LogicalOperatorStack(String operator, LinkedList<String> list) throws Exception {
        super(list);
        this.operator = operator;
        type = "operator";
        if ((this.operator.equalsIgnoreCase("ATOM") || this.operator.equalsIgnoreCase("NOT")) && tokens.size() != 1)
            throw new Exception("El operando " + operator + " solo recibe 1 parametro");
    }

    /**
     * Tiene toda la logica de la comparacion
     * @return el string con el resultado de la comparacion
     * @throws Exception si hay algun error en la sintaxis como falta de parametros o error al comprar
     * dos tipos de variables distintas
     */
    @Override
    public String run() throws Exception {
        if (tokens.isEmpty()) throw new Exception("No se paso ni un operando " + operator);
        for (iStack token: tokens) { if (token.rawValue() == null) throw new Exception("Valor invalido no se puede comparar con nada");}
        if (operator.equalsIgnoreCase("ATOM")) return (tokens.getFirst().isAtom()) ? "T" : "NIL";
        if (operator.equalsIgnoreCase("NOT")){
            if( tokens.getFirst().run().equalsIgnoreCase("NIL")){
                return "T";
            } else {
                return "NIL";
            }
        }
        StringBuilder result = new StringBuilder();
        if (tokens.size() < 2) throw new Exception("No hay suficientes operandos para " + operator);
        else if (tokens.size() > 2) throw new Exception("Hay  demasiados operandos logicos");

        var secondNode = tokens.get(1);
        var firstNode = tokens.get(0);
        var areNil = (firstNode instanceof NilStack || secondNode instanceof NilStack);
        if (areNil) return (firstNode instanceof NilStack && secondNode instanceof NilStack) ? "T": "NIL";
        if(canCompare(firstNode, secondNode, operator)){
            if ((!firstNode.isAtom() && secondNode.isAtom()) || (!secondNode.isAtom() && firstNode.isAtom()))
                return "NIL";
            else if(!firstNode.isAtom() && !secondNode.isAtom()){
                result.append("\"").append(firstNode.run()).append("\"")
                        .append(" === ")
                        .append("\"")
                        .append(secondNode.run())
                        .append("\"");
            }else{
                if (operator.equalsIgnoreCase("EQUAL")){
                    if (firstNode.rawValue().contains("\"") && secondNode.rawValue().contains("\"")){
                        result.append(firstNode.rawValue()).append(" == ").append(secondNode.rawValue());
                    }else if (
                            (!isDigit(firstNode.rawValue()) && isDigit(secondNode.rawValue())) ||
                            (!isDigit(secondNode.rawValue()) && isDigit(firstNode.rawValue()))
                    ){
                        return "NIL";
                    }else if (firstNode instanceof BooleanStack || secondNode instanceof BooleanStack){
                        return firstNode.rawValue().equalsIgnoreCase(secondNode.rawValue()) ? "T" : "NIL";
                    } else {
                        if (firstNode.rawValue().contains(".") || secondNode.rawValue().contains(".")){
                            if (firstNode.rawValue().contains(".") && secondNode.rawValue().contains("."))
                                result.append(firstNode.rawValue()).append(" == ").append(secondNode.rawValue());
                            else return "NIL";
                        }else{
                            result.append(firstNode.rawValue()).append(" == ").append(secondNode.rawValue());
                        }
                    }
                }else{
                    if (operator.equalsIgnoreCase("=")) operator = "==";
                    result.append(firstNode.rawValue()).append(" ").append(operator).append(" ").append(secondNode.rawValue());
                }
            }
            var total = (Boolean)(JsReader.getJs().getEngine().eval(String.valueOf(result)));

            return total ? "T" : "NIL";
        }

        throw new Exception("No se pueden comparar los valores " + firstNode.rawValue() +" y " + secondNode.rawValue());
    }

    /**
     *
     * @return el string que se esta trabajando
     * @throws Exception
     */
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @param node1 el elemento a comparar
     * @param node2 el otro elemento a comparar
     * @param operator un "y" o un "o" en lisp
     * @return si se puede realizar la operacion true
     * @throws Exception
     */
    public boolean canCompare(iStack node1, iStack node2, String operator) throws Exception{
        if (operator.equalsIgnoreCase("EQUAL")) return true;
        else
//            (operator.equalsIgnoreCase(">") || operator.equalsIgnoreCase("<") ||
//                operator.equalsIgnoreCase("=") || operator.equalsIgnoreCase(">=")
//        || operator.equalsIgnoreCase("<="))
    {
            if (!node1.isAtom() || !node2.isAtom()) return false;
            return isDigit(node1.rawValue()) && isDigit(node2.rawValue());
        }
    }

    @Override
    public boolean isAtom() throws Exception {
        return true;
    }
}
