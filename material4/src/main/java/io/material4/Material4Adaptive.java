package io.material4;

import android.content.Context;
import android.graphics.Rect;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.window.layout.DisplayFeature;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Window size classes and foldable awareness. The sample uses this to swap a navigation bar for
 * a rail and to switch between one-pane and two-pane catalogue layouts.
 *
 * <p>Window layout is polled after a decor-view layout change instead of collecting a coroutine
 * Flow, which keeps the library usable from plain Java without a coroutine dependency.
 */
public final class Material4Adaptive {

    public enum SizeClass {
        COMPACT,
        MEDIUM,
        EXPANDED
    }

    private Material4Adaptive() {
    }

    @NonNull
    public static SizeClass sizeClass(@NonNull Context context) {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(context);
        return sizeClass(metrics, context);
    }

    @NonNull
    public static SizeClass sizeClass(@NonNull WindowMetrics metrics, @NonNull Context context) {
        Rect bounds = metrics.getBounds();
        int widthDp = Math.round(bounds.width() / context.getResources().getDisplayMetrics().density);
        if (widthDp >= 840) {
            return SizeClass.EXPANDED;
        }
        if (widthDp >= 600) {
            return SizeClass.MEDIUM;
        }
        return SizeClass.COMPACT;
    }

    public static boolean isTablet(@NonNull Context context) {
        return sizeClass(context) != SizeClass.COMPACT;
    }

    public static boolean isTwoPane(@NonNull Context context) {
        return sizeClass(context) == SizeClass.EXPANDED;
    }

    /** Reports the current layout immediately and after every decor-view layout change. */
    public static void observe(@NonNull ComponentActivity activity, @NonNull Listener listener) {
        listener.onLayout(snapshot(activity));
        activity.getWindow().getDecorView().addOnLayoutChangeListener(
                (view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                        listener.onLayout(snapshot(activity)));
    }

    @NonNull
    private static LayoutInfo snapshot(@NonNull ComponentActivity activity) {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(activity);
        List<Rect> folds = new ArrayList<>();
        boolean hinge = false;
        boolean separating = false;
        WindowLayoutInfo info = WindowInfoTracker.getOrCreate(activity)
                .getCurrentWindowLayoutInfo(activity);
        for (DisplayFeature feature : info.getDisplayFeatures()) {
            if (feature instanceof FoldingFeature fold) {
                folds.add(fold.getBounds());
                hinge = true;
                separating = fold.isSeparating();
            }
        }
        return new LayoutInfo(sizeClass(metrics, activity), metrics.getBounds(), folds, hinge,
                separating);
    }

    public interface Listener {
        void onLayout(@NonNull LayoutInfo info);
    }

    /** Immutable layout snapshot delivered to {@link Listener}. */
    public static final class LayoutInfo {
        @NonNull public final SizeClass sizeClass;
        @NonNull public final Rect windowBounds;
        @NonNull public final List<Rect> displayFeatures;
        public final boolean hinge;
        public final boolean separatingFold;

        LayoutInfo(@NonNull SizeClass sizeClass, @NonNull Rect windowBounds,
                   @NonNull List<Rect> displayFeatures, boolean hinge, boolean separatingFold) {
            this.sizeClass = sizeClass;
            this.windowBounds = windowBounds;
            this.displayFeatures = displayFeatures;
            this.hinge = hinge;
            this.separatingFold = separatingFold;
        }

        public boolean hasHinge() {
            return hinge || !displayFeatures.isEmpty();
        }
    }

    /** Convenience for a two-pane layout: returns a safe pane width in pixels. */
    public static int paneWidth(@NonNull Context context, int windowWidthPx, int gapPx) {
        return Math.max(0, (windowWidthPx - gapPx) / 2);
    }

    @Nullable
    public static Rect hingeBounds(@NonNull WindowLayoutInfo info) {
        for (DisplayFeature feature : info.getDisplayFeatures()) {
            if (feature instanceof FoldingFeature fold) {
                return fold.getBounds();
            }
        }
        return null;
    }
}
