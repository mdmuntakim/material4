package io.material4.widget;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.material4.Material4Depth;
import io.material4.Material4Tokens;

/**
 * Material 4 dialog builder. A dialog cannot be subclassed through its public API, so the
 * wrapper keeps the Material 4 tokens on top of the public Material 3 builder.
 */
public class Material4AlertDialog extends MaterialAlertDialogBuilder {

    public Material4AlertDialog(@NonNull Context context) {
        super(context);
    }

    public Material4AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public androidx.appcompat.app.AlertDialog create() {
        androidx.appcompat.app.AlertDialog dialog = super.create();
        Material4Depth.applyDialogBlur(dialog);
        return dialog;
    }

    /** Shows a simple Material 4 dialog with a title and message. */
    @NonNull
    public static androidx.appcompat.app.AlertDialog show(@NonNull Context context,
                                                          @Nullable CharSequence title,
                                                          @NonNull CharSequence message) {
        Material4AlertDialog builder = new Material4AlertDialog(context);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /** Applies the token colours to a dialog that was created elsewhere. */
    public static void style(@NonNull androidx.appcompat.app.AlertDialog dialog) {
        Material4Tokens.from(dialog.getContext());
        Material4Depth.applyDialogBlur(dialog);
    }
}
