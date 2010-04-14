#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.widget.tellurium.object

import ${package}.ui.widget.tellurium.TelluriumWidget
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * A dummy widget
 *
 * Date: Jan 21, 2009
 * 
 */
class Dummy extends TelluriumWidget {

    public void defineWidget() {

    }

    public String helloTelluriumWidget(){
        return "Hello Tellurium Widget!"
    }

  public JSONObject toJSON() {
    return buildJSON() {jso ->
      jso.put(UI_TYPE, NAMESPACE + "_" + "Dummy")
    }
  }

}