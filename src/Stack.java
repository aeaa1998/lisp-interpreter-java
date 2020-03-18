/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.ArrayList;
import java.util.LinkedList;


public abstract class Stack implements iStack {
    protected  ArrayList<String> invalidNodeTypes = new ArrayList<>();
    protected ArrayList<String> allowedCharacters = new ArrayList<>();
    protected LinkedList<String> predicates;
    protected LinkedList<iStack> tokens = new LinkedList<>();
    protected String type;
    boolean quoted = false;
    int relativePosition = 0;
    Integer parentesisCounter = 0;


    /**
     *
     * @param list la lista a la cual le vamos agregar el nodo viendo la posicion
     *             segun lo que nos diga el string
     * @throws Exception si hay error en la sintaxis
     */
    public Stack(LinkedList<String> list) throws Exception {
        predicates = list;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String holder = list.get(i);
            if ((s.equalsIgnoreCase("'(") && !quoted)){
                if (parentesisCounter == 0) quoted = true;
                s = s.substring(s.length() - 1, s.length());
            }
            if (s.equalsIgnoreCase(Constants.OPEN_LIST) || s.equalsIgnoreCase(Constants.CLOSE_LIST) || parentesisCounter > 0){
                if (s.equalsIgnoreCase(Constants.OPEN_SECIAL_LIST)){
                    parentesisCounter++;
                }
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
                parentesisCounter = 0;
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
        if (parentesisCounter < 0){ throw new Exception("Error sintaxis hay un ) de mas"); }



    }


    /**
     *
     * @return es un getter de tokens
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
