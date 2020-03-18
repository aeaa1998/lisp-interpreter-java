/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

//cuando tu creas una variable se crea una instancia
//ese ya es la variable
public class VariableReferenceStack extends Stack {
    public String variableName;
    public String normalName;
    private String value;
    public String scope = "global";

    /**
     *
     * @param sublist una lista para agregar instancias
     * @param name nombre de la variable
     * @throws Exception
     */
    public VariableReferenceStack(LinkedList<String> sublist, String name) throws Exception {

        super(sublist);

        if (MainMemory.getMemory().existsVariable(name)) value =  MainMemory.getMemory().getVariableNode(name).value;

        if (!tokens.isEmpty()){
            if(tokens.get(0).rawValue().startsWith("\"") && tokens.get(0).rawValue().endsWith("\""))
                value = "\"" + tokens.get(0).run() + "\"";
            else
                value = tokens.get(0).run();
        }

        variableName = name;

    }


    /**
     *
     * @param name nombre de la funcion
     * @param normalName nombre de la isntancia
     * @throws Exception
     */
    public VariableReferenceStack(String name, String normalName) throws Exception {
        super(new LinkedList<>());
        scope = "scoped";
        value = null;
        variableName = name;
        this.normalName = normalName;

    }

    /**
     *
     * @return el string
     * @throws Exception si hay algun error en la sintaxis
     */
    @Override
    public String run() throws Exception {
        if (scope.equalsIgnoreCase("global")){
            if (tokens.size() > 1) throw new Exception("Error de sintaxis al asignarle valor a la variable " + value);
            if (tokens.isEmpty()) throw new Exception("No value passed for variable " + variableName);
            else if (tokens.get(0).run() == null) throw new Exception("Invalid value");
            return value;
        }else{
            return value;
        }

    }

    public void setValue(String value){
        this.value = value;
    }
    //No se utiliza aca
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return true si es atom
     * @throws Exception si no existe ningun valor o es mayor a 1
     */
    @Override
    public boolean isAtom() throws Exception {
        if (scope.equalsIgnoreCase("global")){
            if (tokens.size() > 1) throw new Exception("Error de sintaxis al asignarle valor a la variable " + value);
            if (tokens.isEmpty()) throw new Exception("No value passed for variable " + variableName);
            else if (tokens.get(0).run() == null) throw new Exception("Invalid value");
            return tokens.get(0).isAtom();
        }else{
            if (Utils.isDigit(value) || value.contains("\"")) return true;
            else return value.split(" ").length == 1;
        }
    }


}
