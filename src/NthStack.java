/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;


public class NthStack extends Stack {
    int index;

    public NthStack(LinkedList<String> list) throws Exception {
        super(list);
        if(tokens.size() != 2) throw new Exception("Nth debe recibir dos argumentos");

        if(!tokens.get(1).rawValue().startsWith("(") && !tokens.get(1).rawValue().endsWith(")"))
            throw new Exception(list.get(1) + " no es una lista");

        if(!Utils.isNonNegativeInteger(tokens.get(0).rawValue()))
            throw new Exception("Nth solo recibe integers no negativos");
        index = Integer.parseInt(tokens.get(0).rawValue());
    }

    @Override
    public String run() throws Exception {
        var holder = tokens.get(1).rawValue();
        var list = tokens.get(1).rawValue().substring(1, holder.length() - 1).trim().split(" ");
        if(list.length <= index) return "NILL";
        return list[index];
    }

    private iStack NthStack() throws Exception {


        return tokens.get(1).getTokens().get(index);
    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    @Override
    public boolean isAtom() throws Exception {
            return NthStack().isAtom();

    }
}
