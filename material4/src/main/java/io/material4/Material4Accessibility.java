package io.material4;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
/** WCAG contrast helpers and touch-target enforcement for Material 4 components. */
public final class Material4Accessibility {

    private Material4Accessibility() {
    }

    public static double contrastRatio(@ColorInt int foreground, @ColorInt int background) {
        double fg = relativeLuminance(foreground);
        double bg = relativeLuminance(background);
        double lighter = Math.max(fg, bg);
        double darker = Math.min(fg, bg);
        return (lighter + 0.05) / (darker + 0.05);
    }

    /** WCAG 2.2 relative luminance in the sRGB colour space. */
    public static double relativeLuminance(@ColorInt int colour) {
        double red = linear(((colour >> 16) & 0xFF) / 255.0);
        double green = linear(((colour >> 8) & 0xFF) / 255.0);
        double blue = linear((colour & 0xFF) / 255.0);
        return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
    }

    private static double linear(double channel) {
        return channel <= 0.03928 ? channel / 12.92 : Math.pow((channel + 0.055) / 1.055, 2.4);
    }

    public static boolean meetsWcagAa(@ColorInt int foreground, @ColorInt int background,
                                      boolean largeText) {
        return contrastRatio(foreground, background) >= (largeText ? 3.0 : 4.5);
    }

    public static boolean meetsWcagAaa(@ColorInt int foreground, @ColorInt int background,
                                       boolean largeText) {
        return contrastRatio(foreground, background) >= (largeText ? 4.5 : 7.0);
    }

    /** Ensures at least a 48dp interactive target, without changing a larger explicit size. */
    public static void ensureTouchTarget(@NonNull View view) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        int target = Math.round(tokens.touchTarget);
        if (view.getMinimumWidth() < target) {
            view.setMinimumWidth(target);
        }
        if (view.getMinimumHeight() < target) {
            view.setMinimumHeight(target);
        }
    }

    /** Sets a content description only when the caller has not supplied one. */
    public static void describeIfMissing(@NonNull View view, @NonNull CharSequence description) {
        if (view.getContentDescription() == null) {
            view.setContentDescription(description);
        }
    }

    /** Explicit TalkBack traversal order, front to back. */
    public static void traversalOrder(@NonNull View... views) {
        for (int i = 1; i < views.length; i++) {
            views[i].setAccessibilityTraversalAfter(views[i - 1].getId());
        }
    }

    /** Applies the Material 4 high-contrast outline to a view without touching its content. */
    public static void enforceOutline(@NonNull View view, @ColorInt int outlineColour, float widthPx) {
        view.setBackground(new android.graphics.drawable.GradientDrawable() {{
            setColor(Color.TRANSPARENT);
            setStroke(Math.max(2, Math.round(widthPx)), outlineColour);
        }});
    }
}
