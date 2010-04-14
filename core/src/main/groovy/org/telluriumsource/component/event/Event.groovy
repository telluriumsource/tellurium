package org.telluriumsource.component.event
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 14, 2008
 *
 *
 *
 * -------------------------------------------------------------------------------------------------------------
 * Event Handlers  	   	Applicable inside:
 * -------------------------------------------------------------------------------------------------------------
 * onAbort 	  	    <img> tags
 * onBlur 	  	    window object, all form objects (ie: <input>), and <frame>.
 * onClick 	  	    Most visible elements such as <a>, <div>, <body> etc.
 * onChange 	  	Use this to invoke JavaScript if the mouse goes pass some link
 * onError 	  	    Text fields, textareas, and select lists.
 * onFocus 	  	    Most visible elements such as <a>, <div>, <body> etc.
 * onLoad 	  	    <body>, <img>, and <frame>
 * onMouseover 	  	Most visible elements such as <a>, <div>, <body> etc.
 * onMouseout 	  	Most visible elements such as <a>, <div>, <body> etc.
 * onReset 	  	    <form> tag, triggered when the form is reset via <input type="reset">.
 * onSelect 	  	Elements with textual content. Most commonly used inside text fields and textareas.
 * onSubmit 	  	<form> tag, triggered when the form is submitted.
 * onUnload 	  	<body>
 * ------------------------------------------------------------------------------------------------------------
 * 
 */
enum Event {
    //action is a placeholder for the actual action the event handler needs to handle
    ACTION,
    BLUR,
    FOCUS,
    CLICK,
    DOUBLECLICK,
    MOUSEOVER,
    MOUSEOUT,
    MOUSEDOWN,
    MOUSEUP,
    KEYDOWN,
    KEYPRESS,
    KEYUP
}