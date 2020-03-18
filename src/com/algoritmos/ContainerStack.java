package com.algoritmos;

import java.util.LinkedList;


/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

public class ContainerStack extends Stack {
    /**
     *
     * @param list se recibe un array con strings para guardar
     * @throws Exception por interface
     */
    public ContainerStack(LinkedList<String> list) throws Exception {
        super(list);
    }

    /**
     *
     * @return solamente usa el metodo run de la clase que esta arriba
     * @throws Exception
     */
    @Override
    public String run() throws Exception {
//        if (tokens.size() == 1) return tokens.get(0).run();
        for (iStack node: tokens){
            node.run();
        }
        return null;
    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return si solamente la lista tiene un elemento se retorna true
     * @throws Exception si no se puede ver de que size es la lista
     */
    @Override
    public boolean isAtom() throws Exception {
        if (tokens.size() == 1) return tokens.get(0).isAtom();
        throw new Exception("Cannot check is an atom in here");
    }


}
