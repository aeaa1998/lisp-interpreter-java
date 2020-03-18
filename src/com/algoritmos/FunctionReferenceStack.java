package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class FunctionReferenceStack implements iStack {
    //Se crean variables
    private String functionName;
    boolean quoted = false;
    String id ="";
    protected LinkedList<String> predicates;
    protected ArrayList<String> parameters = new ArrayList<>();
    protected LinkedList<iStack> tokens = new LinkedList<>();
    int relativePosition = 0;
    Integer parentesisCounter = 0;
    Integer parentesisCreated = 0;
    private ArrayList<String> invalidSyntax = new ArrayList<>(){{
        add("\""); add("'"); add("-");
        addAll(Constants.OPERATORS_TOKENS); addAll(Constants.LOGICAL_TOKENS);
        add(".");
        addAll(Constants.NUMBERS);
    }};
    //Arraylist con valores que no se pueden utilizar
    private ArrayList<String> invalidNames = new ArrayList<>(){{ addAll(Constants.LISP_RESTRICTED_VALUES); addAll(Constants.LISP_UTILS);}};

    /**
     *
     * @param functionName es el nombre de la funcion
     * @param tokens los parametros a utilizar de la funcion
     * @throws Exception
     */
    public FunctionReferenceStack(String functionName, LinkedList<iStack> tokens) throws Exception {
        this.functionName = functionName;
        this.tokens = new LinkedList<>(){{addAll(tokens);}};
        this.parameters = MainMemory.getMemory().getParameterNames().get(functionName);
        this.id = UUID.randomUUID().toString();
        for (String parameterName: parameters){
            MainMemory.getMemory().createParameter(id + functionName + parameterName, parameterName);
        }

    }


    /**
     *
     * @param functionName el nombre de la funcion
     * @param tokens los arraylist que estan en inode
     * @return una funcion con el nombre de las clases que va a utilizar en el linkedlist
     * @throws Exception
     */
    public  FunctionReferenceStack returnNewFunc(String functionName, LinkedList<iStack> tokens) throws Exception{

        return new FunctionReferenceStack(functionName, tokens);
    }

    /**
     *
     * @param list con el nombre de las clases que va a usar
     * @param name nombre de la funcion
     * @throws Exception por errores de sintaxis
     */
    public FunctionReferenceStack(LinkedList<String> list, String name) throws Exception {
        MainMemory.getMemory().setInFunctionScoope(true);
        MainMemory.getMemory().setFunctionNameScope(name);
        MainMemory.getMemory().setFunctionName(name);
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
                    if (parentesisCreated < 1){
                        if (quoted) throw new Exception("Error no pueden estar quoteados los parametros");
                        for (String parameterName: linkedList){
                            checkNameIsValid(parameterName);
                            parameters.add(parameterName);

                            MainMemory.getMemory().createParameter(id + name + parameterName, parameterName);
                        }
                        MainMemory.getMemory().setParametersArrayList(name, parameters);
                    }else{
                        iStack newNode;
                        if (quoted){
                            if (linkedList.isEmpty()) newNode =  new NilStack(linkedList);
                            else newNode = StackFactory.getFactory().createObject(linkedList, true);
                            quoted = false;
                        }else{
                            newNode = StackFactory.getFactory().createNode(linkedList);
                        }
                        tokens.add(newNode);
                        relativePosition = i + 1;
                    }
                    parentesisCreated++;
                }
            }else{
                if (parentesisCreated < 1) throw new Exception("Error no se pasaron parametros");
                if (!parameters.contains(s)){
                    var newNode = StackFactory.getFactory().createSimpleNode(s);
                    tokens.add(newNode);
                }else{
                    tokens.add(MainMemory.getMemory().getVariableNode(id + name  + s));
                }
            }
        }
        if (parentesisCounter > 0){  throw new Exception("No se cerro parentesis");}
        if (parentesisCounter < 0){  throw new Exception("Error sintaxis hay un ) de mas");}
        if (tokens.size() != 1) throw new Exception("Error de sintaxis al crear  la funcion mas de un cuerpo pasado");
        functionName = name;

        MainMemory.getMemory().removeFunctionName(functionName);
        MainMemory.getMemory().setInFunctionScoope(false);
        MainMemory.getMemory().removeFunctionNameScope(functionName);
    }

    /**
     *
     * @return el string del valor que va devolviendo la funcion con cada ciclo de recursividad
     * @throws Exception si el ciclo no se termina
     */
    @Override
    public String run() throws Exception {
        MainMemory.getMemory().setInFunctionScoope(true);
        MainMemory.getMemory().setFunctionNameScope(id + functionName);
        MainMemory.getMemory().setFunctionName(functionName);
        var tokenHolder = tokens.getFirst();

        if (tokenHolder instanceof RecursiveFunctionHolder) throw new Exception("Infinite loop in funtcion");
        String holder = tokenHolder.run();
        MainMemory.getMemory().setInFunctionScoope(false);
        MainMemory.getMemory().removeFunctionName(functionName);
        MainMemory.getMemory().removeFunctionNameScope(id + functionName);
        return holder;
    }



    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return la funcion que esta guarda
     * @throws Exception si el loop que hizo el usuario nunca se termina
     */
    @Override
    public boolean isAtom() throws Exception {
        MainMemory.getMemory().setInFunctionScoope(true);
        MainMemory.getMemory().setFunctionNameScope(id + functionName);
        MainMemory.getMemory().setFunctionName(functionName);
        var tokenHolder = tokens.getFirst();

        if (tokenHolder instanceof RecursiveFunctionHolder) throw new Exception("Infinite loop in funtcion");
        boolean holder = tokenHolder.isAtom();
        MainMemory.getMemory().setInFunctionScoope(false);
        MainMemory.getMemory().removeFunctionName(functionName);
        MainMemory.getMemory().removeFunctionNameScope(id + functionName);
        return holder;
    }

    @Override
    public void addAsFirst(iStack node) {

    }

    /**
     *
     * @return se retorna el linkedList
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }

    /**
     *
     * @return el nombre de los parametros
     */
    public ArrayList<String> getParameters() {
        return parameters;
    }


    /**
     *
     * @param name el nombre de la funcion
     * @throws Exception si no se puede nombrar a la funcion con ciertos nombres
     */
    private void checkNameIsValid(String name) throws Exception {
        for (String invalid : invalidSyntax){
            if (name.contains(invalid)) throw new Exception(invalid + " es un elemento invalido para crear funciones");
        }
        for (String invalid : invalidNames){
            if (name.equalsIgnoreCase(invalid)) throw new Exception(invalid + " es un nombre invalido para crear funciones");
        }
    }
}
