/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
package com.algoritmos;

import java.util.LinkedList;

public class IfNode extends Stack {
    public IfNode(LinkedList<String> list) throws Exception {
        super(list);
        if (tokens.size() != 2 && tokens.size() != 3) throw new Exception("Error en if");
    }

    @Override
    public String run() throws Exception {
        if (!tokens.get(0).rawValue().equalsIgnoreCase("T") && !tokens.get(0).rawValue().equalsIgnoreCase("nil"))
            throw new Exception("Valor pasado a IF no es booleano " + tokens.get(0).rawValue());

        if(tokens.get(0).rawValue().equalsIgnoreCase("T")){
            return tokens.get(1).run();

        } else if (tokens.size() == 3)
            return  tokens.get(2).run();

        return null;
    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    @Override
    public boolean isAtom() throws Exception {
        if(tokens.get(0).rawValue().equalsIgnoreCase("T")){
            return tokens.get(1).isAtom();

        } else if (tokens.size() == 3)
            return  tokens.get(2).isAtom();

        throw new Exception("No se puede verificar si dicho valor es atom");
    }
}
