
/**
 * @author Augusto Alonso 181085, Pablo Mendez 19195, Dvid Cuellar 18382
 * @Title proyecto LISP
 */
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JsReader {
    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("js");
    private static JsReader jsReader = null;

    public static JsReader getJs() {
        if (jsReader == null) jsReader = new JsReader();
        return jsReader;
    }

    public ScriptEngine getEngine(){
        return engine;
    }
}
