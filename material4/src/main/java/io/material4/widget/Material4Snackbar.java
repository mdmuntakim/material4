package io.material4.widget;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import io.material4.Material4Tokens;

/**
 * Material 4 snackbar factory. Snackbar's constructor is not public, so the wrapper creates a
 * Material 3 snackbar and applies the Material 4 tokens.
 */
public final class Material4Snackbar {

    private Material4Snackbar() {
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        snackbar.setBackgroundTint(tokens.highContrast ? tokens.surface : tokens.onSurface);
        snackbar.setTextColor(tokens.surface);
        snackbar.setActionTextColor(tokens.auroraMid);
        return snackbar;
    }

    @NonNull
    public static Snackbar show(@NonNull View view, @NonNull CharSequence text) {
        Snackbar snackbar = make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    @NonNull
    public static Snackbar action(@NonNull View view, @NonNull CharSequence text,
                                  @NonNull CharSequence action, @NonNull View.OnClickListener listener) {
        Snackbar snackbar = make(view, text, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.show();
        return snackbar;
    }
}
