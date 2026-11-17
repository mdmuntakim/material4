package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Haptics;

/** Material 4 checkbox with the shared touch target and a haptic state change. */
public class MaterialCheckBox extends com.google.android.material.checkbox.MaterialCheckBox {

    public MaterialCheckBox(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public MaterialCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

    public MaterialCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Material4Widgets.init(this);
    }

    @Override
    public void toggle() {
        super.toggle();
        Material4Haptics.toggle(this);
    }
}
