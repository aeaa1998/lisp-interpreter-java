package com.algoritmos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Interpreter {
    private int parentesisCounter = 0;
    private int relativePosition = 0;
    private boolean quoted = false;
    /**
     * Function in charge of parsing the string into code
     * @param string as the lisp string to execute
     * **/
    public void parse(String string) throws Exception {
        ArrayList<iStack> nodes = new ArrayList<>();
        string = reformatString(string);
        string = string.replace(")", " ) ");
        string = string.replace("(", " ( ");
        string = string.replace("\n", " ");
        var holder = List.of(string.split(" "));
        LinkedList<String> listHolder = new LinkedList<>(holder);
        listHolder.removeIf(s -> (s.equalsIgnoreCase("")));
        StringBuilder stringBuilder = new StringBuilder();
        for (String withoutSpaces:
             listHolder) {
            stringBuilder.append(" ").append(withoutSpaces);
        }
        string = stringBuilder.toString();
        string = string.replace("' (", "'( ");
        holder = List.of(string.split(" "));
        LinkedList<String> list = new LinkedList<>(holder);
        list.removeIf(s -> (s.equalsIgnoreCase("")));
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if ((s.equalsIgnoreCase("'(") && !quoted)){
                if (parentesisCounter == 0) quoted = true;
                s = s.substring(s.length() - 1, s.length());
            }
            if (s.equalsIgnoreCase(Constants.OPEN_LIST)){
                if (parentesisCounter == 0) relativePosition = i;
                parentesisCounter++;
            }
            else if (s.equalsIgnoreCase(Constants.CLOSE_LIST)) parentesisCounter--;
            else if (parentesisCounter == 0 && !s.equalsIgnoreCase(Constants.OPEN_LIST) &&
                    !s.equalsIgnoreCase(Constants.CLOSE_LIST)){
                throw new Exception("Valor invalido");
            }
            if (parentesisCounter < 0) throw new Exception(") Colocado de manera incorrecta");
            if (parentesisCounter == 0){
                var linkedList = Utils.returnListAsLinked(list.subList(relativePosition + 1, i));
                iStack newNode;
                if (quoted){
                    newNode = StackFactory.getFactory().createObject(linkedList, true);
                    quoted = false;
                }else{
                    newNode = StackFactory.getFactory().createNode(linkedList);
                }
                nodes.add(newNode);
                relativePosition = i + 1;
            }
        }
        if (parentesisCounter != 0) throw new Exception("Se abrio un parentesis que nunca se cerro");
        for (iStack node : nodes) node.run();

    }
    /**
     *
     * @param string El codigo de lisp ingresado
     * @return el string verificado con el string bien cerrado y abierto
     * @throws Exception si el string se abrio pero nunca se cerro
     */
    private String reformatString(String string) throws Exception{
        var openedString = false;
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            var holder = String.valueOf(string.charAt(i));
            if (holder.equalsIgnoreCase("\"")) openedString = !openedString;
            if (openedString){
                for (int j = i + 1; j < string.length(); j++) {
                    var nextHolder = String.valueOf(string.charAt(j));
                    if (nextHolder.equalsIgnoreCase("\"")){
                        newString.append(string.substring(i, j+1).replace(" ", Constants.STRING_REFACTOR));
                        i = j;
                        j = string.length();
                        openedString = !openedString;
                    }
                }
                if (openedString)throw new Exception("Error no se cerro el string.");
            }else{
                newString.append(holder);
            }
        }
        return newString.toString();
    }

}
