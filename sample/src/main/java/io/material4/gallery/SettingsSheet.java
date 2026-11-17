package io.material4.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import io.material4.Material4Theme;
import io.material4.Material4ThemeConfig;
import io.material4.Material4Tokens;
import io.material4.widget.Material4BottomSheetDialog;
import io.material4.widget.Material4GradientTextView;
import io.material4.widget.MaterialButton;
import io.material4.widget.MaterialButtonToggleGroup;
import io.material4.widget.MaterialCardView;
import io.material4.widget.MaterialDivider;
import io.material4.widget.MaterialSwitch;
import io.material4.widget.MaterialTextView;

/** Global token controls shown in a Material 4 bottom sheet. */
final class SettingsSheet {

    private SettingsSheet() {
    }

    static void show(@NonNull Activity activity, @NonNull Material4ThemeConfig config,
                     @NonNull Runnable changed) {
        Material4Tokens tokens = Material4Tokens.from(activity);
        LinearLayout content = Ui.column(activity);
        content.setPadding(Ui.dp(activity, 22), Ui.dp(activity, 18),
                Ui.dp(activity, 22), Ui.dp(activity, 28));

        Material4GradientTextView title = Ui.gradientTitle(activity, "Material 4 controls");
        content.addView(title);
        content.addView(Ui.caption(activity, "Every control updates the theme overlay and "
                + "recreates the catalogue. No screen styles a component by hand."));
        content.addView(Ui.spacer(activity, 14));

        content.addView(Ui.label(activity, "APPEARANCE"));
        MaterialButtonToggleGroup appearance = new MaterialButtonToggleGroup(activity);
        appearance.setSingleSelection(true);
        appearance.setSelectionRequired(true);
        String[] labels = {"Auto", "Light", "Dark"};
        int checkedIndex = config.nightMode == Material4ThemeConfig.MODE_LIGHT ? 1
                : config.nightMode == Material4ThemeConfig.MODE_DARK ? 2 : 0;
        for (int i = 0; i < labels.length; i++) {
            MaterialButton button = Ui.outlinedButton(activity, labels[i]);
            button.setId(View.generateViewId());
            appearance.addView(button);
            if (i == checkedIndex) {
                appearance.check(button.getId());
            }
        }
        appearance.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) {
                return;
            }
            int index = group.indexOfChild(group.findViewById(checkedId));
            config.nightMode = index == 1 ? Material4ThemeConfig.MODE_LIGHT
                    : index == 2 ? Material4ThemeConfig.MODE_DARK
                    : Material4ThemeConfig.MODE_AUTO;
            changed.run();
        });
        content.addView(appearance);
        content.addView(divider(activity));

        MaterialSwitch dynamic = toggle(activity, "Dynamic colour (wallpaper)",
                config.dynamicColour);
        dynamic.setOnCheckedChangeListener((button, checked) -> {
            config.dynamicColour = checked;
            changed.run();
        });
        content.addView(dynamic);
        content.addView(Ui.caption(activity, Material4Theme.dynamicColourAvailable()
                ? "Android 12+ extracts a tonal palette from the wallpaper."
                : "This device is below Android 12; the static aurora palette is used."));

        content.addView(Ui.spacer(activity, 10));
        content.addView(Ui.label(activity, "SEED COLOUR"));
        LinearLayout seeds = Ui.row(activity);
        int[][] swatches = {
                {android.graphics.Color.parseColor("#FF4F46E5"), 0},
                {android.graphics.Color.parseColor("#FF0E7490"), 0},
                {android.graphics.Color.parseColor("#FFBE185D"), 0},
                {android.graphics.Color.parseColor("#FF2E7D32"), 0},
                {android.graphics.Color.parseColor("#FFB45309"), 0},
                {android.graphics.Color.parseColor("#FF7E22CE"), 0},
        };
        for (int i = 0; i < swatches.length; i++) {
            final int seed = swatches[i][0];
            MaterialButton swatch = new MaterialButton(activity, null,
                    com.google.android.material.R.attr.materialButtonOutlinedStyle);
            swatch.setText("");
            swatch.setContentDescription("Seed colour " + (i + 1));
            swatch.setBackgroundTintList(ColorStateList.valueOf(seed));
            swatch.setMinWidth(Ui.dp(activity, 42));
            swatch.setMinHeight(Ui.dp(activity, 42));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    Ui.dp(activity, 42), Ui.dp(activity, 42));
            params.setMarginEnd(Ui.dp(activity, 8));
            swatch.setLayoutParams(params);
            if (config.seedColour != null && config.seedColour == seed) {
                swatch.setStrokeWidth(Ui.dp(activity, 3));
                swatch.setStrokeColor(ColorStateList.valueOf(tokens.onSurface));
            }
            swatch.setOnClickListener(view -> {
                config.seedColour = seed;
                config.dynamicColour = true;
                changed.run();
            });
            seeds.addView(swatch);
        }
        content.addView(seeds);
        content.addView(Ui.spacer(activity, 8));
        MaterialButton wallpaper = Ui.textButton(activity, "Use wallpaper colour");
        wallpaper.setOnClickListener(view -> {
            config.seedColour = null;
            config.dynamicColour = true;
            changed.run();
        });
        content.addView(wallpaper);
        content.addView(divider(activity));

        content.addView(Ui.label(activity, "ACCESSIBILITY"));
        MaterialSwitch contrast = toggle(activity, "High contrast", config.highContrast);
        contrast.setOnCheckedChangeListener((button, checked) -> {
            config.highContrast = checked;
            changed.run();
        });
        content.addView(contrast);
        content.addView(Ui.caption(activity, Material4Theme.highContrastAvailable()
                ? "Uses Material's high-contrast overlay plus the M4 outline tokens."
                : "Uses the Material 4 high-contrast overlay."));

        MaterialSwitch motion = toggle(activity, "Reduced motion", config.reducedMotion);
        motion.setOnCheckedChangeListener((button, checked) -> {
            config.reducedMotion = checked;
            changed.run();
        });
        content.addView(motion);
        content.addView(divider(activity));

        content.addView(Ui.label(activity, "COMPARISON"));
        MaterialSwitch comparison = toggle(activity, "Material 3 ↔ Material 4",
                config.comparisonMode);
        comparison.setOnCheckedChangeListener((button, checked) -> {
            config.comparisonMode = checked;
            changed.run();
        });
        content.addView(comparison);
        content.addView(Ui.caption(activity, "Material 3 baseline removes the M4 shape, depth, "
                + "motion and haptic tokens to show the delta."));

        Material4BottomSheetDialog dialog = new Material4BottomSheetDialog(activity);
        dialog.showContent(content);
    }

    private static MaterialSwitch toggle(Context context, String label, boolean checked) {
        MaterialSwitch toggle = new MaterialSwitch(context);
        toggle.setText(label);
        toggle.setChecked(checked);
        return toggle;
    }

    private static View divider(Context context) {
        MaterialDivider divider = new MaterialDivider(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(context, 1));
        params.topMargin = Ui.dp(context, 12);
        params.bottomMargin = Ui.dp(context, 12);
        divider.setLayoutParams(params);
        return divider;
    }
}
