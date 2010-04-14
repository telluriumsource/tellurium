package org.telluriumsource.ui
/**
 * 
 * Place to hold shared constants
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 16, 2009
 * 
 */

public class Const {
  
    public static final String UID = "uid"
    public static final String NAMESPACE = "namespace"
    public static final String LOCATOR = "locator"
    public static final String CLOCATOR = "clocator"
    public static final String JQLOCATOR = "jqlocator"
    public static final String HEADER = "header"
    public static final String TRAILER = "trailer"
    public static final String TAG = "tag"
    public static final String TEXT = "text"
    public static final String POSITION = "position"
    public static final String TYPE = "type"
    public static final String USE_GROUP_INFO = "group"
    public static final String TRUE = "TRUE"
    public static final String FALSE = "FALSE"
    public static final String CACHEABLE = "cacheable"
    public static final String NO_CACHE_FOR_CHILDREN = "nocacheforchildren"
  
    //direct child of either the header or the parent UI
    public static final String DIRECT = "direct"
    //inside an container element directly, useful for Tables
    public static final String INSIDE = "inside"
  
    public static final String RESPOND_TO_EVENTS = "respond"
    public static final String TITLE = "title"
    public static final String ID = "id"
    public static final String NAME = "name"
    public static final String CLASS = "class"
    public static final String STYLE = "style"

    public static final String NOT_PREFIX = "!"
    public static final String START_PREFIX = "^"
    public static final String END_PREFIX = "\$"
    public static final String ANY_PREFIX = "*"

//    public static final String HAS = ":has"
    public static final String HAS = ":group"
    public static final String MATCH_ALL = "*"
    public static final String SELECTOR_SEPARATOR = ", "
    public static final String ANY_DESCENDANT = "descendant::*"
    public static final String ANY_CHILD = "child::*"

    public static final String CHILD_SEPARATOR = " > "
    public static final String DESCENDANT_SEPARATOR = " "
    public static final String NEXT_SEPARATOR = " + "
    public static final String SIBLING_SEPARATOR = " ~ "
    public static final String ID_SELECTOR_PREFIX = "#"
    public static final String CLASS_SELECTOR_PREFIX = "."
    public static final String CONTAINS_FILTER = ":contains"
    public static final String NOT_FILTER = ":not"
    public static final String SINGLE_QUOTE = "'"
    public static final String SPACE = " "

    //represent it is a partial match i.e., contains
    public static final String CONTAIN_PREFIX = "%%"

    // Selects all children of the current node
    public static final String CHILD = "child"

    // Selects all descendants (children, grandchildren, etc.) of the current node
    public static final String DESCENDANT = "descendant"

    //  Selects all descendants (children, grandchildren, etc.) of the current node
    // and the current node itself
    public static final String DESCENDANT_OR_SELF = "descendant-or-self"

    //  Selects the current node
    public static final String SELF = "self"

    //  Selects everything in the document after the closing tag of the current node
    public static final String FOLLOWING = "following"

    //  Selects all siblings after the current node
    public static final String FOLLOWING_SIBLING = "following-sibling"

    //  Selects the parent of the current node
    public static final String PARENT = "parent"

    //the prefix to start get a relative xpath, it is save to start with the following prefix
    public static final String DESCENDANT_OR_SELF_PATH = "/descendant-or-self::"

    public static final String CHILD_PATH = "/child::"

    public static final String DESCENDANT_PREFIX = "descendant::"

    public static final String CHILD_PREFIX = "child::"

    public static final String SEPARATOR = "/"

    public static final PIPE_TYPE = 1
    public static final CSV_TYPE = 2
    public static final EXCEL_TYPE = 3
}