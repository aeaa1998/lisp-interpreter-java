package com.algoritmos;

import java.util.LinkedList;

import static com.algoritmos.Utils.isDigit;


/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

//(DEFUN FTOC (TEMP)(/ (- TEMP 32) 1.8))
//(DEFUN FIBONACCI (N)(COND ((= N 0) 1)((= N 1) 1)(T (+ (FIBONACCI (- N 1))(FIBONACCI (- N 2))))))
public class OperatorStack extends Stack {
    private String operator;

    public OperatorStack(String operator, LinkedList<String> list) throws Exception {
        super(list);

        this.operator = operator;
        type = "operator";
    }



    @Override
    public String run() throws Exception {
        StringBuilder result = new StringBuilder();
        if (tokens.size() < 2) throw new Exception("No hay suficientes operandos");
        for (int i = 0; i < tokens.size(); i++) {
            var node = tokens.get(i);
            if (node instanceof VariableReferenceStack) {
                var holl = (VariableReferenceStack)node;
                if (!holl.scope.equalsIgnoreCase("global")){
                    node = MainMemory.getMemory().getVariableNode(MainMemory.getMemory().getFunctionNameScope() + holl.normalName);
                }

            }
            if (isDigit(node.run()) || node.isAtom()){
                if (i == 0) result = new StringBuilder(node.run() + " ");
                else result.append(operator).append(" ").append(node.run());
            }else{
                throw new Exception(node.run() + " No es un operando");
            }
        }

        var total = String.valueOf(JsReader.getJs().getEngine().eval(String.valueOf(result)));
        if (total.equalsIgnoreCase("Infinity")) throw new Exception("No se puede divir dentro de 0");
        return total;
    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    @Override
    public boolean isAtom() throws Exception {
        return true;
    }


}
