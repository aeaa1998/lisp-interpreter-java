package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

public class EltNode extends Stack{
    /**
     *
     * @param list
     * @throws Exception
     */
    public EltNode(LinkedList<String> list) throws Exception {
        super(list);
        if(tokens.size() != 2) throw new Exception("Elt should receive two elements");

        if(tokens.get(0) instanceof VariableReferenceStack)
            tokens.set(0, tokens.get(0).getTokens().get(0));

        if(!(tokens.get(0) instanceof ObjectStack || tokens.get(0) instanceof StringStack))
            throw new Exception(list.get(0) + " is not sequence");
    }

    /*private iNode EltNode() throws Exception{
        if(!Utils.isNonNegativeInteger(tokens.get(1).rawValue()))
            throw new Exception("Elt solo recibe integers no negativos");

        int index = Integer.parseInt(tokens.get(1).rawValue());

        if(tokens.get(0) instanceof ObjectNode) {
            if (index >= tokens.get(0).getTokens().size()) throw new Exception("No existe un elemento con ese indice");
            return tokens.get(0).getTokens().get(index).rawValue();
        }

        if(index >= tokens.get(0).rawValue().length()) throw new Exception("No existe un elemento con ese indice");
        return Character.toString(tokens.get(0).run().charAt(index));
    }*/

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public String run() throws Exception {
        if(!Utils.isNonNegativeInteger(tokens.get(1).rawValue()))
            throw new Exception("Elt solo recibe integers no negativos");

        int index = Integer.parseInt(tokens.get(1).rawValue());

        if(tokens.get(0) instanceof ObjectStack) {
            if (index >= tokens.get(0).getTokens().size()) throw new Exception("No existe un elemento con ese indice");
            return tokens.get(0).getTokens().get(index).rawValue();
        }

        if(index >= tokens.get(0).rawValue().length()) throw new Exception("No existe un elemento con ese indice");
        return Character.toString(tokens.get(0).run().charAt(index));
    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    @Override
    public boolean isAtom() throws Exception {
        rawValue();
        if(tokens.get(0) instanceof StringStack)
            return true;

        return tokens.get(0).getTokens().get(Integer.parseInt(tokens.get(1).rawValue())).isAtom();
    }
}
