package io.material4;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.Shapeable;

/**
 * Material 4 shape helpers: morphing corner sizes and an optional superellipse ("squircle")
 * corner treatment built on the public Material shape API.
 */
public final class Material4Shape {

    private Material4Shape() {
    }

    /** A rounded rectangle whose corners use a cubic superellipse approximation. */
    @NonNull
    public static ShapeAppearanceModel squircle(float radius) {
        ShapeAppearanceModel.Builder builder = ShapeAppearanceModel.builder();
        builder.setAllCorners(new SquircleCornerTreatment(radius));
        builder.setAllCornerSizes(radius);
        return builder.build();
    }

    /** Applies the token shape to any Material {@link Shapeable}. */
    public static void apply(@NonNull Shapeable shapeable, @NonNull Context context, float radiusDp) {
        Material4Tokens tokens = Material4Tokens.from(context);
        float radius = Material4Tokens.dp(context, radiusDp);
        if (tokens.superellipse && !tokens.highContrast) {
            shapeable.setShapeAppearanceModel(squircle(radius));
        } else {
            shapeable.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                    .setAllCornerSizes(radius)
                    .build());
        }
    }

    /** Smoothly morphs the corner size of a shapeable view. */
    public static void animateCorners(@NonNull Shapeable shapeable, float fromPx, float toPx,
                                      int duration, boolean reducedMotion) {
        if (reducedMotion || duration <= 0) {
            shapeable.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                    .setAllCornerSizes(toPx)
                    .build());
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(fromPx, toPx);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> shapeable.setShapeAppearanceModel(
                ShapeAppearanceModel.builder()
                        .setAllCornerSizes((float) animation.getAnimatedValue())
                        .build()));
        animator.start();
    }

    /** Returns the current top-left corner radius in pixels. */
    public static float cornerRadius(@NonNull Shapeable shapeable) {
        ShapeAppearanceModel model = shapeable.getShapeAppearanceModel();
        CornerSize size = model.getTopLeftCornerSize();
        return size.getCornerSize(new RectF(0, 0, 1000, 1000));
    }

    /**
     * A superellipse corner. A cubic Bezier with a 0.5/0.15 control point distribution is a
     * close, cheap approximation of a squircle and keeps the shape compatible with Material's
     * ShapePath pipeline (no bitmap, no custom view outline).
     */
    public static final class SquircleCornerTreatment extends com.google.android.material.shape.CornerTreatment {
        private final float radius;

        public SquircleCornerTreatment(float radius) {
            this.radius = radius;
        }

        @Override
        public void getCornerPath(@NonNull ShapePath path, float angle, float interpolation,
                                  float radius) {
            float r = Math.min(this.radius, radius);
            path.reset(0f, r);
            path.lineTo(0f, r * 0.52f);
            path.cubicToPoint(0f, r * 0.16f, r * 0.16f, 0f, r * 0.52f, 0f);
            path.lineTo(r, 0f);
        }
    }
}
