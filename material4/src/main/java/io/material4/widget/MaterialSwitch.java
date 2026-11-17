package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Haptics;

/** Material 4 switch. Applies its tokens from the theme and gives haptic feedback on toggle. */
public class MaterialSwitch extends com.google.android.material.materialswitch.MaterialSwitch {

    public MaterialSwitch(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public MaterialSwitch(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

    public MaterialSwitch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Material4Widgets.init(this);
    }

    @Override
    public void toggle() {
        super.toggle();
        Material4Haptics.toggle(this);
    }
}
