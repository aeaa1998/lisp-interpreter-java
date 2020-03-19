
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

public class EltStack extends Stack {
    /**
     *
     * @param list
     * @throws Exception
     */
    public EltStack(LinkedList<String> list) throws Exception {
        super(list);
        if(tokens.size() != 2) throw new Exception("Elt should receive two elements");

        String holder = tokens.get(0).rawValue();

        if (holder.startsWith("\"") && holder.endsWith("\""))
            return;

        if (holder.startsWith("(") && holder.endsWith(")"))
            return;

        throw new Exception("Elt only accepts sequences");
    }

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
        String holder = tokens.get(0).rawValue();

        if (holder.startsWith("\"")){
            if (holder.length() < index + 3) throw new Exception("Elt index out of range");
            return "#\\" + holder.charAt(index + 1);
        }

        String[] listHolder = holder.substring(1, holder.length() - 1).trim().split(" ");
        if (listHolder.length <= index) throw new Exception("Elt index out of range");
        return listHolder[index];

    }

    @Override
    public String rawValue() throws Exception {
        return run();
    }

    @Override
    public boolean isAtom() throws Exception {
        String holder = rawValue();
        if(holder.startsWith("\""))
            return true;

        return tokens.get(0).getTokens().get(Integer.parseInt(tokens.get(1).rawValue())).isAtom();
    }
}