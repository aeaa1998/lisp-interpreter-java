/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

public class IfStack extends Stack {
    public IfStack(LinkedList<String> list) throws Exception {
        super(list);
        if (tokens.size() != 2 && tokens.size() != 3) throw new Exception("Error en if");
    }

    /**
     * Devuelve el valor del primer argumento si la condición es cierta, el segundo si es falsa o null si no existe
     * @return Primer arguemento, Null o segundo argumento
     * @throws Exception
     */
    @Override
    public String run() throws Exception {

        if(!tokens.get(0).rawValue().equalsIgnoreCase("nil"))
            return tokens.get(1).run();

         else if (tokens.size() == 3)
            return  tokens.get(2).run();

         else if (tokens.size() == 2)
             return "nil";

         throw new Exception("IF solo acepta dos o tres parametros");

    }

    /**
     * Devuelve el valor del primer argumento si la condición es cierta, el segundo si es falsa o null si no existe
     * @return Primer arguemento, Null o segundo argumento
     * @throws Exception
     */
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     * Calcula que valor devuelve el if y ve si el valor es un atom
     * @return T o Nil, dependiendo de los valores de if
     * @throws Exception
     */
    @Override
    public boolean isAtom() throws Exception {
        if(!tokens.get(0).rawValue().equalsIgnoreCase("nil")){
            return tokens.get(1).isAtom();

        } else if (tokens.size() == 3)
            return  tokens.get(2).isAtom();

        else if(tokens.size() == 2)
            return true;

        throw new Exception("IF solo acepta dos o tres parametros");

    }
}
