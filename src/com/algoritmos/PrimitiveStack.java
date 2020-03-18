package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

abstract class PrimitiveStack implements iStack {
    protected LinkedList<iStack> tokens = new LinkedList<>();
    protected LinkedList<String> predicates;
    public PrimitiveStack(LinkedList<String> list) {
        predicates = list;
    }

    /**
     *
     * @return un string con una sustitucion de cambio de reglon por espacio vacio
     * @throws Exception por inteface
     */
    @Override
    public String run() throws Exception {
        return predicates.get(0).replace("\"", "");
    }
    /**
     *
     * @return el primer elemento del arraylist en mayuscula
     * @throws Exception por la interface
     */
    @Override
    public String rawValue() throws Exception {
        return predicates.get(0);
    }

    /**
     *
     * @return un booleano para ver si es un ATOM
     * @throws Exception by interface
     */
    @Override
    public boolean isAtom() throws Exception {
        return true;
    }


    /**
     * @return una linked list
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
