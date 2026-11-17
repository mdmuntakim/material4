package io.material4.widget;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import io.material4.Material4Depth;
import io.material4.Material4Tokens;

/** Material 4 modal bottom sheet with the glass/blur treatment. */
public class Material4BottomSheetDialog extends BottomSheetDialog {

    public Material4BottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public Material4BottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        Material4Depth.applyDialogBlur(this);
        if (getWindow() != null) {
            getWindow().setDimAmount(Material4Tokens.from(getContext()).highContrast ? 0.7f : 0.42f);
        }
    }

    /** Sets a content view and shows the sheet. */
    public void showContent(@NonNull android.view.View content) {
        setContentView(content);
        show();
    }

    @Nullable
    @Override
    public com.google.android.material.bottomsheet.BottomSheetBehavior<android.widget.FrameLayout>
            getBehavior() {
        return super.getBehavior();
    }
}
