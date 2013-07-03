(function(Ext, Wicket) {
    /**
     * Ajax implementation that synchronizes Ext AJAX calls with Wicket.
     * When the default wicket channel is busy, the ext call is executed synchronously.
     */
    var original = Ext.lib.Ajax.request;
    Ext.lib.Ajax.request = function(method, uri, cb, data, options) {
        options.async = options.async || true;


        var defaultChannel = "0|s"; // wicket default channel
        var wicketChannel = Wicket.channelManager.channels[defaultChannel];
        if (wicketChannel != null && wicketChannel.busy) {
            options.async = false;
        } else {
            Wicket.Event.publish('/ajax/call/before');
        }

        var callback;
        if (typeof cb !== "undefined") {
            var originalSuccess = cb.success;
            cb.success = function() {
                if (typeof originalSuccess === 'function') {
                    originalSuccess.apply(Ext.Ajax, arguments);
                }
                if (options.async) {
                    Wicket.Event.publish('/ajax/call/success');
                    Wicket.Event.publish('/ajax/call/complete');
                }
            };
            var originalFail = cb.failure;
            cb.failure = function() {
                if (typeof originalFail === 'function') {
                    originalFail.apply(Ext.Ajax, arguments);
                }
                if (options.async) {
                    Wicket.Event.publish('/ajax/call/failure');
                    Wicket.Event.publish('/ajax/call/complete');
                }
            };
            callback = cb;
        } else {
            callback = {
                success: function() {
                    if (options.async) {
                        Wicket.Event.publish('/ajax/call/success');
                        Wicket.Event.publish('/ajax/call/complete');
                    }
                },
                failure: function() {
                    if (options.async) {
                        Wicket.Event.publish('/ajax/call/failure');
                        Wicket.Event.publish('/ajax/call/complete');
                    }
                }
            };
        }

        return original.apply(Ext.lib.Ajax, [method, uri, callback, data, options]);
    };

})(Ext, Wicket);