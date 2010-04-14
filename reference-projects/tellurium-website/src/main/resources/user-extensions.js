var UiSelectMenu = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'SelectMenu';
        this.tag = "div";
        this.header = null;
        this.menuItems = {};
    },

    getMenuItem: function(context, menuItem){
        return teJQuery(context.domRef).find("table > tbody > tr > td:te_text(" + menuItem + ")");
    },

    walkTo: function(context, uiid) {
         !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() > 1) {
                        if(this.header != null){
                            $found = $found.filter("has(table > tbody > tr:eq(0) > th:te_text(" + this.header + "))");
                        }
                    }              

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + this.uid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        if(uiid.size() < 1)
            return this;

        var cid = uiid.pop();
        var child = this.menuItems.get(cid);
        if (child != null) {
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            var $fmt = this.getMenuItem(child);
            if ($fmt.size() == 1) {
                context.domRef = $fmt.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($fmt.size() == 0)
                    fbError("Cannot find UI element " + this.uid, this);
                if ($fmt.size() > 1) {
                    fbError("Found multiple matches for UI element " + this.uid, $fmt.get());
                    context.domRef = null;
                }
            }

            return this;
        } else {
            fbError("Cannot find child " + cid, child);
            context.domRef = null;
            return null;
        }
    }

});

function UiSelectMenuBuilder(){

}

UiSelectMenuBuilder.prototype.build = function(){
     return new UiSelectMenuBuilder();
};

teJQuery(document).ready(function() {
    tellurium.registerUiBuilder("SelectMenu", new UiSelectMenuBuilder());
});