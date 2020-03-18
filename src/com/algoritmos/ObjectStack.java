package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

public class ObjectStack implements iStack {
    LinkedList<String> predicates;

    LinkedList<iStack> tokens = new LinkedList<>();
    int relativePosition = 0;
    Integer parentesisCounter = 0;

    boolean quoted = false;


    /**
     *
     * @param list recibe una lista con
     * @param objectIsQuoted es un false
     * @throws Exception
     */
    public ObjectStack(LinkedList<String> list, boolean objectIsQuoted) throws Exception {
        if (!list.isEmpty()) fillObject(list, objectIsQuoted);
        else tokens.add( new NilStack(new LinkedList<>()));
    }


    /**
     *
     * @param list elementos que se mandaron en una lista
     * @throws Exception por interface
     */
    public ObjectStack(LinkedList<String> list) throws Exception {
        fillObject(list, true);
        if (tokens.size() != 1) throw new Exception("Error al mandar parametros de mas en quote");
    }

    /**
     *
     * @param list una lista con los elementos a revisar y agregar ciertos simbolos de sintaxis
     * @param objectIsQuoted es un false porque el elemento no esta quoteado
     * @throws Exception si esta quoteado el codigo
     */
    private void fillObject(LinkedList<String> list, boolean objectIsQuoted) throws Exception{
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i); String holder = list.get(i);
            if ((s.equalsIgnoreCase("'(") && !quoted)){
                if (parentesisCounter == 0) quoted = true;
                s = s.substring(s.length() - 1, s.length());
            }
            if (s.equalsIgnoreCase(Constants.OPEN_LIST) || s.equalsIgnoreCase(Constants.CLOSE_LIST) || parentesisCounter > 0){
                if (s.equalsIgnoreCase(Constants.OPEN_LIST) || s.equalsIgnoreCase(Constants.OPEN_SECIAL_LIST)) {
                    if (parentesisCounter == 0) relativePosition = i;
                    parentesisCounter++;
                }
                else if (list.get(i).equalsIgnoreCase(Constants.CLOSE_LIST)) parentesisCounter--;
                if (parentesisCounter == 0){
                    var linkedList = Utils.returnListAsLinked(list.subList(relativePosition + 1, i));
                    iStack newNode;
                    if (quoted || objectIsQuoted){
                        if (linkedList.isEmpty()) newNode =  new NilStack(linkedList);
                        else newNode = NodeFactory.getFactory().createObject(linkedList, true);
                        if (objectIsQuoted && quoted) newNode.getTokens().addFirst(new QuotedValue(new LinkedList<>(){{add("'");}}));
                        quoted = false;
                    }else{
                        newNode = NodeFactory.getFactory().createNode(linkedList);
                    }
                    tokens.add(newNode);
                    relativePosition = i + 1;
                }
            }else{
                iStack newNode;
                if (objectIsQuoted) newNode = NodeFactory.getFactory().createQuotedValue(s);
                else newNode = NodeFactory.getFactory().createSimpleNode(s);
                tokens.add(newNode);
            }
        }
        if (list.isEmpty()) throw new Exception("No value passed for QUOTED expression ");
        predicates = list;
    }

    /**
     *
     * @return un string con los cambios efectuados
     * @throws Exception si no es string
     */
    @Override
    public String run() throws Exception {
        if (tokens.size() == 1) {
            if (tokens.get(0).run().equalsIgnoreCase("nil")) return tokens.get(0).run().toUpperCase();
            return "( " + tokens.get(0).run()  + " )";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("( ");
        for (int i = 0; i < tokens.size(); i++) {
            iStack token = tokens.get(i);
            builder.append(token.run() + " ");
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     *
     * @return un string para seguir agregando cosas
     * @throws Exception por cualquier cosa
     */
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return true si el linkedlist solo tiene un elemento
     * @throws Exception por interface
     */
    @Override
    public boolean isAtom() throws Exception {
        return tokens.size() == 1;
    }

    /**
     * No hace nada solo overridea
     * @param node recibe un arraylist
     */
    @Override
    public void addAsFirst(iStack node) {

    }

    /**
     *
     * @return los tokens
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }

}
