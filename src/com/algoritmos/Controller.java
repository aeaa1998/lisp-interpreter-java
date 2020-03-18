package com.algoritmos;
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import java.util.ArrayList;

public class Controller {
    private Interpreter interpreter = new Interpreter();
    private ArrayList<String> menu = new ArrayList<>(){{add("Probar Secuencia fibonnacci");
    add("Iniciar Consola (Se ira ingresando el codigo por  statements)");}};
    private String fibonnacciString = "" +
            "(defun lol (x y) (- x y))\n" +
            "(setq x \"esta variable guarda un string x y la ponemos para mostrar el scope entre parametros y variables de igual nombre no colicionan\")\n" +
            "(defun getList () 1)\n" +
            "(defun fibonacci (x)\n" +
            "    (cond((< x 1) 0) \n" +
            "         ((< x 2) 1)\n" +
            "         ( t (+ (fibonacci (lol x 1)) (fibonacci (- x 2))))))\n" +
            "(print (fibonacci (fibonacci 6)))(print (fibonacci 8))\n" +
            "(print x)\n" +
            "(print 'QUOTEADO)\n" +
            "(quote 'QUOTEADOPEROSINSINGLEQUOTE)\n" +
            "(setq milistaSinQuote (list 1 2 3))\n" +
            "(setq milistaConQuote '(a 1lol setq))\n" +
            "(print \"Lista con cuote\")\n" +
            "(print milistaConQuote)\n" +
            "(print \"Lista sin quote\")\n" +
            "(print milistaSinQuote)\n" +
            "(print \"Aca mostraremos que las funciones pueden retornar listas y ATOM es capaz de verlo\")\n" +
            "(print (ATOM (getList)))" +
            "(print \"Imprimira NIL ya que en lisp las listas vacias son nil\")" +
            "(print \"Puede imprimir listas quoteadas y anidadas simular su comportamiento extraño\")\n" +
            "(print '( '(1 2 porfavor Esto es Un CiEn) 2 3 '( '(1 test fun) 4 ) ) ) \n" +
            "(print \"Puede imprimir y generar listas anidadas y normales\")\n" +
            "(print (list 1 2 345 (list 1 2 (list 1 0 0 0) 0) 0)\n)" +
            "(print ())\n"
            ;
    /**
     * Funcion que incia el programa
     * **/
    public void start() throws Exception {
        switch (View.getView().selectOptions(menu, "Ingrese el numero de opcion que desea.", "Ingrese una opcion valida")){
            case 0:
                View.getView().print("Codigo a probar:\n" + fibonnacciString);
                interpreter.parse(fibonnacciString);
                break;
            case 1:
                View.getView().print("Ingrese el codigo a continuacion:");
                while (true){ interpreter.parse(View.getView().input("")); }

        }
    }
}
