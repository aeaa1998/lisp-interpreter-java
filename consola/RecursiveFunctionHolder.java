
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

public class RecursiveFunctionHolder implements iStack {
    private String functionName;
    protected LinkedList<iStack> tokens;
    public RecursiveFunctionHolder(String name) {

        functionName = name;
    }
    public RecursiveFunctionHolder(String name, LinkedList<String> list) throws Exception{

        functionName = name;
        tokens = Utils.setNodes(list, new LinkedList<>());

    }

    @Override
    public String run() throws Exception {
        var functionReference = MainMemory.getMemory().getFunctionNode(functionName);

        var holder = functionReference.returnNewFunc(functionName, functionReference.tokens);
        if (tokens != null){
            MainMemory.getMemory().setFunctionsToSetValue(holder.id + functionName);
            MainMemory.getMemory().setInFunctionScoope(true);
            MainMemory.getMemory().setFunctionName(functionName);
            MainMemory.getMemory().setParameters(tokens, functionName);

            MainMemory.getMemory().setFunctionNameScope(holder.id + functionName);

            MainMemory.getMemory().getFunctionsToSetValue().remove(holder.id + functionName);

        }
//        functionReference.clearParameters();
//        functionReference.setParameters(tokens);
        return holder.run();
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
