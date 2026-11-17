package io.material4;

import android.os.Build;
import android.view.HapticFeedbackConstants;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

/** Small, token-aware haptic helper used by toggles, chips and sliders. */
public final class Material4Haptics {

    private Material4Haptics() {
    }

    public static void toggle(@NonNull View view) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        if (!tokens.hapticsEnabled) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
        } else {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
    }

    public static void tick(@NonNull View view) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        if (!tokens.hapticsEnabled) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);
        } else {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
    }

    public static void attach(@NonNull View view) {
        if (view instanceof android.widget.CompoundButton button) {
            button.setOnCheckedChangeListener((v, checked) -> toggle(v));
        } else if (view instanceof Slider slider) {
            slider.addOnChangeListener((s, value, fromUser) -> {
                if (fromUser) {
                    tick(s);
                }
            });
        } else if (view instanceof RangeSlider slider) {
            slider.addOnChangeListener((s, value, fromUser) -> {
                if (fromUser) {
                    tick(s);
                }
            });
        }
    }
}
