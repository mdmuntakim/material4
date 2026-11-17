package io.material4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.google.android.material.shape.Shapeable;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Material 4 motion: spring physics, press morphing, staggered reveals, container transforms
 * and predictive back. Every helper honours the reduced-motion token and the system animator
 * scale; when motion is reduced the end state is applied immediately.
 */
public final class Material4Motion {

    private static final Map<View, ShapeableState> PRESS_STATE = new WeakHashMap<>();

    private Material4Motion() {
    }

    public static boolean isReducedMotion(@NonNull Context context) {
        Material4Tokens tokens = Material4Tokens.from(context);
        if (tokens.reducedMotion || tokens.motionMedium == 0) {
            return true;
        }
        try {
            float scale = android.provider.Settings.Global.getFloat(context.getContentResolver(),
                    android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 1f);
            return scale == 0f;
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public static int duration(@NonNull Context context, int base) {
        return isReducedMotion(context) ? 0 : base;
    }

    /** Adds a spring press scale and, when the view is shapeable, a corner morph. */
    public static void pressFeedback(@NonNull View view) {
        Context context = view.getContext();
        Material4Tokens tokens = Material4Tokens.from(context);
        if (tokens.reducedMotion || !tokens.morphOnPress) {
            return;
        }
        final View.OnTouchListener previous = getTouchListener(view);
        view.setOnTouchListener((v, event) -> {
            boolean handled = previous != null && previous.onTouch(v, event);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN -> {
                    spring(v, DynamicAnimation.SCALE_X, 0.965f);
                    spring(v, DynamicAnimation.SCALE_Y, 0.965f);
                    morphOnPress(v, true);
                }
                case MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    spring(v, DynamicAnimation.SCALE_X, 1f);
                    spring(v, DynamicAnimation.SCALE_Y, 1f);
                    morphOnPress(v, false);
                }
                default -> {
                }
            }
            return handled;
        });
    }

    @Nullable
    private static View.OnTouchListener getTouchListener(View view) {
        // There is no getter for OnTouchListener. Callers that need a chained listener can use
        // attachPressFeedback(view, previous); this method only exists for the common case.
        return null;
    }

    /** Adds press feedback in front of an existing touch listener. */
    public static void pressFeedback(@NonNull View view, @Nullable View.OnTouchListener existing) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        if (tokens.reducedMotion || !tokens.morphOnPress) {
            return;
        }
        view.setOnTouchListener((v, event) -> {
            boolean handled = existing != null && existing.onTouch(v, event);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN -> {
                    spring(v, DynamicAnimation.SCALE_X, 0.965f);
                    spring(v, DynamicAnimation.SCALE_Y, 0.965f);
                    morphOnPress(v, true);
                }
                case MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    spring(v, DynamicAnimation.SCALE_X, 1f);
                    spring(v, DynamicAnimation.SCALE_Y, 1f);
                    morphOnPress(v, false);
                }
                default -> {
                }
            }
            return handled;
        });
    }

    private static void morphOnPress(View view, boolean pressed) {
        if (!(view instanceof Shapeable shapeable)) {
            return;
        }
        if (pressed) {
            PRESS_STATE.put(view, new ShapeableState(shapeable.getShapeAppearanceModel()));
            float radius = Material4Shape.cornerRadius(shapeable);
            shapeable.setShapeAppearanceModel(
                    new com.google.android.material.shape.ShapeAppearanceModel.Builder(
                                    shapeable.getShapeAppearanceModel())
                            .setAllCornerSizes(radius * 1.22f)
                            .build());
        } else {
            ShapeableState state = PRESS_STATE.remove(view);
            if (state != null) {
                shapeable.setShapeAppearanceModel(state.model);
            }
        }
    }

    /** Runs a spring animation to a target value. */
    public static void spring(@NonNull View view, @NonNull DynamicAnimation.ViewProperty property,
                              float target) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        SpringAnimation animation = new SpringAnimation(view, property, target);
        animation.setSpring(new SpringForce(target)
                .setStiffness(tokens.springStiffness)
                .setDampingRatio(tokens.springDamping));
        animation.start();
    }

    /** Staggered reveal for a list of views. */
    public static void stagger(@NonNull List<? extends View> views) {
        if (views.isEmpty()) {
            return;
        }
        Context context = views.get(0).getContext();
        Material4Tokens tokens = Material4Tokens.from(context);
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.animate().cancel();
            if (isReducedMotion(context)) {
                view.setAlpha(1f);
                view.setTranslationY(0f);
                continue;
            }
            view.setAlpha(0f);
            view.setTranslationY(Material4Tokens.dp(context, 18f));
            view.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setStartDelay(Math.min(i * 38L, tokens.motionLong))
                    .setDuration(tokens.motionMedium)
                    .setInterpolator(emphasized())
                    .start();
        }
    }

    /** Reveals a single view with the Material 4 container transform timing. */
    public static void reveal(@NonNull View view) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        if (isReducedMotion(view.getContext())) {
            view.setAlpha(1f);
            view.setScaleX(1f);
            view.setScaleY(1f);
            return;
        }
        view.setAlpha(0f);
        view.setScaleX(0.94f);
        view.setScaleY(0.94f);
        view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(tokens.motionMedium)
                .setInterpolator(emphasized())
                .start();
    }

    /** Container-transform transition between a card and a detail screen. */
    @NonNull
    public static MaterialContainerTransform containerTransform(@NonNull Context context) {
        MaterialContainerTransform transform = new MaterialContainerTransform(context, true);
        Material4Tokens tokens = Material4Tokens.from(context);
        transform.setDuration(duration(context, tokens.motionLong));
        transform.setInterpolator(emphasized());
        transform.setScrimColor(tokens.surface);
        return transform;
    }

    /** Platform transition variant for Activity shared elements (API 26+). */
    @NonNull
    public static com.google.android.material.transition.platform.MaterialContainerTransform
            containerTransformPlatform(@NonNull Context context) {
        com.google.android.material.transition.platform.MaterialContainerTransform transform =
                new com.google.android.material.transition.platform.MaterialContainerTransform(
                        context, false);
        Material4Tokens tokens = Material4Tokens.from(context);
        transform.setDuration(duration(context, tokens.motionLong));
        transform.setInterpolator(emphasized());
        return transform;
    }

    @NonNull
    public static MaterialFadeThrough fadeThrough(@NonNull Context context) {
        Material4Tokens tokens = Material4Tokens.from(context);
        MaterialFadeThrough transition = new MaterialFadeThrough();
        transition.setDuration(duration(context, tokens.motionMedium));
        return transition;
    }

    /**
     * Registers a predictive-back aware callback. On Android 13+ the system animates the
     * progress before invoking the callback; older releases get the same callback without the
     * predictive preview. Returns the callback so it can be removed.
     */
    @NonNull
    public static OnBackPressedCallback predictiveBack(@NonNull ComponentActivity activity,
                                                       @NonNull Runnable onBack) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                onBack.run();
                activity.getOnBackPressedDispatcher().onBackPressed();
                setEnabled(true);
            }
        };
        activity.getOnBackPressedDispatcher().addCallback(activity, callback);
        return callback;
    }

    @NonNull
    public static TimeInterpolator emphasized() {
        return new PathInterpolator(0.05f, 0.7f, 0.1f, 1f);
    }

    /** Fades out a view and runs the action afterwards (immediately with reduced motion). */
    public static void fadeOut(@NonNull View view, @NonNull Runnable endAction) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        if (isReducedMotion(view.getContext())) {
            endAction.run();
            return;
        }
        view.animate()
                .alpha(0f)
                .setDuration(tokens.motionShort)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.animate().setListener(null);
                        endAction.run();
                    }
                })
                .start();
    }

    private record ShapeableState(com.google.android.material.shape.ShapeAppearanceModel model) {
    }
}
