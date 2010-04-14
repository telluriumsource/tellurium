package org.telluriumsource.ui.locator
/**
 * This one is depreciated since Groovy has GString that you can use directly.
 * Please use GString like:
 *
 *     "label=${target}"
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class PlaceHolderLocator {
    String template
    String[] attributes
}