

/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class CreateVariableStack implements iStack {
    private String name;
    /**
     * se agregan valores con los nombres que no se pueden usar para declarar variables
     */
    private ArrayList<String> invalidSyntax = new ArrayList<>(){{
        add("\""); add("'"); add("-");
        addAll(Constants.OPERATORS_TOKENS); addAll(Constants.LOGICAL_TOKENS);
        add(".");
        addAll(Constants.NUMBERS);
    }};
    private ArrayList<String> invalidNames = new ArrayList<>(){{ addAll(Constants.LISP_RESTRICTED_VALUES); addAll(Constants.LISP_UTILS);}};


    /**
     *
     * @param list strings con nombres para variables
     * @throws Exception si es un nombre ilegal
     */
    public CreateVariableStack(LinkedList<String> list) throws Exception {
        if(list.size() < 2) throw new Exception("La function setq recibe 2 parametros\n1. Nombre variable\n2. Valor");
        String name = list.getFirst();
        for (String invalid : invalidSyntax){
            if (name.contains(invalid)) throw new Exception(invalid + " es un elemento invalido para crear variables");
        }
        for (String invalid : invalidNames){
            if (name.equalsIgnoreCase(invalid)) throw new Exception(invalid + " es un nombre invalido para crear variables");
        }
        if (MainMemory.getMemory().existsVariable(name)) throw new Exception("Esa variable ya existe");
        var h = Utils.returnListAsLinked(list.subList(1, list.size()));
    this.name  = name;
        MainMemory.getMemory().createVariable(name, Utils.returnListAsLinked(list.subList(1, list.size())));

    }
    //ESTOS METODOS NO SE UTILIZAN EN ESTA CLASE
    @Override
    public String run() throws Exception {
        return MainMemory.getMemory().getVariableNode(name).run();
    }

    @Override
    public String rawValue() throws Exception { return MainMemory.getMemory().getVariableNode(name).rawValue(); }

    @Override
    public boolean isAtom() throws Exception{
        throw new Exception("Cannot check is an atom in here");
    }

    public void addAsFirst(iStack node) {

    }

    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
