package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Haptics;

/** Material 4 slider with a haptic tick for user initiated value changes. */
public class Slider extends com.google.android.material.slider.Slider {

    public Slider(@NonNull Context context) {
        super(context);
        init();
    }

    public Slider(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Slider(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Material4Widgets.init(this);
        addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                Material4Haptics.tick(slider);
            }
        });
    }
}
