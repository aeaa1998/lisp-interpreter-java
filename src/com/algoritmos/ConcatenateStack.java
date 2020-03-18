package com.algoritmos;
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
        if(!tokens.get(0).rawValue().equalsIgnoreCase("string") && !tokens.get(0).rawValue().equalsIgnoreCase("list"))
            throw new Exception("La secuencia " + tokens.get(0).rawValue() + " no existe.");
    }

    //Hace que los elementos de una lista se concatenen
    //va ingresando a la lista y revisando elemento por elemento hasta que queda junto todo

    @Override
    public String run() throws Exception {
        String string = "";
        //Si es un string entonces lo agregamos al string builder
        if (tokens.get(0).run().equalsIgnoreCase("string")){
            String holder;
            StringBuffer stringBuilder = new StringBuffer();//Es un string que puede crecer
            for (int i = 1; i < tokens.size(); i++) {

                holder = tokens.get(i).rawValue();
                tokens.get(i).run();
                if (!holder.startsWith("\"") || !holder.endsWith("\""))
                    throw new Exception(holder + " no es un string");

                stringBuilder.append(holder.substring(1, holder.length() - 1));
            }

            string = "\"" + stringBuilder.toString() + "\"";

        } else if (tokens.get(0).run().equalsIgnoreCase("list")){
            StringBuffer stringBuilder = new StringBuffer();
            String holder;
            for (int i = 1; i < tokens.size(); i++) {
                holder = tokens.get(i).rawValue();
                tokens.get(i).run();

                if (holder.startsWith("\"") && holder.endsWith("\"")) {
                    for (char caracter : holder.substring(1, holder.length() - 1).toCharArray()) {
                        stringBuilder.append("\\#" + caracter + " ");
                    }
                } else if (holder.startsWith("(") && holder.endsWith(")")) {
                    String[] holder2 = holder.substring(1, holder.length() - 1).trim().split(" ");
                    for (String cadenaSimple : holder2)
                        stringBuilder.append(cadenaSimple + " ");

                }
            }
            string = "( " + stringBuilder.toString() + ")";

        } else
            throw new Exception("Elt debe recibir solo secuencias.");

        return string;
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
