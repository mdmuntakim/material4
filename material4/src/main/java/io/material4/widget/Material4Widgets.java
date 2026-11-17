package io.material4.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.listitem.ListItemCardView;
import com.google.android.material.shape.Shapeable;

import io.material4.Material4Accessibility;
import io.material4.Material4Depth;
import io.material4.Material4Motion;
import io.material4.Material4Shape;
import io.material4.Material4Tokens;

/**
 * Shared component initialisation. Every Material 4 widget calls this once from its constructor,
 * so tokens are applied by the theme overlay rather than by a screen.
 */
public final class Material4Widgets {

    private Material4Widgets() {
    }

    @NonNull
    public static <T extends View> T init(@NonNull T view) {
        Context context = view.getContext();
        Material4Tokens tokens = Material4Tokens.from(context);

        boolean interactive = view instanceof MaterialButton
                || view instanceof Chip
                || view instanceof android.widget.CompoundButton
                || view instanceof FloatingActionButton
                || view instanceof ExtendedFloatingActionButton
                || view instanceof com.google.android.material.slider.Slider
                || view instanceof com.google.android.material.slider.RangeSlider
                || view instanceof com.google.android.material.tabs.TabLayout;
        if (interactive) {
            Material4Accessibility.ensureTouchTarget(view);
        }

        if (!tokens.comparisonMode && view instanceof Shapeable shapeable) {
            float radius;
            if (view instanceof MaterialCardView || view instanceof ListItemCardView) {
                radius = tokens.radiusLarge / context.getResources().getDisplayMetrics().density;
            } else if (view instanceof FloatingActionButton
                    || view instanceof ExtendedFloatingActionButton) {
                radius = tokens.radiusLarge / context.getResources().getDisplayMetrics().density;
            } else if (view instanceof Chip) {
                radius = tokens.radiusMedium / context.getResources().getDisplayMetrics().density;
            } else {
                radius = tokens.radiusMedium / context.getResources().getDisplayMetrics().density;
            }
            Material4Shape.apply(shapeable, context, radius);
        }

        if (interactive && !(view instanceof android.widget.CompoundButton)) {
            Material4Motion.pressFeedback(view);
        }

        if (view instanceof MaterialCardView card) {
            card.setElevation(tokens.elevation2);
            card.setStrokeWidth(Math.round(tokens.borderWidth));
        } else if (view instanceof FloatingActionButton fab) {
            fab.setElevation(tokens.elevation3);
        } else if (view instanceof ExtendedFloatingActionButton fab) {
            fab.setElevation(tokens.elevation3);
        }

        view.setAccessibilityLiveRegion(View.ACCESSIBILITY_LIVE_REGION_NONE);
        return view;
    }
}
