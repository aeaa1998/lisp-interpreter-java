
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;
import java.util.List;

public class Utils {
    public static LinkedList<String> returnListAsLinked(List<String> list){
        return new LinkedList<String>(){{ addAll(list); }};
    }


    public static boolean isDigit(String word){
        try{
            Double i = Double.valueOf(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public static boolean isNonNegativeInteger(String word){
        try{
            Integer i = Integer.valueOf(word);
            return i >= 0;
        }catch (Exception e){
            return false;
        }
    }

    public static LinkedList<iStack> setNodes(LinkedList<String> list, LinkedList<iStack> tokens) throws Exception {
        int parentesisCounter = 0; int relativePosition = 0;
        boolean quoted = false;

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String holder = list.get(i);
            if ((s.equalsIgnoreCase("'(") && !quoted)) {
                if (parentesisCounter == 0) quoted = true;
                s = s.substring(s.length() - 1, s.length());
            }
            if (s.equalsIgnoreCase(Constants.OPEN_LIST) || s.equalsIgnoreCase(Constants.CLOSE_LIST) || parentesisCounter > 0) {
                if (s.equalsIgnoreCase(Constants.OPEN_LIST)) {
                    if (parentesisCounter == 0) relativePosition = i;
                    parentesisCounter++;
                } else if (list.get(i).equalsIgnoreCase(Constants.CLOSE_LIST)) parentesisCounter--;
                if (parentesisCounter == 0) {
                    var linkedList = Utils.returnListAsLinked(list.subList(relativePosition + 1, i));
                    iStack newNode;
                    if (quoted) {
                        if (linkedList.isEmpty()) newNode = new NilStack(linkedList);
                        else newNode = StackFactory.getFactory().createObject(linkedList, true);
                        quoted = false;
                    } else {
                        newNode = StackFactory.getFactory().createNode(linkedList);
                    }
                    tokens.add(newNode);
                    relativePosition = i + 1;
                }
            } else {
                var newNode = StackFactory.getFactory().createSimpleNode(s);
                tokens.add(newNode);
            }
        }
        if (parentesisCounter > 0) {
            throw new Exception("No se cerro parentesis");
        }
        if (parentesisCounter < 0) {
            throw new Exception("Error sintaxis hay un ) de mas");
        }
        return tokens;
    }
}
