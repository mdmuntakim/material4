package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Haptics;

/** Material 4 radio button with the shared touch target and a haptic selection. */
public class MaterialRadioButton extends com.google.android.material.radiobutton.MaterialRadioButton {

    public MaterialRadioButton(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public MaterialRadioButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

    public MaterialRadioButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Material4Widgets.init(this);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            Material4Haptics.toggle(this);
        }
        super.toggle();
    }
}
