
var FileUtils = {

    showFilePicker: function(window, title, mode, defaultDirPrefName, handler) {
        var nsIFilePicker = Components.interfaces.nsIFilePicker;
        var fp = Components.classes["@mozilla.org/filepicker;1"].createInstance(nsIFilePicker);
        fp.init(window, title, mode);
        //        var defaultDir = Preferences.getString(defaultDirPrefName);
        //        if (defaultDir) {
        //            fp.displayDirectory = FileUtils.getFile(defaultDir);
        //        }
        var defaultDir = defaultDirPrefName;
        fp.displayDirectory = FileUtils.getFile(defaultDir);
        fp.appendFilters(nsIFilePicker.filterHTML | nsIFilePicker.filterAll);
        var res = fp.show();
        if (res == nsIFilePicker.returnOK || res == nsIFilePicker.returnReplace) {
            //            Preferences.setString(defaultDirPrefName, fp.file.parent.path);
            return handler(fp);
        } else {
            return null;
        }
    },

    getUnicodeConverter: function(encoding) {
        var unicodeConverter = Components.classes["@mozilla.org/intl/scriptableunicodeconverter"].createInstance(Components.interfaces.nsIScriptableUnicodeConverter);
        try {
            unicodeConverter.charset = encoding;
        } catch (error) {
            throw "setting encoding failed: " + encoding;
        }
        return unicodeConverter;
    },

    saveAs: function(defaultDirPrefName, text) {
        try {
            var file = this.showFilePicker(window, "Save as...",
                    Components.interfaces.nsIFilePicker.modeSave,
                    defaultDirPrefName,
                    function(fp) {
                        return fp.file;
                    });

            if (file != null) {
                var outputStream = Components.classes["@mozilla.org/network/file-output-stream;1"].createInstance(Components.interfaces.nsIFileOutputStream);
                outputStream.init(file, 0x02 | 0x08 | 0x20, 0644, 0);

                outputStream.write(text, text.length);
                outputStream.close();

                return true;
            } else {
                return false;
            }
        } catch (err) {
            logger.error("Error: " + err);
            return false;
        }

    },

    openFileOutputStream: function(file) {
        var stream = Components.classes["@mozilla.org/network/file-output-stream;1"].createInstance(Components.interfaces.nsIFileOutputStream);
        stream.init(file, 0x02 | 0x08 | 0x20, 420, 0);
        return stream;
    },

    openFileInputStream: function(file) {
        var stream = Components.classes["@mozilla.org/network/file-input-stream;1"].createInstance(Components.interfaces.nsIFileInputStream);
        stream.init(file, 0x01, 00004, 0);
        var sis = Components.classes["@mozilla.org/scriptableinputstream;1"].createInstance(Components.interfaces.nsIScriptableInputStream);
        sis.init(stream);
        return sis;
    },

    getFile: function(path) {
        var file = Components.classes['@mozilla.org/file/local;1'].createInstance(Components.interfaces.nsILocalFile);
        file.initWithPath(path);
        return file;
    }

}