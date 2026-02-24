package com.capacitorjs.plugins.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
public class Dialog {

    public interface OnResultListener {
        void onResult(boolean value, boolean didCancel, String inputValue);
    }

    public interface OnCancelListener {
        void onCancel();
    }

    /**
     * Show a simple alert with a message and default values for
     * title and ok button
     * @param message the message to show
     */
    public static void alert(final Context context, final String message, final Dialog.OnResultListener listener) {
        alert(context, message, null, null, null,null, listener);
    }

    /**
     * Show an alert window
     * @param context the context
     * @param message the message for the alert
     * @param title the title for the alert
     * @param okButtonTitle the title for the OK button
     * @param listener the listener for returning data back
     */
    public static void alert(
        final Context context,
        final String message,
        final String title,
        final String okButtonTitle,
        final String okButtonStyle,
        final Boolean useDefaultDialogStyle,
        final Dialog.OnResultListener listener
    ) {
        final String alertOkButtonTitle = okButtonTitle == null ? "OK" : okButtonTitle;
        // Default: false (force device default theme) if null
        final boolean useCapacitorStyle = Boolean.TRUE.equals(useDefaultDialogStyle);

        new Handler(Looper.getMainLooper()).post(() -> {
            final AlertDialog.Builder builder = useCapacitorStyle
                ? new AlertDialog.Builder(context)
                : new AlertDialog.Builder(context, deviceDefaultDialogTheme(context));

            if (title != null) {
                builder.setTitle(title);
            }
            builder
                .setMessage(message)
                .setPositiveButton(alertOkButtonTitle, (dialog, buttonIndex) -> {
                    dialog.dismiss();
                    listener.onResult(true, false, null);
                })
                .setOnCancelListener((dialog) -> {
                    dialog.dismiss();
                    listener.onResult(false, true, null);
                });

            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(d -> {
                Button okBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final int confirmOkButtonStyle = resolveButtonTextColor(context, okButtonStyle);

                if (okBtn != null)
                    okBtn.setTextColor(confirmOkButtonStyle);
            });
            dialog.show();
        });
    }

    public static void confirm(final Context context, final String message, final Dialog.OnResultListener listener) {
        confirm(context, message, null, null, null,null,null,null, listener);
    }

    public static void confirm(
    final Context context,
    final String message,
    final String title,
    final String okButtonTitle,
    final String cancelButtonTitle,
    final String okButtonStyle,
    final String cancelButtonStyle,
    final Boolean useDefaultDialogStyle,
    final Dialog.OnResultListener listener
) {
    final String confirmOkButtonTitle = okButtonTitle == null ? "OK" : okButtonTitle;
    final String confirmCancelButtonTitle = cancelButtonTitle == null ? "Cancel" : cancelButtonTitle;

    // Default: false (force device default theme) if null
    final boolean useCapacitorStyle = Boolean.TRUE.equals(useDefaultDialogStyle);

    new Handler(Looper.getMainLooper()).post(() -> {
        final AlertDialog.Builder builder = useCapacitorStyle
            ? new AlertDialog.Builder(context)
            : new AlertDialog.Builder(context, deviceDefaultDialogTheme(context));

        if (title != null) {
            builder.setTitle(title);
        }

        builder
            .setMessage(message)
            .setPositiveButton(confirmOkButtonTitle, (dialog, buttonIndex) -> {
                dialog.dismiss();
                listener.onResult(true, false, null);
            })
            .setNegativeButton(confirmCancelButtonTitle, (dialog, buttonIndex) -> {
                dialog.dismiss();
                listener.onResult(false, false, null);
            })
            .setOnCancelListener(dialog -> {
                dialog.dismiss();
                listener.onResult(false, true, null);
            });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d -> {
            Button okBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button cancelBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            int okColor = resolveButtonTextColor(context, okButtonStyle);
            int cancelColor = resolveButtonTextColor(context, cancelButtonStyle);

            if (okBtn != null) okBtn.setTextColor(okColor);
            if (cancelBtn != null) cancelBtn.setTextColor(cancelColor);
        });

        dialog.show();
    });
}

    public static void prompt(final Context context, final String message, final Dialog.OnResultListener listener) {
        prompt(context, message, null, null,null, null,null, null, null, null, listener);
    }

    public static void prompt(
        final Context context,
        final String message,
        final String title,
        final String okButtonTitle,
        final String okButtonStyle,
        final String cancelButtonTitle,
        final String cancelButtonStyle,
        final String inputPlaceholder,
        final String inputText,
        final Boolean useDefaultDialogStyle,
        final Dialog.OnResultListener listener
    ) {
        final String promptOkButtonTitle = okButtonTitle == null ? "OK" : okButtonTitle;
        final String promptCancelButtonTitle = cancelButtonTitle == null ? "Cancel" : cancelButtonTitle;
        final String promptInputPlaceholder = inputPlaceholder == null ? "" : inputPlaceholder;
        final String promptInputText = inputText == null ? "" : inputText;

        // Default: false (force device default theme) if null
        final boolean useCapacitorStyle = Boolean.TRUE.equals(useDefaultDialogStyle);

        new Handler(Looper.getMainLooper()).post(() -> {
            final AlertDialog.Builder builder = useCapacitorStyle
                ? new AlertDialog.Builder(context)
                : new AlertDialog.Builder(context, deviceDefaultDialogTheme(context));
            final EditText input = new EditText(context);

            input.setHint(promptInputPlaceholder);
            input.setText(promptInputText);
            if (title != null) {
                builder.setTitle(title);
            }
            builder
                .setMessage(message)
                .setView(input)
                .setPositiveButton(promptOkButtonTitle, (dialog, buttonIndex) -> {
                    dialog.dismiss();

                    String inputText1 = input.getText().toString().trim();
                    listener.onResult(true, false, inputText1);
                })
                .setNegativeButton(promptCancelButtonTitle, (dialog, buttonIndex) -> {
                    dialog.dismiss();
                    listener.onResult(false, true, null);
                })
                .setOnCancelListener((dialog) -> {
                    dialog.dismiss();
                    listener.onResult(false, true, null);
                });

            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(d -> {
                Button okBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button cancelBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                final int confirmOkButtonStyle = resolveButtonTextColor(context, okButtonStyle);
                final int confirmCancelButtonStyle = resolveButtonTextColor(context, cancelButtonStyle);

                if (okBtn != null)
                    okBtn.setTextColor(confirmOkButtonStyle);
                if (cancelBtn != null)
                    cancelBtn.setTextColor(confirmCancelButtonStyle);
            });

            dialog.show();
        });
    }
    private static int resolveButtonTextColor(Context context, String style) {
        if (style == null) style = "default";


        switch (style) {
            case "destructive":
                // If you defined a custom red in colors.xml, use that.
                return resolveThemeColor(
                        context,
                        android.R.attr.colorError,
                        0xFFB00020 // fallback red-ish
                );

            case "cancel":
            case "default":
            default:
                return resolveThemeColor(
                        context,
                        android.R.attr.colorAccent,
                        0xFF1A73E8 // fallback blue-ish
                );
        }
    }

    private static int resolveThemeColor(Context context, int attr, int fallbackColor) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attr, typedValue, true)) {
            return typedValue.data;
        }
        return fallbackColor;
    }


    private static int deviceDefaultDialogTheme(Context context) {
        int night =
                context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        boolean isNight = (night == Configuration.UI_MODE_NIGHT_YES);

        // Dark uses Theme_DeviceDefault_Dialog_Alert
        // Light uses Theme_DeviceDefault_Light_Dialog_Alert
        return isNight
                ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
    }
}

