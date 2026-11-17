package io.material4;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Persisted Material 4 gallery settings.
 *
 * <p>The library itself never reads preferences. The sample persists its global controls here
 * so a configuration change or a process restart keeps the selected tokens.
 */
public final class Material4ThemeConfig {

    public static final int MODE_AUTO = 0;
    public static final int MODE_LIGHT = 1;
    public static final int MODE_DARK = 2;

    private static final String PREFS = "material4.settings";
    private static final String KEY_MODE = "night_mode";
    private static final String KEY_DYNAMIC = "dynamic_colour";
    private static final String KEY_SEED = "seed_colour";
    private static final String KEY_CONTRAST = "high_contrast";
    private static final String KEY_REDUCED_MOTION = "reduced_motion";
    private static final String KEY_COMPARISON = "comparison";

    public int nightMode = MODE_AUTO;
    public boolean dynamicColour = true;
    @Nullable public Integer seedColour;
    public boolean highContrast;
    public boolean reducedMotion;
    public boolean comparisonMode;

    public static Material4ThemeConfig load(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Material4ThemeConfig config = new Material4ThemeConfig();
        config.nightMode = prefs.getInt(KEY_MODE, MODE_AUTO);
        config.dynamicColour = prefs.getBoolean(KEY_DYNAMIC, true);
        int seed = prefs.getInt(KEY_SEED, 0);
        config.seedColour = seed == 0 ? null : seed;
        config.highContrast = prefs.getBoolean(KEY_CONTRAST, false);
        config.reducedMotion = prefs.getBoolean(KEY_REDUCED_MOTION, false);
        config.comparisonMode = prefs.getBoolean(KEY_COMPARISON, false);
        return config;
    }

    public void save(@NonNull Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_MODE, nightMode);
        editor.putBoolean(KEY_DYNAMIC, dynamicColour);
        editor.putInt(KEY_SEED, seedColour == null ? 0 : seedColour);
        editor.putBoolean(KEY_CONTRAST, highContrast);
        editor.putBoolean(KEY_REDUCED_MOTION, reducedMotion);
        editor.putBoolean(KEY_COMPARISON, comparisonMode);
        editor.apply();
    }
}
