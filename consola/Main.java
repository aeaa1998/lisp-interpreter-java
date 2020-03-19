

/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */

public class Main {

    public static void main(String[] args) throws Exception {
	// Se inicia el programa
        Controller controller = new Controller();
        controller.start();
    }
}
/**
 * Nota importante:
 *
 * Debido a que java es un lenguaje donde las funciones no son ciudadanos de primer orden
 * uno no puedo guardar funciones de referencia en variables a diferencia de lenguajes como kotlin
 * con esto tampoco se pueden delegar comportamientos de clases
 * En pocas palabras las funciones de primer orden permite tratar a las funciones como cualquier
 * objeto o variable
 * para mejor entendimiento del tema se recomienda un video y un foro:
 * https://www.youtube.com/watch?v=kr0mpwqttM0
 * https://stackoverflow.com/questions/5178068/what-is-a-first-class-citizen-function
 *
 * Para resolver este problema la clase recursionholder se encarga de simular la comportamiento de otra clase
 * sin la necesidad de tener funciones como ciudadanos de primer orden
 */