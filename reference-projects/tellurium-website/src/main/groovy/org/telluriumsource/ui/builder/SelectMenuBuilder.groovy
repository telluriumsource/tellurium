package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.SelectMenu

/**
 * 
 * @author   Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 18, 2010
 * 
 */
class SelectMenuBuilder extends UiObjectBuilder{
    static final String ITEMS = "items"
    static final String TITLE = "title"

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, SelectMenu.TAG)
        SelectMenu menu = this.internBuild(new SelectMenu(), map, df)
        Map<String, String> items = map.get(ITEMS)
        if(items != null && items.size() > 0){
           menu.addMenuItems(items)
        }

        menu.addTitle(map.get(TITLE))

        return menu
    }
}
