package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.ArrayList;
import java.util.LinkedList;

public interface iStack {

    LinkedList<iStack> tokens = new LinkedList<>();
    ArrayList<iStack> invalidNodesTypes = new ArrayList<>();
    LinkedList<String> predicates = new LinkedList<>();
    String type = "";
    int relativePosition = 0;
    int parentesisCounter = 0;

    String run() throws Exception;
    String rawValue() throws Exception;
    //verifica si es un atom el elemento a analizar
    boolean isAtom() throws Exception;
    default void addAsFirst(iStack node){
        var holder = new ArrayList<iStack>() {{
            add(node);
            addAll(tokens);
        }};
        tokens.clear();
        tokens.addAll(holder);
    }
    LinkedList<iStack> getTokens();


}
