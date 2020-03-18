import java.util.LinkedList;

/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

//Esta es una clase que se encarga de verificar si los valores booleanos
//son booleanos y ver si estan bien escritos
public class CondStack extends Stack {
    /**
     *
     * @param list una lista con los elementos que en teoria son condicionales
     * @throws Exception si el condicional no fue bien cerrado, si no existe y si es distinto 2
     */
    public CondStack(LinkedList<String> list) throws Exception {
        super(list);
        if (tokens.size() == 0) throw new Exception("Error en cond");
        for (iStack token: tokens){
            if (!(token instanceof ContainerStack)) throw new Exception("Sintax error in cond probably missing parentesis wrapping assert that sintaxis is (test action)");
            if (token.getTokens().isEmpty()) throw new Exception("Error en cond");
            else if (token.getTokens().size() != 2) throw new Exception("Error en cond");
            var firstToken = token.getTokens().get(0);

        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public String run() throws Exception {
        for (iStack container : tokens){
            recurse(container);
            var firstToken = container.getTokens().get(0);
            if (!firstToken.rawValue().equalsIgnoreCase("T") && !firstToken.rawValue().equalsIgnoreCase("nil"))
                throw new Exception("Valor pasado a COND no es booleano " + firstToken.rawValue());

            if (firstToken.rawValue().equalsIgnoreCase("T"))
                return container.getTokens().get(1).run();
        }
        return "NIL";
    }


    /**
     *
     * @param list un elemento del tipo interface
     * @return la lista con los cambios hechos una a terminado el ciclo de la funcion
     * asi como tambien el de la recursividad
     * @throws Exception
     */
    public iStack recurse(iStack list) throws Exception{
        for (int i = 0; i < list.getTokens().size(); i++) {
            if (list.getTokens().get(i).getTokens().size() > 1){
                list.getTokens().set(i, recurse(list.getTokens().get(i)));
            }

            if (list.getTokens().get(i) instanceof VariableReferenceStack){
                var holder = (VariableReferenceStack)(list.getTokens().get(i));
                if (!holder.scope.equalsIgnoreCase("global")){
                    holder = MainMemory.getMemory().getVariableNode(MainMemory.getMemory().getFunctionNameScope() + holder.normalName);
                }
                list.getTokens().set(0, holder);
            }

        }
        return list;
    }

    /**
     *
     * @return un string que es con el que se trabaja la recursividad
     * @throws Exception por interface
     */
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return false puesto que el elemento no es atom
     * @throws Exception si es atom
     */
    @Override
    public boolean isAtom() throws Exception {
        for (iStack container : tokens){
            var firstToken = container.getTokens().get(0);
            if (firstToken.rawValue().equalsIgnoreCase("T")) return container.getTokens().get(1).isAtom();
        }
        throw new Exception("No se puede verificar si dicho valor es atom");
    }
}
