package org.telluriumsource.ui.widget

import org.telluriumsource.ui.builder.UiObjectBuilderRegistry

/**
 * Bootstrap interface for all widget modules, i.e., the widget bootstrap class must implement this interface
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 * 
 */
interface WidgetBootstrap {

    public void loadWidget(UiObjectBuilderRegistry registry)
}