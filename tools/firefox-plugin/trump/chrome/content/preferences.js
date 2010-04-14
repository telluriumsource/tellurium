var Preferences = {
    initialize: function() {
        this.prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefService).getBranch('extensions.trump.');

        this.prefs.QueryInterface(Components.interfaces.nsIPrefBranch2);
    },

    getPref: function(name) {
        var type = this.prefs.getPrefType(name);

        if (type == this.prefs.PREF_STRING)
            return this.prefs.getCharPref(name);
        else if (type == this.prefs.PREF_INT)
            return this.prefs.getIntPref(name);
        else if (type == this.prefs.PREF_BOOL)
                return this.prefs.getBoolPref(name);
    },

    setPref: function(name, value) {
        var type = this.prefs.getPrefType(name);

        if (type == 0) {
            if (typeof(value) == 'number')
                type = 64;
            else if (typeof(value) == 'string')
                type = 32;
            else if (typeof(value) == 'boolean')
                    type = 128;
        }

        if (type == this.prefs.PREF_STRING)
            this.prefs.setCharPref(name, value);
        else if (type == this.prefs.PREF_INT)
            this.prefs.setIntPref(name, value);
        else if (type == this.prefs.PREF_BOOL)
                this.prefs.setBoolPref(name, value);
    }
};

Preferences.DEFAULT_OPTIONS = {

    defaultDirectory: "/",

    defaultWinDirectory: "c:\\",

    defaultJSLog: "true",

    defaultLogWrap: "false"
};

Preferences.initialize();

