package io.material4;

import android.animation.ValueAnimator;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Luminous Material 4 accent gradient. The angle slowly rotates when motion is enabled; with
 * reduced motion the gradient is drawn once as a static three-stop linear gradient.
 */
public final class Material4AuroraDrawable extends Drawable {

    private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path clip = new Path();
    private final RectF rect = new RectF();
    private final int[] colors;
    private final float radius;
    private final float borderWidth;
    private final boolean animated;
    private float angle = 45f;
    @Nullable private ValueAnimator animator;

    public Material4AuroraDrawable(@NonNull Material4Tokens tokens, float radiusPx,
                                   float borderWidthPx, boolean animated) {
        colors = new int[]{tokens.auroraStart, tokens.auroraMid, tokens.auroraEnd};
        radius = radiusPx;
        borderWidth = borderWidthPx;
        this.animated = animated && !tokens.reducedMotion;
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(Math.max(borderWidth, 1f));
        setAlpha(255);
    }

    @Override
    protected void onBoundsChange(@NonNull Rect bounds) {
        rect.set(bounds);
        rebuildShader();
    }

    private void rebuildShader() {
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        double radians = Math.toRadians(angle);
        float dx = (float) Math.cos(radians) * rect.width() / 2f;
        float dy = (float) Math.sin(radians) * rect.height() / 2f;
        fill.setShader(new LinearGradient(rect.centerX() - dx, rect.centerY() - dy,
                rect.centerX() + dx, rect.centerY() + dy,
                colors, null, Shader.TileMode.CLAMP));
        border.setShader(new LinearGradient(rect.left, rect.top, rect.right, rect.bottom,
                new int[]{Color.WHITE, colors[1], colors[2]}, null, Shader.TileMode.CLAMP));
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
        canvas.restoreToCount(save);
        if (borderWidth > 0f) {
            rect.inset(borderWidth / 2f, borderWidth / 2f);
            canvas.drawRoundRect(rect, radius, radius, border);
            rect.inset(-borderWidth / 2f, -borderWidth / 2f);
        }
    }

    public void start() {
        if (!animated || animator != null) {
            return;
        }
        animator = ValueAnimator.ofFloat(0f, 360f);
        animator.setDuration(14000L);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(animation -> {
            angle = (float) animation.getAnimatedValue();
            rebuildShader();
            invalidateSelf();
        });
        animator.start();
    }

    public void stop() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        if (visible) {
            start();
        } else {
            stop();
        }
        return super.setVisible(visible, restart);
    }

    @Override
    public void setAlpha(int alpha) {
        fill.setAlpha(alpha);
        border.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        fill.setColorFilter(colorFilter);
        border.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
