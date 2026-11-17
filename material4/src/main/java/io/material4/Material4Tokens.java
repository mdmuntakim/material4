package io.material4;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.google.android.material.color.MaterialColors;

/**
 * Resolved Material 4 design tokens.
 *
 * <p>Every value comes from the current theme overlay, so a component never needs to be
 * styled at the screen level. Tokens are intentionally immutable: re-resolve them after a
 * theme change (the sample recreates its activity).
 */
public final class Material4Tokens {

    @ColorInt public final int auroraStart;
    @ColorInt public final int auroraMid;
    @ColorInt public final int auroraEnd;
    @ColorInt public final int onAurora;
    @ColorInt public final int glassSurface;
    @ColorInt public final int glassBorderStart;
    @ColorInt public final int glassBorderEnd;
    @ColorInt public final int innerGlow;
    @ColorInt public final int success;
    @ColorInt public final int warning;
    @ColorInt public final int primary;
    @ColorInt public final int onPrimary;
    @ColorInt public final int secondary;
    @ColorInt public final int tertiary;
    @ColorInt public final int surface;
    @ColorInt public final int onSurface;
    @ColorInt public final int outline;

    @Dimension public final float elevation1;
    @Dimension public final float elevation2;
    @Dimension public final float elevation3;
    @Dimension public final float elevation4;
    @Dimension public final float elevation5;
    @Dimension public final float borderWidth;
    @Dimension public final float glowWidth;

    @Dimension public final float radiusSmall;
    @Dimension public final float radiusMedium;
    @Dimension public final float radiusLarge;
    @Dimension public final float radiusExtraLarge;
    public final boolean superellipse;
    public final boolean morphOnPress;

    public final int motionShort;
    public final int motionMedium;
    public final int motionLong;
    public final float springStiffness;
    public final float springDamping;
    public final boolean reducedMotion;

    @Dimension public final float touchTarget;
    public final boolean hapticsEnabled;
    public final boolean highContrast;
    public final boolean comparisonMode;

    @StyleRes public final int displayTextAppearance;
    @StyleRes public final int headlineTextAppearance;
    @StyleRes public final int titleTextAppearance;
    @StyleRes public final int bodyTextAppearance;
    @StyleRes public final int labelTextAppearance;

    private Material4Tokens(@NonNull Context context) {
        auroraStart = color(context, R.attr.m4AuroraStart, 0xFF7C4DFF);
        auroraMid = color(context, R.attr.m4AuroraMid, 0xFF00D9FF);
        auroraEnd = color(context, R.attr.m4AuroraEnd, 0xFFFF4D9D);
        onAurora = color(context, R.attr.m4OnAurora, 0xFF0B1020);
        glassSurface = color(context, R.attr.m4GlassSurface, 0x8CFFFFFF);
        glassBorderStart = color(context, R.attr.m4GlassBorderStart, 0xCCFFFFFF);
        glassBorderEnd = color(context, R.attr.m4GlassBorderEnd, 0x667C4DFF);
        innerGlow = color(context, R.attr.m4InnerGlow, 0x337C4DFF);
        success = color(context, R.attr.m4Success, 0xFF146C2E);
        warning = color(context, R.attr.m4Warning, 0xFF7A5900);
        primary = color(context, androidx.appcompat.R.attr.colorPrimary, 0xFF4F46E5);
        onPrimary = color(context, com.google.android.material.R.attr.colorOnPrimary, 0xFFFFFFFF);
        secondary = color(context, com.google.android.material.R.attr.colorSecondary, 0xFF0E7490);
        tertiary = color(context, com.google.android.material.R.attr.colorTertiary, 0xFFBE185D);
        surface = color(context, com.google.android.material.R.attr.colorSurface, 0xFFFDFBFF);
        onSurface = color(context, com.google.android.material.R.attr.colorOnSurface, 0xFF1C1B1F);
        outline = color(context, com.google.android.material.R.attr.colorOutline, 0xFF79747E);

        elevation1 = dimension(context, R.attr.m4Elevation1, 1f);
        elevation2 = dimension(context, R.attr.m4Elevation2, 2f);
        elevation3 = dimension(context, R.attr.m4Elevation3, 4f);
        elevation4 = dimension(context, R.attr.m4Elevation4, 8f);
        elevation5 = dimension(context, R.attr.m4Elevation5, 16f);
        borderWidth = dimension(context, R.attr.m4BorderWidth, 1f);
        glowWidth = dimension(context, R.attr.m4GlowWidth, 12f);

        radiusSmall = dimension(context, R.attr.m4RadiusSmall, 10f);
        radiusMedium = dimension(context, R.attr.m4RadiusMedium, 16f);
        radiusLarge = dimension(context, R.attr.m4RadiusLarge, 24f);
        radiusExtraLarge = dimension(context, R.attr.m4RadiusExtraLarge, 32f);
        superellipse = bool(context, R.attr.m4Superellipse, true);
        morphOnPress = bool(context, R.attr.m4MorphOnPress, true);

        motionShort = integer(context, R.attr.m4MotionShort, 140);
        motionMedium = integer(context, R.attr.m4MotionMedium, 260);
        motionLong = integer(context, R.attr.m4MotionLong, 420);
        springStiffness = floatValue(context, R.attr.m4SpringStiffness, 420f);
        springDamping = floatValue(context, R.attr.m4SpringDamping, 0.78f);
        reducedMotion = bool(context, R.attr.m4ReducedMotion, false);

        touchTarget = dimension(context, R.attr.m4TouchTarget, 48f);
        hapticsEnabled = bool(context, R.attr.m4HapticsEnabled, true);
        highContrast = bool(context, R.attr.m4HighContrast, false);
        comparisonMode = bool(context, R.attr.m4ComparisonMode, false);

        displayTextAppearance = style(context, R.attr.m4DisplayTextAppearance,
                R.style.TextAppearance_Material4_Display);
        headlineTextAppearance = style(context, R.attr.m4HeadlineTextAppearance,
                R.style.TextAppearance_Material4_Headline);
        titleTextAppearance = style(context, R.attr.m4TitleTextAppearance,
                R.style.TextAppearance_Material4_Title);
        bodyTextAppearance = style(context, R.attr.m4BodyTextAppearance,
                R.style.TextAppearance_Material4_Body);
        labelTextAppearance = style(context, R.attr.m4LabelTextAppearance,
                R.style.TextAppearance_Material4_Label);
    }

    @NonNull
    public static Material4Tokens from(@NonNull Context context) {
        return new Material4Tokens(context);
    }

    public boolean isDark() {
        return !MaterialColors.isColorLight(surface);
    }

    /** Resolves a colour attribute, returning {@code fallback} when the theme has no value. */
    @ColorInt
    public static int color(@NonNull Context context, @AttrRes int attr, @ColorInt int fallback) {
        Integer value = MaterialColors.getColorOrNull(context, attr);
        return value == null ? fallback : value;
    }

    /** Resolves a dimension attribute in pixels, returning a dp fallback. */
    public static float dimension(@NonNull Context context, @AttrRes int attr, float fallbackDp) {
        TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(attr, value, true)) {
            return dp(context, fallbackDp);
        }
        if (value.type == TypedValue.TYPE_DIMENSION) {
            return TypedValue.complexToDimension(value.data, context.getResources().getDisplayMetrics());
        }
        if (value.type == TypedValue.TYPE_FLOAT) {
            return value.getFloat() * context.getResources().getDisplayMetrics().density;
        }
        if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
            return dp(context, value.data);
        }
        return dp(context, fallbackDp);
    }

    public static int integer(@NonNull Context context, @AttrRes int attr, int fallback) {
        TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(attr, value, true)) {
            return fallback;
        }
        if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
            return value.data;
        }
        return fallback;
    }

    public static float floatValue(@NonNull Context context, @AttrRes int attr, float fallback) {
        TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(attr, value, true)) {
            return fallback;
        }
        if (value.type == TypedValue.TYPE_FLOAT) {
            return value.getFloat();
        }
        if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
            return value.data;
        }
        return fallback;
    }

    public static boolean bool(@NonNull Context context, @AttrRes int attr, boolean fallback) {
        TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(attr, value, true)) {
            return fallback;
        }
        if (value.type == TypedValue.TYPE_INT_BOOLEAN) {
            return value.data != 0;
        }
        return fallback;
    }

    @StyleRes
    public static int style(@NonNull Context context, @AttrRes int attr, @StyleRes int fallback) {
        TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(attr, value, true)) {
            return fallback;
        }
        return value.resourceId != 0 ? value.resourceId : fallback;
    }

    public static float dp(@NonNull Context context, float value) {
        return value * context.getResources().getDisplayMetrics().density;
    }

    public static int dpi(@NonNull Context context, float value) {
        return Math.round(dp(context, value));
    }
}
