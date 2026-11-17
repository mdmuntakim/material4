package io.material4;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.color.ColorContrast;
import com.google.android.material.color.ColorContrastOptions;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

/**
 * Applies a {@link Material4ThemeConfig} to an activity.
 *
 * <p>Call {@link #apply(Activity, Material4ThemeConfig)} from {@code onCreate} before
 * {@code setContentView}. Both Material's dynamic-colour resource overlay and the Material 4
 * high-contrast overlay are applied there, so every component (including views inflated from
 * XML) resolves the same tokens.
 */
public final class Material4Theme {

    public static final int THEME_OVERLAY = R.style.ThemeOverlay_Material4;
    public static final int HIGH_CONTRAST_OVERLAY = R.style.ThemeOverlay_Material4_HighContrast;
    public static final int BASELINE_OVERLAY = R.style.ThemeOverlay_Material4_Material3Baseline;

    private Material4Theme() {
    }

    /** Applies night mode, dynamic colour, seed colour, contrast and comparison settings. */
    public static void apply(@NonNull Activity activity, @NonNull Material4ThemeConfig config) {
        int nightMode = switch (config.nightMode) {
            case Material4ThemeConfig.MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO;
            case Material4ThemeConfig.MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES;
            default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        };
        if (AppCompatDelegate.getDefaultNightMode() != nightMode) {
            AppCompatDelegate.setDefaultNightMode(nightMode);
        }

        if (config.comparisonMode) {
            activity.getTheme().applyStyle(BASELINE_OVERLAY, true);
        }
        if (config.reducedMotion) {
            activity.getTheme().applyStyle(R.style.ThemeOverlay_Material4_ReducedMotion, true);
        }

        if (config.highContrast) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ColorContrast.applyToActivityIfAvailable(activity,
                        new ColorContrastOptions.Builder()
                                .setHighContrastThemeOverlay(HIGH_CONTRAST_OVERLAY)
                                .setMediumContrastThemeOverlay(HIGH_CONTRAST_OVERLAY)
                                .build());
            } else {
                activity.getTheme().applyStyle(HIGH_CONTRAST_OVERLAY, true);
            }
        }

        if (config.dynamicColour && DynamicColors.isDynamicColorAvailable()) {
            DynamicColorsOptions.Builder builder = new DynamicColorsOptions.Builder()
                    .setThemeOverlay(THEME_OVERLAY);
            if (config.seedColour != null) {
                builder.setContentBasedSource(config.seedColour.intValue());
            }
            DynamicColors.applyToActivityIfAvailable(activity, builder.build());
        } else if (config.seedColour != null) {
            // Dynamic colour is unavailable below Android 12. The static Material 4 palette is
            // used and Material4Tokens still exposes the selected seed to the gallery header.
            activity.getTheme().applyStyle(THEME_OVERLAY, true);
        } else {
            activity.getTheme().applyStyle(THEME_OVERLAY, true);
        }
    }

    /** True when the platform supports Material You dynamic colour. */
    public static boolean dynamicColourAvailable() {
        return DynamicColors.isDynamicColorAvailable();
    }

    /** True when the platform supports Material's high-contrast resource overlay. */
    public static boolean highContrastAvailable() {
        return ColorContrast.isContrastAvailable();
    }
}
