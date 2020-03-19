
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * NOTA IMPORTANTE:
 *
 * Esta clase es una memoria virtual dentro del programa, donde tenemos el nombre de la variable
 * con su respectivo nodo (list) donde esta guardado
 *
 * Tambien es importante entender que es el Scope, porque de esta manera se logro la recursividad
 * supongamos que es como llamar a la funcion en jefe de loque se esta ejecutando como por ejemplo
 * en la universidad si tengo algun problema primero me refiero con mi auxiliar si el auxiliar no
 * sabe se refiere con el profesor
 */
public class MainMemory {
    private static MainMemory mainMemory = null;


    private boolean isInFunctionScoope = false;
    private LinkedList<String> currentFunctions = new LinkedList<>(),
            currentFunctionName = new LinkedList<>(), functionsToSetValue = new LinkedList<>();
    private HashMap<String, VariableReferenceStack> variables = new HashMap<>();
    private HashMap<String, ArrayList<String>> parameterNames = new HashMap<>();
    private HashMap<String, FunctionReferenceStack> functions = new HashMap<>();

    /**
     * Funcion que se encarga de setear los paramtros a cierta funcion dentro de memoria con su scope correspondiente
     * @param functionName nombre de la funcion con su scope
     * @param parameters parametros a setear solo el nombre
     * **/
    public void setParametersArrayList(String functionName, ArrayList<String> parameters){
        parameterNames.put(functionName, parameters);
    }
    /**
     * Funcion que se encarga de setear los paramtros a cierta funcion dentro de memoria con su scope correspondiente
     * @param functionName nombre de la funcion con su scope
     * @param nodes parametros a setear ya con su valor.
     * **/
    public void setParameters(LinkedList<iStack> nodes, String functionName) throws Exception{

        if (nodes.size() != parameterNames.get(functionName).size()) throw  new Exception("Error al mandar parametros");
        int i = 0;
        for (String s : parameterNames.get(functionName)) {
            if (functionsToSetValue.isEmpty()) variables.get(functionName + s).setValue(nodes.get(i).run());
            else variables.get(functionsToSetValue.getLast() +  s).setValue(nodes.get(i).run());
            i++;
        }
    }

    /**
     * Getter de funciones a las cuales
     **/
    public LinkedList<String> getFunctionsToSetValue() {
        return functionsToSetValue;
    }
    /**
     * Setter de funciones a las cuales
     **/
    public void setFunctionsToSetValue(String functionsToSetValue) {
        this.functionsToSetValue.addLast(functionsToSetValue);
    }
    /**
     * Getter de nombres de parametros
     **/

    public HashMap<String, ArrayList<String>> getParameterNames() {
        return parameterNames;
    }

    /**
     * Funcion encargada de jalar una vairable por medio del nombre de la misma
     * @param nameOfNode nombre de la variable
     **/
    public VariableReferenceStack getVariableNode(String nameOfNode) throws Exception {

        if (!variables.containsKey(nameOfNode)) throw new Exception("No existe dicha variable");
        return variables.get(nameOfNode);
    }

    /**
     * Funcion encargada de jalar una funcion por medio del nombre de la misma
     * @param nameOfNode nombre de la funcion
     **/
    public FunctionReferenceStack getFunctionNode(String nameOfNode) throws Exception {
        if (!functions.containsKey(nameOfNode)) throw new Exception("No existe dicha funcion");
        return functions.get(nameOfNode);
    }
    /** Funcion encargada de ver si existe la variable. **/
    public boolean existsVariable(String nameOfNode) throws Exception { return variables.containsKey(nameOfNode); }
    /** Funcion encargada de ver si existe la funcion. **/
    public boolean existsFunction(String nameOfNode) throws Exception { return functions.containsKey(nameOfNode); }

    /** Funcion encargada de crear la variable y guardarla en memoria.
     * @param name nombre de la variable
     * @param struct estructura de la variable con la que se creara el nodo
     * **/

    public void createVariable(String name, LinkedList<String> struct) throws Exception {
        variables.put(name, new VariableReferenceStack(struct, name));
    }

    /** Funcion encargada de crear el parametro y guardarla en memoria.
     * @param name nombre del parametro con su scope
     * @param normalName nombre normal del parametro
     * **/

    public void createParameter(String name, String normalName) throws Exception {
        variables.put(name, new VariableReferenceStack(name, normalName));
    }


    /**
     *
     * @param name nombre de la funcion
     * @param struct nombre de donde se a guardar la funcion
     * @throws Exception
     */
    public void createFunction(String name, LinkedList<String> struct) throws Exception {
        functions.put(name, new FunctionReferenceStack(struct, name));
    }


    /**
     * es un getter con principio de singleton
     * @return un main memory
     */
    public static MainMemory getMemory(){
        if (mainMemory == null) mainMemory = new MainMemory();
        return mainMemory;
    }

    /** funcion que devuelve si esta en scope o no
     * **/
    public boolean isInFunctionScoope() {
        return isInFunctionScoope;
    }
    /** funcion que devuelve el scope actual
     * **/

    public String getFunctionNameScope(){
        if (currentFunctions.isEmpty()) return "";
        return currentFunctions.getLast();
    }


    /**
     *
     * @param functionNameScope cambia la contexto donde esta la funcion corriendo
     */
    public void setFunctionNameScope(String functionNameScope) {
        if (!currentFunctions.contains(functionNameScope)) currentFunctions.addLast(functionNameScope);
    }
    /** funcion que quita el scope actual
     * **/
    public void removeFunctionNameScope(String functionNameScope) {
        currentFunctions.remove(functionNameScope);
    }


    /**
     *
     * @param functionName el nombre de la funcion que necesitamos ver si esta
     */
    public void setFunctionName(String functionName) {
        if (!currentFunctionName.contains(functionName))  currentFunctionName.addLast(functionName);
    }


    /**
     *
     * @return el ultimo elemento de la lista de funciones
     */
    public String getCurrentFunctionName() {
        if (currentFunctionName.isEmpty()) return "";
        return currentFunctionName.getLast();
    }

    /**
     *
     * @param functionName nombre de la funcion a eliminar
     */
    public void removeFunctionName(String functionName){
        currentFunctionName.remove(functionName);
    }

    public void setInFunctionScoope(boolean inFunctionScoope) {
        isInFunctionScoope = inFunctionScoope;
    }

}
