package org.telluriumsource.ui.builder


import org.telluriumsource.ui.object.SimpleMenu

/**
 *
 * Builder for the simple menu
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 6, 2008
 *
 */
class SimpleMenuBuilder extends UiObjectBuilder{
    static final String ITEMS = "items"

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, SimpleMenu.TAG)
        SimpleMenu menu = this.internBuild(new SimpleMenu(), map, df)
        Map<String, String> items = map.get(ITEMS)
        if(items != null && items.size() > 0){
            menu.addMenuItems(items)
        }

        return menu
    }

}