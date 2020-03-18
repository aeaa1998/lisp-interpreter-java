package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

public class NilStack extends PrimitiveStack {


    public NilStack(LinkedList<String> list) throws Exception {
        super(list);
        if (list.size()==2){
            if (list.get(0).equalsIgnoreCase("(") && list.get(1).equalsIgnoreCase(")")) list.clear();
            else throw new Exception("Error valor no nulo");
        }

        if (!list.isEmpty()) throw new Exception("Error valor no nulo");
        predicates.add("nil");
    }

    @Override
    public String run() throws Exception {
        if (!predicates.get(0).equalsIgnoreCase("nil")) throw new Exception("El valor deberia de ser nil");
        return  predicates.get(0).toUpperCase();
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
