
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;


public class HolderStack implements iStack {
    //Declaramos variables
    private LinkedList<String> listHolder;
    private String value, rawValue, functionName;
    private boolean hasBuilt = false;
    //Creamos constructor
    public HolderStack(LinkedList<String> listHolder, String functionName) {

        this.listHolder = listHolder;
        this.functionName = functionName;

    }

    /**
     *
     * @return un string con una sustitucion de valores o si esta vacio
     * lo llena con lo que hay en el build
     * @throws Exception
     */
    @Override
    public String run() throws Exception {
        build();

        if (rawValue == null) return rawValue;
        return rawValue.replace("\"", "");
    }

    /**
     * Construye la funcion que esta dentro de la otra funcion
     * @throws Exception
     */
    private void build() throws Exception{
        var nodesHolder = new LinkedList<iStack>(Utils.setNodes(listHolder, new LinkedList<>()));
        var fun = MainMemory.getMemory().getFunctionNode(functionName);
        MainMemory.getMemory().setFunctionsToSetValue(fun.id + functionName);
        MainMemory.getMemory().setParameters(nodesHolder, functionName);
        rawValue = fun.rawValue();
        MainMemory.getMemory().getFunctionsToSetValue().remove(fun.id + functionName);

//        value = fun.run();
        hasBuilt = true;

    }
    //Un string
    @Override
    public String rawValue() throws Exception {
        build();
        return rawValue;
    }

    /**
     *
     * @return true si es un espacio " "
     * @throws Exception si hay un error de sintaxis
     */
    @Override
    public boolean isAtom() throws Exception {
        build();
        if (rawValue == null) throw new Exception("Error no se ve si es un elemento nulo");
        if (String.valueOf(rawValue.charAt(0)).equalsIgnoreCase("\""))
            return true;
        return !rawValue.contains("( ");
    }

    //No hace nada aca
    @Override
    public void addAsFirst(iStack node) {

    }

    /**
     *
     * @return getter del list de tokens
     */
    @Override
    public LinkedList<iStack> getTokens() {
        return tokens;
    }
}
