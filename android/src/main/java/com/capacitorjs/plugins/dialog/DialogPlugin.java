package com.capacitorjs.plugins.dialog;

import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Dialog")
public class DialogPlugin extends Plugin {

    @PluginMethod
    public void alert(final PluginCall call) {
        final AppCompatActivity activity = this.getActivity();
        final String title = call.getString("title");
        final String message = call.getString("message");
        final String buttonTitle = call.getString("buttonTitle", "OK");
        final String buttonStyle = call.getString("buttonStyle", "default");
        final Boolean useDefaultDialogStyle = call.getBoolean("useDefaultDialogStyle", false);

        if (message == null) {
            call.reject("Please provide a message for the dialog");
            return;
        }

        if (activity.isFinishing()) {
            call.reject("App is finishing");
            return;
        }

        Dialog.alert(activity, message, title, buttonTitle,buttonStyle,useDefaultDialogStyle, (value, didCancel, inputValue) -> call.resolve());
    }

    @PluginMethod
    public void confirm(final PluginCall call) {
        final AppCompatActivity activity = this.getActivity();
        final String title = call.getString("title");
        final String message = call.getString("message");
        final String okButtonTitle = call.getString("okButtonTitle", "OK");
        final String cancelButtonTitle = call.getString("cancelButtonTitle", "Cancel");
        final String cancelButtonStyle = call.getString("cancelButtonStyle", "default");
        final String okButtonStyle = call.getString("okButtonStyle", "default");
        final Boolean useDefaultDialogStyle = call.getBoolean("useDefaultDialogStyle", false);

        if (message == null) {
            call.reject("Please provide a message for the dialog");
            return;
        }

        if (activity.isFinishing()) {
            call.reject("App is finishing");
            return;
        }

        Dialog.confirm(activity, message, title, okButtonTitle, cancelButtonTitle, okButtonStyle,cancelButtonStyle,useDefaultDialogStyle, (value, didCancel, inputValue) -> {
            JSObject ret = new JSObject();
            ret.put("value", value);
            call.resolve(ret);
        });
    }

    @PluginMethod
    public void prompt(final PluginCall call) {
        final AppCompatActivity activity = this.getActivity();
        final String title = call.getString("title");
        final String message = call.getString("message");
        final String okButtonTitle = call.getString("okButtonTitle", "OK");
        final String cancelButtonTitle = call.getString("cancelButtonTitle", "Cancel");
        final String cancelButtonStyle = call.getString("cancelButtonStyle", "default");
        final String okButtonStyle = call.getString("okButtonStyle", "default");
        final String inputPlaceholder = call.getString("inputPlaceholder", "");
        final String inputText = call.getString("inputText", "");
        final Boolean useDefaultDialogStyle = call.getBoolean("useDefaultDialogStyle", false);

        if (message == null) {
            call.reject("Please provide a message for the dialog");
            return;
        }

        if (activity.isFinishing()) {
            call.reject("App is finishing");
            return;
        }

        Dialog.prompt(
            activity,
            message,
            title,
            okButtonTitle,
            cancelButtonTitle,
            cancelButtonStyle,
            okButtonStyle,
            inputPlaceholder,
            inputText,
            useDefaultDialogStyle,
            (value, didCancel, inputValue) -> {
                JSObject ret = new JSObject();
                ret.put("cancelled", didCancel);
                ret.put("value", inputValue == null ? "" : inputValue);
                call.resolve(ret);
            }
        );
    }
}
