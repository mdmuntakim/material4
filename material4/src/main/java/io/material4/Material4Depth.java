package io.material4;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Translucent "glass" panel: tonal fill, gradient border and an inner glow. On API 31+ a dialog
 * window can additionally use a real render-effect backdrop blur; the panel itself always keeps
 * an opaque-enough scrim so contrast survives devices without blur support.
 */
public final class Material4Depth {

    private Material4Depth() {
    }

    public static void applyGlass(@NonNull View view, @NonNull Material4Tokens tokens,
                                  float radiusDp) {
        Material4GlassDrawable drawable = new Material4GlassDrawable(tokens,
                Material4Tokens.dp(view.getContext(), radiusDp), tokens.borderWidth, tokens.glowWidth);
        view.setBackground(drawable);
        view.setClipToOutline(false);
    }

    /** Applies elevation 1..5 from the Material 4 scale. */
    public static void applyElevation(@NonNull View view, @NonNull Material4Tokens tokens, int level) {
        float elevation = switch (level) {
            case 1 -> tokens.elevation1;
            case 2 -> tokens.elevation2;
            case 3 -> tokens.elevation3;
            case 4 -> tokens.elevation4;
            default -> tokens.elevation5;
        };
        view.setElevation(elevation);
    }

    /**
     * Requests a real background blur for a dialog on API 31+, and a dim scrim fallback on older
     * releases. Returns true when the platform blur was applied.
     */
    public static boolean applyDialogBlur(@Nullable Dialog dialog) {
        if (dialog == null) {
            return false;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            window.setBackgroundBlurRadius(Material4Tokens.dpi(dialog.getContext(), 28));
            return true;
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.45f;
        window.setAttributes(params);
        return false;
    }

    /** Gradient-bordered glass panel drawable. */
    public static final class Material4GlassDrawable extends Drawable {
        private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path clip = new Path();
        private final RectF rect = new RectF();
        private final float radius;
        private final float borderWidth;
        private final float glowWidth;
        private final Material4Tokens tokens;

        public Material4GlassDrawable(@NonNull Material4Tokens tokens, float radiusPx,
                                      float borderWidthPx, float glowWidthPx) {
            this.tokens = tokens;
            this.radius = radiusPx;
            this.borderWidth = borderWidthPx;
            this.glowWidth = glowWidthPx;
            fill.setStyle(Paint.Style.FILL);
            border.setStyle(Paint.Style.STROKE);
            border.setStrokeWidth(Math.max(borderWidthPx, 1f));
        }

        @Override
        protected void onBoundsChange(@NonNull Rect bounds) {
            rect.set(bounds);
            fill.setShader(new LinearGradient(0, 0, 0, bounds.height(),
                    tokens.glassSurface, tokens.glassSurface, Shader.TileMode.CLAMP));
            border.setShader(new LinearGradient(0, 0, bounds.width(), bounds.height(),
                    tokens.glassBorderStart, tokens.glassBorderEnd, Shader.TileMode.CLAMP));
            glow.setShader(new RadialGradient(bounds.width() * 0.15f, 0f,
                    Math.max(bounds.width() * 0.7f, glowWidth), tokens.innerGlow,
                    Color.TRANSPARENT, Shader.TileMode.CLAMP));
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (rect.isEmpty()) {
                return;
            }
            clip.reset();
            clip.addRoundRect(rect, radius, radius, Path.Direction.CW);
            int save = canvas.save();
            canvas.clipPath(clip);
            canvas.drawRect(rect, fill);
            canvas.drawRect(rect, glow);
            canvas.restoreToCount(save);
            if (borderWidth > 0f) {
                rect.inset(borderWidth / 2f, borderWidth / 2f);
                canvas.drawRoundRect(rect, radius, radius, border);
                rect.inset(-borderWidth / 2f, -borderWidth / 2f);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            fill.setAlpha(alpha);
            border.setAlpha(alpha);
            glow.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            fill.setColorFilter(colorFilter);
            border.setColorFilter(colorFilter);
            glow.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override
        @SuppressWarnings("deprecation")
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
