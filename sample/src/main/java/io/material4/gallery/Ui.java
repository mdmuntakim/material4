package io.material4.gallery;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Depth;
import io.material4.Material4Tokens;
import io.material4.widget.Material4GradientTextView;
import io.material4.widget.MaterialButton;
import io.material4.widget.MaterialCardView;
import io.material4.widget.MaterialDivider;
import io.material4.widget.MaterialTextView;

/** Small view builders used by the catalogue. All styling comes from theme overlays. */
final class Ui {

    private Ui() {
    }

    static int dp(@NonNull Context context, float value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    @NonNull
    static LinearLayout column(@NonNull Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    @NonNull
    static LinearLayout row(@NonNull Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        return layout;
    }

    @NonNull
    static LinearLayout.LayoutParams lp(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    @NonNull
    static MaterialTextView text(@NonNull Context context, @Nullable CharSequence value) {
        MaterialTextView text = new MaterialTextView(context);
        text.setText(value);
        Material4Tokens tokens = Material4Tokens.from(context);
        text.setTextAppearance(tokens.bodyTextAppearance);
        text.setTextColor(tokens.onSurface);
        return text;
    }

    @NonNull
    static MaterialTextView title(@NonNull Context context, @Nullable CharSequence value) {
        MaterialTextView text = new MaterialTextView(context);
        text.setText(value);
        Material4Tokens tokens = Material4Tokens.from(context);
        text.setTextAppearance(tokens.titleTextAppearance);
        text.setTextColor(tokens.onSurface);
        return text;
    }

    @NonNull
    static MaterialTextView label(@NonNull Context context, @Nullable CharSequence value) {
        MaterialTextView text = new MaterialTextView(context);
        text.setText(value);
        Material4Tokens tokens = Material4Tokens.from(context);
        text.setTextAppearance(tokens.labelTextAppearance);
        text.setTextColor(tokens.primary);
        return text;
    }

    @NonNull
    static Material4GradientTextView gradientTitle(@NonNull Context context,
                                                   @Nullable CharSequence value) {
        Material4GradientTextView text = new Material4GradientTextView(context);
        text.setText(value);
        text.setTextSize(30f);
        text.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return text;
    }

    @NonNull
    static MaterialTextView code(@NonNull Context context, @NonNull String value) {
        MaterialTextView text = new MaterialTextView(context);
        text.setText(value);
        text.setTextAppearance(io.material4.R.style.TextAppearance_Material4_Code);
        Material4Tokens tokens = Material4Tokens.from(context);
        text.setTextColor(tokens.onSurface);
        text.setPadding(dp(context, 14), dp(context, 12), dp(context, 14), dp(context, 12));
        text.setBackgroundColor(tokens.isDark() ? 0x33000000 : 0x0D000000);
        text.setTextIsSelectable(true);
        return text;
    }

    @NonNull
    static MaterialCardView card(@NonNull Context context) {
        MaterialCardView card = new MaterialCardView(context);
        card.setCardBackgroundColor(Material4Tokens.from(context).surface);
        card.setStrokeWidth(1);
        card.setRadius(dp(context, 20));
        int pad = dp(context, 18);
        card.setContentPadding(pad, pad, pad, pad);
        card.setUseCompatPadding(true);
        return card;
    }

    @NonNull
    static MaterialCardView glassCard(@NonNull Context context) {
        MaterialCardView card = card(context);
        Material4Tokens tokens = Material4Tokens.from(context);
        Material4Depth.applyGlass(card, tokens, 20f);
        card.setCardBackgroundColor(0x00000000);
        card.setStrokeWidth(0);
        return card;
    }

    @NonNull
    static MaterialCardView snippet(@NonNull Context context, @NonNull String code) {
        MaterialCardView card = card(context);
        Material4Tokens tokens = Material4Tokens.from(context);
        card.setCardBackgroundColor(tokens.isDark() ? 0x6610141A : 0xFFFFFFFF);
        card.setStrokeColor(tokens.outline);
        LinearLayout content = column(context);
        content.addView(label(context, "CODE"));
        content.addView(spacer(context, 6));
        content.addView(code(context, code));
        card.addView(content);
        return card;
    }

    @NonNull
    static MaterialDivider divider(@NonNull Context context) {
        MaterialDivider divider = new MaterialDivider(context);
        LinearLayout.LayoutParams params = lp(ViewGroup.LayoutParams.MATCH_PARENT, dp(context, 1));
        params.topMargin = dp(context, 14);
        params.bottomMargin = dp(context, 14);
        divider.setLayoutParams(params);
        return divider;
    }

    @NonNull
    static View spacer(@NonNull Context context, float heightDp) {
        View view = new View(context);
        view.setLayoutParams(lp(1, dp(context, heightDp)));
        return view;
    }

    @NonNull
    static View horizontalSpacer(@NonNull Context context, float widthDp) {
        View view = new View(context);
        view.setLayoutParams(lp(dp(context, widthDp), 1));
        return view;
    }

    @NonNull
    static MaterialButton button(@NonNull Context context, @NonNull String label) {
        MaterialButton button = new MaterialButton(context);
        button.setText(label);
        return button;
    }

    @NonNull
    static MaterialButton outlinedButton(@NonNull Context context, @NonNull String label) {
        MaterialButton button = new MaterialButton(context, null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle);
        button.setText(label);
        return button;
    }

    @NonNull
    static MaterialButton tonalButton(@NonNull Context context, @NonNull String label) {
        MaterialButton button = new MaterialButton(context, null,
                com.google.android.material.R.attr.materialButtonTonalStyle);
        button.setText(label);
        return button;
    }

    @NonNull
    static MaterialButton textButton(@NonNull Context context, @NonNull String label) {
        MaterialButton button = new MaterialButton(context, null,
                androidx.appcompat.R.attr.borderlessButtonStyle);
        button.setText(label);
        return button;
    }

    @NonNull
    static TextView caption(@NonNull Context context, @NonNull CharSequence value) {
        TextView text = text(context, value);
        text.setAlpha(0.72f);
        text.setTextSize(12f);
        return text;
    }

    /** Adds a labelled sample block. */
    static void sample(@NonNull Context context, @NonNull LinearLayout parent,
                       @NonNull CharSequence label, @NonNull View content) {
        parent.addView(label(context, label));
        parent.addView(spacer(context, 6));
        parent.addView(content);
        parent.addView(spacer(context, 14));
    }

    /** A light/dark rendering strip for the current page. */
    static void lightDarkPreview(@NonNull Context context, @NonNull LinearLayout parent) {
        LinearLayout row = row(context);
        row.setLayoutParams(lp(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addPreviewPanel(context, row, R.style.Theme_Material4_PreviewLight, "LIGHT");
        addPreviewPanel(context, row, R.style.Theme_Material4_PreviewDark, "DARK");
        parent.addView(row);
        parent.addView(spacer(context, 14));
    }

    private static void addPreviewPanel(@NonNull Context context, @NonNull LinearLayout row,
                                        int theme, @NonNull String label) {
        Context themed = new ContextThemeWrapper(context, theme);
        LinearLayout panel = column(themed);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        params.setMarginEnd(dp(context, 8));
        panel.setLayoutParams(params);
        panel.setPadding(dp(context, 14), dp(context, 14), dp(context, 14), dp(context, 14));
        Material4Tokens tokens = Material4Tokens.from(themed);
        Material4Depth.applyGlass(panel, tokens, 16f);
        panel.addView(gradientTitle(themed, label));
        panel.addView(spacer(themed, 4));
        panel.addView(caption(themed, "Aa 0123 — tonal surface, aurora accent and outline"));
        row.addView(panel);
    }

    static void addDialogContent(@NonNull Context context, @NonNull LinearLayout content) {
        Material4Tokens tokens = Material4Tokens.from(context);
        content.setPadding(dp(context, 22), dp(context, 10), dp(context, 22), dp(context, 10));
        content.addView(text(context, "A Material 4 dialog uses the same builder, a translucent "
                + "surface and a background blur on Android 12+."));
        content.addView(spacer(context, 10));
        TextView ratio = label(context, "WCAG contrast enforced by tokens");
        ratio.setTextColor(tokens.success);
        content.addView(ratio);
    }

    static void setMargins(@NonNull View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams params) {
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        }
    }

    static void frame(@NonNull View view) {
        view.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
