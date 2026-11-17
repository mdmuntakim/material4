package io.material4;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Material 4 typography. The expressive display/headline scale comes from theme text
 * appearances; {@link #variableTypeface} adds optical sizing for apps that ship a variable font
 * (API 26+ supports font variation axes).
 */
public final class Material4Type {

    public enum Role {
        DISPLAY,
        HEADLINE,
        TITLE,
        BODY,
        LABEL
    }

    private Material4Type() {
    }

    public static void apply(@NonNull TextView view, @NonNull Role role) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        int appearance = switch (role) {
            case DISPLAY -> tokens.displayTextAppearance;
            case HEADLINE -> tokens.headlineTextAppearance;
            case TITLE -> tokens.titleTextAppearance;
            case BODY -> tokens.bodyTextAppearance;
            case LABEL -> tokens.labelTextAppearance;
        };
        view.setTextAppearance(appearance);
    }

    /**
     * Builds a typeface with variable-font axes from an app supplied font. Returns null when the
     * font cannot be loaded; callers then keep the theme typeface.
     */
    @Nullable
    public static Typeface variableTypeface(@NonNull AssetManager assets, @NonNull String path,
                                            float weight, float opticalSize, float width) {
        try {
            Typeface.Builder builder = new Typeface.Builder(assets, path)
                    .setFontVariationSettings(new android.graphics.fonts.FontVariationAxis[]{
                            new android.graphics.fonts.FontVariationAxis("wght", weight),
                            new android.graphics.fonts.FontVariationAxis("opsz", opticalSize),
                            new android.graphics.fonts.FontVariationAxis("wdth", width)
                    });
            return builder.build();
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    /** Applies the display scale plus optical sizing metadata used by the sample headers. */
    public static void display(@NonNull TextView view) {
        apply(view, Role.DISPLAY);
        view.setLetterSpacing(-0.02f);
    }

    /** Uses sp so large-text accessibility settings scale the whole catalogue. */
    public static void body(@NonNull TextView view) {
        apply(view, Role.BODY);
    }

    public static float scaledSize(@NonNull Context context, float sp) {
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }
}
