package io.material4.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.sidesheet.SideSheetDialog;

import io.material4.Material4Depth;

/** Material 4 side sheet with the shared blur/scrim fallback. */
public class Material4SideSheetDialog extends SideSheetDialog {

    public Material4SideSheetDialog(@NonNull Context context) {
        super(context);
    }

    public Material4SideSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        Material4Depth.applyDialogBlur(this);
    }
}
