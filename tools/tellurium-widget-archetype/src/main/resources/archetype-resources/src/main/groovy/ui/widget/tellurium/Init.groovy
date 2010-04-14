#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.widget.tellurium

import ${package}.ui.widget.tellurium.builder.DummyBuilder
import org.telluriumsource.ui.widget.WidgetBootstrap
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry

/**
 * The bootstrap class for the Dojo widget module
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 * 
 */
class Init implements WidgetBootstrap{

    public void loadWidget(UiObjectBuilderRegistry uiObjectBuilderRegistry) {
        if(uiObjectBuilderRegistry != null){
          uiObjectBuilderRegistry.registerBuilder(getFullName("Dummy"), new DummyBuilder())
        }
    }

    protected String getFullName(String name){
        return TelluriumWidget.NAMESPACE + TelluriumWidget.NAMESPACE_SUFFIX + name
    }
}