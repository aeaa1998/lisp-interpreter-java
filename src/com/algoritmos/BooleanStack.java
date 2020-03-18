package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;


public class BooleanStack extends PrimitiveStack {
    /**
     *
     * @param list son una lista con los operadores a revisar
     * @throws Exception si los operadores no son t, T, nil o NIL
     */
    public BooleanStack(LinkedList<String> list) throws Exception {
        super(list);
        if (!list.contains("t") && !list.contains("T") && !list.contains("nil") && !list.contains("NIL")) throw new Exception("Invalid boolean");
        if (predicates.size() != 1) throw new Exception("Invalid boolean");
    }

    /**
     *
     * @return un string quitando un cambio de linea por un espacion vacio
     * @throws Exception por la inteface
     */
    @Override
    public String run() throws Exception {
        return predicates.get(0).replace("\"", "").toUpperCase();
    }

    /**
     *
     * @return el primer elemento del arraylist en mayuscula
     * @throws Exception por la interface
     */
    @Override
    public String rawValue() throws Exception {
        return predicates.get(0).toUpperCase();
    }

    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
