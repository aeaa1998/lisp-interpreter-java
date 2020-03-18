/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

import java.util.LinkedList;

//Basicamente esta clase se encarga de ver que elementos se pueden concatenar con otros
public class ConcatenateStack extends Stack {
    /**
     *
     * @param list recibe una lista de strings con las instrucciones
     * @throws Exception por la interface
     */
    public ConcatenateStack(LinkedList<String> list) throws Exception {
        super(list);//Hace referencia a la clase node
        //Recorre la lista tokends
        for (int i = 0; i < tokens.size(); i++) {
            //Si el elemento es parte del nodo referencia entonces hacemos un set
            if (tokens.get(i) instanceof VariableReferenceStack)
                tokens.set(i, tokens.get(i).getTokens().get(0));
        }
        //si es un string entonces miramos que sea parte del array donde tenemos los strings
        //sino esta ahi entonces no podemos concatenar
        if (tokens.get(0).rawValue().equalsIgnoreCase("string")) {
            for (int i = 1; i < tokens.size(); i++)
                if (!(tokens.get(i) instanceof StringStack))
                //if (!(tokens.get(i) instanceof StringStack))

                    throw new Exception("Solo se pueden concatenar strings a strings");
        //Se verifica si esta en algun arraylist de string node o object node
        } else if (tokens.get(0).rawValue().equalsIgnoreCase("list")) {
            for (int i = 1; i < tokens.size(); i++)
                if (!(tokens.get(i) instanceof StringStack || tokens.get(i) instanceof ObjectStack))
                    throw new Exception(tokens.get(0).rawValue() + " no es una secuencia aceptada.");

        } else {
            throw new Exception("La secuencia " + tokens.get(0).rawValue() + " no existe.");
        }



    }

    //Hace que los elementos de una lista se concatenen
    //va ingresando a la lista y revisando elemento por elemento hasta que queda junto todo

    @Override
    public String run() throws Exception {
        String string = "";
        //Si es un string entonces lo agregamos al string builder
        if (tokens.get(0).run().equalsIgnoreCase("string")){
            StringBuffer stringBuilder = new StringBuffer();//Es un string que puede crecer
            for (int i = 1; i < tokens.size(); i++)
                    stringBuilder.append(tokens.get(i).run());

            string = "\"" + stringBuilder.toString().replace("\"", "") + "\"";

        } else if (tokens.get(0).run().equalsIgnoreCase("list")){
            StringBuffer stringBuilder = new StringBuffer();

            for (int i = 1; i < tokens.size(); i++){

                if (tokens.get(i) instanceof StringStack){
                    String temp = tokens.get(i).run().replace("\"", "");
                    stringBuilder.append(" ");
                    for (char caracter: temp.toCharArray()) {
                        stringBuilder.append("#\\");
                        stringBuilder.append(caracter);
                        stringBuilder.append(" ");
                    }
                }

                if (tokens.get(i) instanceof ObjectStack) {
                    stringBuilder.append(
                            tokens.get(i).run().
                                    replace("(", "").
                                    replace(")", ""));
                }
            }
            //Es un ciclo para recorre el string builder y eliminar los espacios " "
            for (int i = 1; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == ' ' && stringBuilder.charAt(i - 1) == ' ')
                    stringBuilder.deleteCharAt(i);
            }
            //se agregan parentesis para respetar sintaxis
            string = "(" + stringBuilder.toString() + ")";
        }

        return string.toString();
    }

    /**
     *
     * @return un string para seguir concatenando
     * @throws Exception por interface
     */
    @Override
    public String rawValue() throws Exception {
        return run();
    }

    /**
     *
     * @return si es o no un string
     * @throws Exception por la interface
     */
    @Override
    public boolean isAtom() throws Exception {
        rawValue();
        return tokens.get(0).rawValue().equalsIgnoreCase("string");
    }
}
