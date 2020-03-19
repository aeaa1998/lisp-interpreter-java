/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.ArrayList;

//Es una clase donde se encuentran todas las cosas para comparar y clasificar objetos
public class Constants {
    /**
     * Se declaran variables
     */
    public static final String OPEN_LIST = "(";
    public static final String OPEN_SECIAL_LIST = "'(";
    public static final String CLOSE_LIST  = ")";
    //Esta variable es importante para que no se repitan
    public static final String STRING_REFACTOR  = "-ptsd-juice-world";
    /**
     * se agregan operadores al arraylist
     */
    public static final ArrayList<String> OPERATORS_TOKENS  = new ArrayList<String>(){{
        add("+");
        add("-");
        add("/");
        add("*");
    }};
    /**
     * Se agregan operadores logicos al arraylist
     */
    public static final ArrayList<String> LOGICAL_TOKENS  = new ArrayList<String>(){{
        add("EQUAL");
        add("ATOM");
        add("<");
        add("=");
        add("<=");
        add(">");
        add(">=");
        add("NOT");
    }};
    //Se agregan booleanos de lisp al arraylist
    public static final ArrayList<String> BOOLEAN_TOKENS  = new ArrayList<String>(){{
        add("t");
        add("T");
        add("nil");
        add("NIL");
    }};
    //Se agregan comparadores y funciones que tiene lisp
    public static final ArrayList<String> LISP_UTILS  = new ArrayList<String>(){{
        add("print");
        add("defun");
        add("quote");
        add("list");
        add("setq");
        add("nth");
        add("set");
        add("cond");
        add("if");
        add("nth");
        add("elt");
        add("concatenate");
    }};
    //valores que utiliza lisp
    public static final ArrayList<String> LISP_RESTRICTED_VALUES  = new ArrayList<String>(){{
        add("nil"); add("F"); add("T");
    }};
    //Se agregan numeros al arraylist
    public static final ArrayList<String> NUMBERS  = new ArrayList<String>(){{
        add("0");add("1");add("2");add("3");add("4");add("5");add("6");add("7");add("8");add("9");
    }};

}
