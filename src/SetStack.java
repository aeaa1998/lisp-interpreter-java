/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

public class SetStack implements iStack {
    private String varName;
    protected String type;
    boolean quoted = false;
    int relativePosition = 0;
    Integer parentesisCounter = 0;
    protected LinkedList<String> predicates;
    protected LinkedList<iStack> tokens = new LinkedList<>();
    public SetStack(LinkedList<String> list) throws Exception {
        if(list.size() < 2) throw new Exception("La function setq recibe 2 parametros\n1. Nombre variable\n2. Valor");
        predicates = list;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String holder = list.get(i);
            if ((s.equalsIgnoreCase("'(") && !quoted)){
                if (parentesisCounter == 0) quoted = true;
                s = s.substring(s.length() - 1, s.length());
            }
            if (s.equalsIgnoreCase(Constants.OPEN_LIST) || s.equalsIgnoreCase(Constants.CLOSE_LIST) || parentesisCounter > 0){
                if (s.equalsIgnoreCase(Constants.OPEN_LIST)) {
                    if (parentesisCounter == 0) relativePosition = i;
                    parentesisCounter++;
                }
                else if (list.get(i).equalsIgnoreCase(Constants.CLOSE_LIST)) parentesisCounter--;
                if (parentesisCounter == 0){
                    var linkedList = Utils.returnListAsLinked(list.subList(relativePosition + 1, i));
                    iStack newNode;
                    if (quoted){
                        if (linkedList.isEmpty()) newNode =  new NilStack(linkedList);
                        else newNode = NodeFactory.getFactory().createObject(linkedList, true);
                        quoted = false;
                    }else{
                        newNode = NodeFactory.getFactory().createNode(linkedList);
                    }
                    tokens.add(newNode);
                    relativePosition = i + 1;
                }
            }else{
                var newNode = NodeFactory.getFactory().createSimpleNode(s);
                tokens.add(newNode);
            }
        }
        if (parentesisCounter > 0){  throw new Exception("No se cerro parentesis");}
        if (parentesisCounter < 0){  throw new Exception("Error sintaxis hay un ) de mas");}
        String name = list.getFirst();
        varName = name;
    }

    @Override
    public String run() throws Exception {
        if (!MainMemory.getMemory().existsVariable(varName)) throw new Exception("Error variable no existe");
        if (tokens.size() > 2) throw new Exception("Error de sintaxis al asignarle valor a la variable " + varName);
        if (tokens.isEmpty()) throw new Exception("No value passed for variable " + varName);
        else if (tokens.get(1).run() == null) throw new Exception("Invalid value");
        MainMemory.getMemory().getVariableNode(varName).setValue(tokens.get(1).run());
        return null;
    }

    @Override
    public String rawValue() throws Exception {
        return null;
    }

    @Override
    public boolean isAtom() throws Exception {
        return false;
    }

    @Override
    public void addAsFirst(iStack node) {

    }

    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
