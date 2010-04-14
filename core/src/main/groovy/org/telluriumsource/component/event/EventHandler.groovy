package org.telluriumsource.component.event

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.component.event.Event
import org.telluriumsource.component.event.EventSorter
import org.telluriumsource.exception.ElementNotPresentException
import org.telluriumsource.util.Helper
import java.awt.event.KeyEvent

import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.dsl.WorkflowContext;


class EventHandler implements Configurable{

	protected IResourceBundle i18nBundle

    public static final String RETURN_KEY= "BSBS13"
	public static final int ACTION_WAIT_TIME = 50

    BundleProcessor cbp  = BundleProcessor.instance

    private EventSorter alg = new EventSorter()

    private boolean checkElement = false
    private boolean extraEvent = false

    EventHandler(){
	  i18nBundle = Environment.instance.myResourceBundle()

	}
    public void mustCheckElement(){
        this.checkElement = true
    }

    public void notCheckElement(){
        this.checkElement = false
    }

    public void useExtraEvent(){
        this.extraEvent = true
    }

    public void noExtraEvent(){
        this.extraEvent = false
    }

    protected void processingEvent(WorkflowContext context, String locator, Event event){
        switch (event){
            case Event.BLUR:
                cbp.fireEvent(context, locator, "blur")
                break
            case Event.FOCUS:
                cbp.fireEvent(context, locator, "focus")
                break
            case Event.MOUSEOUT:
                cbp.mouseOut(context, locator)
                break
            case Event.MOUSEOVER:
                cbp.mouseOver(context, locator)
                break
            case Event.MOUSEDOWN:
                cbp.mouseDown(context, locator)
                break
            case Event.MOUSEUP:
                cbp.mouseUp(context, locator)
                break
            default:
                println i18nBundle.getMessage("EventHandler.UnknownEvent" , event.toString())
        }
    }

    protected void processEvents(WorkflowContext context, String locator, String[] events, String[] defaultEvents, Closure action){
        checkElement(context, locator)

        Event[] evns = alg.sort(events, defaultEvents)
        //For event processing, it is ok to use bundle
        context.makeBundlingable()

        evns.each {Event event ->
          if (event == Event.ACTION)
            action()
          else
            processingEvent(context, locator, event)
        }
    }

    protected void checkElement(WorkflowContext context, String locator){
        context.notBundlingable()

		if(checkElement && (!cbp.isElementPresent(context, locator))){
			checkAndWaitForElementPresent(context, locator, ACTION_WAIT_TIME)
		}
    }

    def mouseOver(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = ["focus"]

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseOver(context, locator)
        }
	}

    def mouseOut(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = ["focus"]

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseOut(context, locator)
        }
	}

   def dragAndDrop(WorkflowContext context, String locator, String movementsString, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = []

        processEvents(context, locator, events, defaultEvents){
            cbp.dragAndDrop(context, locator, movementsString)
        }
	}

   def dragAndDropToObject(WorkflowContext context, String srcLocator, String targetLocator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = []

        processEvents(context, srcLocator, events, defaultEvents){
            cbp.dragAndDropToObject(context, srcLocator, targetLocator)
        }
	}

	def click(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(context, locator, events, defaultEvents){
           cbp.click(context, locator)
        }
	}

	def doubleClick(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(context, locator, events, defaultEvents){
           cbp.doubleClick(context, locator)
        }
	}

	def clickAt(WorkflowContext context, String locator, String coordination, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(context, locator, events, defaultEvents){
           cbp.clickAt(context, locator, coordination)
        }
	}

	def check(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(context, locator, events, defaultEvents){
           cbp.check(context, locator)
        }
	}

	def uncheck(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(context, locator, events, defaultEvents){
           cbp.uncheck(context, locator)
        }
    }

    def altKeyUp(WorkflowContext context) {
        cbp.altKeyUp(context)
	}
    def altKeyDown(WorkflowContext context) {
        cbp.altKeyDown(context)
	}
    def shiftKeyUp(WorkflowContext context) {
         cbp.shiftKeyUp(context)
	}
    def shiftKeyDown(WorkflowContext context) {
         cbp.shiftKeyDown(context)
	}
    def ctrlKeyUp(WorkflowContext context) {
        cbp.ctrlKeyUp(context)
	}
    def ctrlKeyDown(WorkflowContext context) {
        cbp.ctrlKeyDown(context)
	}
    def metaKeyUp(WorkflowContext context) {
        cbp.metaKeyUp(context)
	}
    def metaKeyDown(WorkflowContext context) {
        cbp.metaKeyDown(context)
	}


	def type(WorkflowContext context, String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(context, locator, events, defaultEvents){
           if(includeKeyEvents(events)){
               processKeyEvent(context, locator, input, events)
           }else{
               cbp.type(context, locator, input)
           }
        }
    }

    def keyType(WorkflowContext context, String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(context, locator, events, defaultEvents){
           simulateKeyType(context, locator, input)
        }
	}

	def typeAndReturn(WorkflowContext context, String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(context, locator, events, defaultEvents){
            cbp.type(context, locator, input)
            cbp.keyUp(context, locator,  "\\13")
        }
	}

    def clearText(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(context, locator, events, defaultEvents){
           cbp.type(context, locator, "")
        }
	}

    def simulateKeyType(WorkflowContext context, String locator, String input){

        if(input == null || input.length() < 1){
    		cbp.type(context, locator, "")
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				cbp.keyUp(context, locator,  "\\13")
                }else if(".".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
//                    cbp.keyDownNative(keycode)
//                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else if("(".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
//                    cbp.keyDownNative(keycode)
//                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else if("y".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_Y)
//                    cbp.keyDownNative(keycode)
//                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else{
    				cbp.keyDown(context, locator, key)
    				cbp.keyPress(context, locator, key)
    				cbp.keyUp(context, locator, key)
    			}
    			Helper.pause(15)
    		}
    	}
    }

/*
    def simulateKeyType(String locator, String input){

        if(input == null || input.length() < 1){
    		cbp.type(locator, "")
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				cbp.keyUp(locator,  "\\13")
                }else if(".".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
//                    cbp.keyDownNative(keycode)
                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                }else if("(".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
//                    cbp.keyDownNative(keycode)
                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                }else if("y".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_Y)
//                    cbp.keyDownNative(keycode)
                    cbp.keyPressNative(keycode)
//                    cbp.keyUpNative(keycode)
                }else{
    				cbp.keyDown(locator, key)
    				cbp.keyPress(locator, key)
    				cbp.keyUp(locator, key)
    			}
    			Helper.pause(15)
    		}
    	}
    }
    */

/*
    def processKeyEvent(String locator, String input, String[] events){
        boolean hasKeyDown = includeKeyDown(events)
        boolean hasKeyPress = includeKeyPress(events)
        boolean hasKeyUp = includeKeyUp(events)

        if(input == null || input.length() < 1){
    		cbp.type(locator, "")
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				cbp.keyUp(locator,  "\\13")
                }else if(".".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
                    if(hasKeyDown)
                      cbp.keyDownNative(keycode)

                    cbp.keyPressNative(keycode)

                    if(hasKeyUp)
                      cbp.keyUpNative(keycode)
                }else if("(".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
                    if(hasKeyDown)
                      cbp.keyDownNative(keycode)

                    cbp.keyPressNative(keycode)

                    if(hasKeyUp)
                      cbp.keyUpNative(keycode)
                }else if("y".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_Y)
                    if(hasKeyDown)
                      cbp.keyDownNative(keycode)

                    cbp.keyPressNative(keycode)

                    if(hasKeyUp)
                      cbp.keyUpNative(keycode)
                }else{
                    if(hasKeyDown)
                        cbp.keyDown(locator, key)

                    cbp.keyPress(locator, key)

                    if(hasKeyUp)
                        cbp.keyUp(locator, key)
    			}
    		}
    	}
    }
*/


    def processKeyEvent(WorkflowContext context, String locator, String input, String[] events){
        boolean hasKeyDown = includeKeyDown(events)
        boolean hasKeyPress = includeKeyPress(events)
        boolean hasKeyUp = includeKeyUp(events)

        if(input == null || input.length() < 1){
    		cbp.type(context, locator, "")
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				cbp.keyUp(context, locator,  "\\13")
                }else if(".".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
//                    if(hasKeyDown)
//                      cbp.keyDownNative(keycode)
//
//                    cbp.keyPressNative(keycode)
//
//                    if(hasKeyUp)
//                      cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else if("(".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
//                    if(hasKeyDown)
//                      cbp.keyDownNative(keycode)
//
//                    cbp.keyPressNative(keycode)
//
//                    if(hasKeyUp)
//                      cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else if("y".equals(key)){
//                    String keycode = Integer.toString(KeyEvent.VK_Y)
//                    if(hasKeyDown)
//                      cbp.keyDownNative(keycode)
//
//                    cbp.keyPressNative(keycode)
//
//                    if(hasKeyUp)
//                      cbp.keyUpNative(keycode)
                    cbp.typeKey(context, locator, key)
                }else{
                    if(hasKeyDown)
                        cbp.keyDown(context, locator, key)

                    cbp.keyPress(context, locator, key)

                    if(hasKeyUp)
                        cbp.keyUp(context, locator, key)
    			}
    		}
    	}
    }

    def select(WorkflowContext context, String locator, String target, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(context, locator, events, defaultEvents){
	        cbp.click(context, locator)
			checkAndWaitForElementPresent(context, locator, ACTION_WAIT_TIME)
			cbp.select(context, locator, target)
        }
    }

    def addSelection(WorkflowContext context, String locator, String optionLocator){
        checkElement(context, locator)

        if(extraEvent){
          cbp.fireEvent(context, locator, "focus")
        }

        cbp.addSelection(context, locator, optionLocator)
    }

    def removeSelection(WorkflowContext context, String locator,String optionLocator){
        checkElement(context, locator)

        if (extraEvent) {
          cbp.fireEvent(context, locator, "focus")
        }
        cbp.removeSelection(context, locator, optionLocator)
    }

    def removeAllSelections(WorkflowContext context, String locator){
        checkElement(context, locator)

        if (extraEvent) {
          cbp.fireEvent(context, locator, "focus")
        }
        cbp.removeAllSelections(context, locator)
    }

    def submit(WorkflowContext context, String locator){
        checkElement(context, locator)

        if (extraEvent) {
          cbp.fireEvent(context, locator, "focus")
        }
        cbp.submit(context, locator)
    }

    void openWindow(WorkflowContext context, String url, String windowID){

        cbp.openWindow(context, url, windowID)
    }

    void selectWindow(WorkflowContext context, String windowID){

        cbp.selectWindow(context, windowID)
    }

    void windowFocus(WorkflowContext context){

        cbp.windowFocus(context)
    }

    def closeWindow(WorkflowContext context, String windowID){
        cbp.selectWindow(context, windowID)
        cbp.close(context)
    }

    void windowMaximize(WorkflowContext context){
        cbp.windowMaximize(context)
    }

    void selectFrame(WorkflowContext context, String locator){

        cbp.selectFrame(context, locator)
    }

    def private boolean checkAndWaitForElementPresent(WorkflowContext context, String locator, int timeout){

		boolean result = false

        for (int second = 0; second < timeout; second+=500) {
            try {
            	if (cbp.isElementPresent(context, locator)){
            		result = true
            		break
            		}
            }catch (Exception e){

            }

            Helper.pause(500)
        }

        return result
	}

    void chooseCancelOnNextConfirmation(WorkflowContext context){

        cbp.chooseCancelOnNextConfirmation(context)
    }

    void chooseOkOnNextConfirmation(WorkflowContext context){

        cbp.chooseOkOnNextConfirmation(context)
    }

    void answerOnNextPrompt(WorkflowContext context, String answer){

        cbp.answerOnNextPrompt(context, answer)
    }

    void goBack(WorkflowContext context){

        cbp.goBack(context)
    }

    void refresh(WorkflowContext context){

        cbp.refresh(context)
    }

    protected boolean includeKeyEvents(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYDOWN.toString().equalsIgnoreCase(event) || Event.KEYPRESS.toString().equalsIgnoreCase(event)
                    || Event.KEYUP.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyDown(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYDOWN.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyPress(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYPRESS.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyUp(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYUP.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }


    def mouseDown(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseDown(context, locator)
        }
	}

    def mouseDownRight(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseDownRight(context, locator)
        }
	}

    def mouseDownRightAt(WorkflowContext context, String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseDownRightAt(context, locator, coordinate)
        }
	}

    def mouseUp(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseUp(context, locator)
        }
	}

    def mouseUpRight(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseUpRight(context, locator)
        }
	}

    def mouseUpRightAt(WorkflowContext context, String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseUpRightAt(context, locator, coordinate)
        }
	}

    def mouseMove(WorkflowContext context, String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseMove(context, locator)
        }
	}

    def mouseMoveAt(WorkflowContext context, String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(context, locator, events, defaultEvents){
            cbp.mouseMoveAt(context, locator, coordinate)
        }
	}

}