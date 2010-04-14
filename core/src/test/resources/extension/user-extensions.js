// User extensions can be added here.
// All do* methods on the Selenium prototype are added as actions.
// Eg add a typeRepeated action to Selenium, which types the text twice into a text box.
// The typeTwiceAndWait command will be available automatically

Selenium.prototype.doTypeRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to type
    var valueToType = text + text;

    // Replace the element text with the new text
    this.page().replaceText(element, valueToType);
};
