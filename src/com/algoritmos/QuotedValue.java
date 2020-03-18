package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;


public class QuotedValue implements iStack {
    LinkedList<String> predicates;

    /**
     *
     * @param list se pasan los parametros que estan cuoteados en teoria
     * @throws Exception defensa si no estan bien quoteados
     */
    public QuotedValue(LinkedList<String> list) throws Exception {
        if (list.isEmpty()) throw new Exception("No value passed for QUOTED expression ");
        if (list.size() != 1) throw new Exception("Error sintax error in quote");
        if (String.valueOf(list.get(0).charAt(0)).equalsIgnoreCase("(")) throw new Exception("Error sintax error in quote");
        if (String.valueOf(list.get(0).charAt(0)).equalsIgnoreCase(")")) throw new Exception("Error sintax error in quote");
        if (String.valueOf(list.get(list.size() -1).charAt(0)).equalsIgnoreCase(")")) throw new Exception("Error sintax error in quote");
        if (String.valueOf(list.get(list.size() -1).charAt(0)).equalsIgnoreCase("(")) throw new Exception("Error sintax error in quote");
        if (String.valueOf(list.get(0).charAt(0)).equalsIgnoreCase("\"")  && !String.valueOf(list.get(list.size() -1).charAt(0)).equalsIgnoreCase("\"")
        ) throw new Exception("Error sintax error in quote");
        if (!String.valueOf(list.get(0).charAt(0)).equalsIgnoreCase("\"")  && String.valueOf(list.get(list.size() -1).charAt(0)).equalsIgnoreCase("\"")
        ) throw new Exception("Error sintax error in quote");


        predicates = list;
        predicates.set(0,predicates.get(0).replace(Constants.STRING_REFACTOR, " "));
    }

    /**
     *
     * @return un string con el primer elemento en mayusucla
     * @throws Exception por inteface
     */
    @Override
    public String run() throws Exception {
        if (predicates.get(0).charAt(0) == '\"') return predicates.get(0);
        return predicates.get(0).toUpperCase();
    }
    //ESTOS METODOS NO SE UTILIZAN
    @Override
    public String rawValue() throws Exception {
        return  run();
    }

    @Override
    public boolean isAtom() throws Exception {
        return true;
    }

    @Override
    public void addAsFirst(iStack node) {

    }

    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
