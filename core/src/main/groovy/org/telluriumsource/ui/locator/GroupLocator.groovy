package org.telluriumsource.ui.locator
/**
 *    Group locator and it will use all descendants' information to help locating itself
 *    Note: only Container and its child classes can have GroupLocator. For Ui object that does
 *    not hold child objects, it does not make sense to use the GroupLocator
 *
 *    XXX: This class is not really used at this point. Use CompositeLocator instead  
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class GroupLocator {
    String header
    String tag
    Map<String, String> attributes = [:]
}