package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.ArrayList;
import java.util.LinkedList;


public class CreateFunctionStack implements iStack {
    //Se agregan operadores a arraylist
    private ArrayList<String> invalidSyntax = new ArrayList<>(){{
        add("\""); add("'"); add("-");
        addAll(Constants.OPERATORS_TOKENS); addAll(Constants.LOGICAL_TOKENS);
        add(".");
//        addAll(Constants.NUMBERS);
    }};
    //Se crea arraylist con los valores con las funciones de lisp
    private ArrayList<String> invalidNames = new ArrayList<>(){{ addAll(Constants.LISP_RESTRICTED_VALUES); addAll(Constants.LISP_UTILS);}};


    /**
     *
     * @param list strings para analizar
     * @throws Exception si esta el nombre en cualquier lista significa que lisp no acepta estas como parametros
     */
    public CreateFunctionStack(LinkedList<String> list) throws Exception {
        if(list.size() < 2) throw new Exception("La function defun recibe 3 parametros\n1. Nombre de funcion\n2. Parametros \n3. Cuerpo");
        String name = list.getFirst();
        for (String invalid : invalidSyntax){
            if (name.contains(invalid)) throw new Exception(invalid + " es un elemento invalido para crear funciones");
        }
        for (String invalid : invalidNames){
            if (name.equalsIgnoreCase(invalid)) throw new Exception(invalid + " es un nombre invalido para crear funciones");
        }
        MainMemory.getMemory().createFunction(name, Utils.returnListAsLinked(list.subList(1, list.size())));


    }

    /**
     *
     * @return null no hace nada
     * @throws Exception
     */
    @Override
    public String run() throws Exception {
        return null;
    }

    /**
     *
     * @return null
     * @throws Exception no hace no se usa
     */
    @Override
    public String rawValue() throws Exception {
        return null;
    }

    /**
     *
     * @return false porque ninguno de estos elementos son un atom
     * @throws Exception
     */
    @Override
    public boolean isAtom() throws Exception {
        return false;
    }


    /**
     * no hace nada este metodo aca
     * @param node un arraylist
     */
    @Override
    public void addAsFirst(iStack node) {

    }

    /**
     *
     * @return un linkedlist con los tokens
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }


}
