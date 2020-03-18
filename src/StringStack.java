/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;

public class StringStack extends PrimitiveStack {

    public StringStack(LinkedList<String> list) {
        super(list);
        predicates.set(0, predicates.get(0).replace(Constants.STRING_REFACTOR, " "));
    }


}
