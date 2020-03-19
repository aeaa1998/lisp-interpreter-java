
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.LinkedList;


public class PrintStack extends Stack {

public PrintStack(LinkedList<String> list) throws Exception {
    super(list);
}

/**
 *
 * @return esto podria sere un void pero retornamos null por la interface
 * @throws Exception el parametro puede ser invalido y por eso no imprime
 */
@Override
public String run() throws Exception {
    if (tokens.size() == 0) throw new Exception("No paso parametro a imprimir");
    if (tokens.get(0).run() == null) throw new Exception("Cannot print this value passed");
    View.getView().print(tokens.get(0).run());
    return null;
}
//Estos metodos se aseguran de no retornar un valor
@Override
public String rawValue() throws Exception {
    return null;
}

@Override
public boolean isAtom() throws Exception {
    return false;
}


}
