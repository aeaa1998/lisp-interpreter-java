package com.algoritmos;

/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.ArrayList;
import java.util.LinkedList;

import static com.algoritmos.Utils.isDigit;


public class NodeFactory {


    //Se aplica singleton para crear una unica instancia
    private static NodeFactory nodeFactory = null;
    /**
     *
     * @param list lista con los strings para el nodo
     * @return un nodo anclado al Inode
     * @throws Exception
     */
    public iStack createNode(LinkedList<String> list) throws Exception {
        return createWrapper(list, MainMemory.getMemory().isInFunctionScoope(), MainMemory.getMemory().getCurrentFunctionName());
    }
    /**
     *
     * @param list una lista de strings para ver que tipo de nodo generar
     * @param functionScooped si la funcion esta dentro de algun contexto o no
     * @param functionName el nobmre de la funcion
     * @return un nodo
     * @throws Exception si hay error de sintaxis
     */
    public iStack createWrapper(LinkedList<String> list, boolean functionScooped, String functionName)  throws Exception{
        if (list.isEmpty()) return new NilStack(new LinkedList<>(){{}});
        var value = list.get(0);

        if (has(Constants.OPERATORS_TOKENS, value)){
            return new OperatorStack(list.get(0), Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if(has(Constants.LISP_UTILS, value)){
            return createLispNode(list);
        }else if(has(Constants.LOGICAL_TOKENS, value)){
            return new LogicalOperatorStack(list.get(0), Utils.returnListAsLinked(list.subList(1, list.size())));
        } else if(has(Constants.BOOLEAN_TOKENS, value)){
            return new ContainerStack(Utils.returnListAsLinked(list));
        }else if (value.equalsIgnoreCase(Constants.OPEN_LIST)){
            return new ContainerStack(list);
        }else {

            var valuetoCompare = "";
            if (functionScooped && value.equals(functionName)) {
                if (list.size() == 1) return new RecursiveFunctionHolder(functionName);
                else
                    return new RecursiveFunctionHolder(functionName, Utils.returnListAsLinked(list.subList(1, list.size())));
            }
            if (MainMemory.getMemory().existsFunction(value)) {
                LinkedList<String> listHolder;
                if (list.size() == 1) {
                    listHolder = new LinkedList<>();
                } else {
                    listHolder = Utils.returnListAsLinked(list.subList(1, list.size()));
                }


                   return new HolderStack(listHolder, value);

//                return fun;
            }
            ;

        }
        throw new Exception("Hay un error de sintaxis con " + list.toString());
    }

    public iStack createSimpleNode(String string) throws Exception {
        return createSimpleNodeWrapper(string, MainMemory.getMemory().isInFunctionScoope(), MainMemory.getMemory().getCurrentFunctionName());
    }

    /**
     *
     * @param isScooped si la funcion esta dentro de un contexto
     * @param functionName el nombre de la funcion
     * @return una lista de nodos
     * @throws Exception
     */
    private iStack createSimpleNodeWrapper(String string, boolean isScooped, String functionName) throws Exception{
        if (String.valueOf(string.charAt(0)).equalsIgnoreCase("'")){ return createQuotedValue(string.substring(1));}
        var list = new LinkedList<String>(){{add(string);}};

        if (isScooped) {
            if (functionName.equalsIgnoreCase("")) {
                functionName = MainMemory.getMemory().getFunctionsToSetValue().getLast();
            }
            ;
            if (MainMemory.getMemory().getParameterNames().get(functionName).contains(string))
                return MainMemory.getMemory().getVariableNode(MainMemory.getMemory().getFunctionNameScope() + string);
        }
        if (MainMemory.getMemory().existsVariable(string)) {
            return MainMemory.getMemory().getVariableNode(string);
        } else if (isDigit(string)){
            return new DoubleStack(list);
        } else if(String.valueOf(string.charAt(0)).equalsIgnoreCase("\"")){
            if(String.valueOf(string.charAt(string.length() - 1)).equalsIgnoreCase("\"")) return new StringStack(list);
            else throw new Exception("No cerro el string de manera correcta" + string);
        } else if (string.equalsIgnoreCase("NIL") || string.equalsIgnoreCase("T")){
            return new BooleanStack(new LinkedList<>(){{add(string.toUpperCase());}});

        }
        throw new Exception("Hay un error de sintaxis. \nCaracter invalido: " + string);

    }


    /**
     *
     * @param string un string para agregar a lista de elementos quoteados
     * @return una lista del tipo iNode
     * @throws Exception
     */
    public iStack createQuotedValue(String string) throws Exception {
        var list = new LinkedList<String>(){{add(string);}};
        return new QuotedValue(list);

    }

    /**
     *
     * @param list
     * @param isQuoted
     * @return
     * @throws Exception
     */
    public iStack createObject(LinkedList<String> list, boolean isQuoted) throws Exception {
        return new ObjectStack(list, isQuoted);
    }
    /**
     * @param list la lista con los strings para ver donde meter cada string
     * @return una lista con los nodos (lista) creados
     * @throws Exception por un error en sintaxis
     */
    public iStack createLispNode(LinkedList<String> list) throws Exception {
        if (list.isEmpty()) throw new Exception("No se acepta la sintaxis ()");
        var value = list.get(0);
        if (value.equalsIgnoreCase("print")){
            return new PrintStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if (value.equalsIgnoreCase("setq")){
            if (MainMemory.getMemory().existsFunction(list.get(1))) throw new Exception("Este nombre ya ha sido usado para funcion.");
            return new CreateVariableStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if (value.equalsIgnoreCase("set")){
            return new SetStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if(value.equalsIgnoreCase("quote")){
            var listHolder = Utils.returnListAsLinked(list.subList(1, list.size()));
            if (listHolder.isEmpty()) throw  new Exception("No se pasaron suficientes parametros para la instruccion quote");
            if (listHolder.size() == 1) return new QuotedValue(listHolder);
            else return new ObjectStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if(value.equalsIgnoreCase("list")){
            return new ObjectStack(Utils.returnListAsLinked(list.subList(1, list.size())), false);
        }else if(value.equalsIgnoreCase("cond")) {
            return new CondStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if(value.equalsIgnoreCase("nth")){
                return new CondStack(Utils.returnListAsLinked(list.subList(1, list.size())));
        }else if(value.equalsIgnoreCase("defun")){
            if (MainMemory.getMemory().existsFunction(list.get(1))) throw new Exception("Este nombre ya ha sido usado para funcion.");
            if (MainMemory.getMemory().existsVariable(list.get(1))) throw new Exception("Este nombre ya ha sido usado para variable.");
            return new CreateFunctionStack(Utils.returnListAsLinked(list.subList(1, list.size())));

        }
        throw new Exception("Hay un error de sintaxis con " + list.get(0));

    }

    /**
     *
     * @return un factory
     */
    public static NodeFactory getFactory() {
        if (nodeFactory == null) nodeFactory = new NodeFactory();
        return nodeFactory;
    }

    private boolean has(ArrayList<String> vals, String str) {
        for (String val : vals) if (str.equalsIgnoreCase(val)) return true;
        return false;
    }
}
