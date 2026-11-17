package io.material4.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;
import java.util.List;

import io.material4.Material4Adaptive;
import io.material4.Material4Motion;
import io.material4.Material4Tokens;
import io.material4.widget.*;

/**
 * Builds one catalogue page. Components are imported from {@code io.material4.widget.*}; no
 * page sets colours, shapes or motion timing on a Material 3 class directly.
 */
final class SectionFactory {

    private SectionFactory() {
    }

    static void build(@NonNull Activity activity, @NonNull Sections section,
                      @NonNull MaterialCardView card, boolean comparison) {
        Context context = activity;
        LinearLayout content = Ui.column(context);
        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (comparison) {
            TextView banner = Ui.label(context, "MATERIAL 3 ↔ MATERIAL 4 · baseline tokens active");
            banner.setTextColor(Material4Tokens.from(context).warning);
            content.addView(banner);
            content.addView(Ui.spacer(context, 8));
        }

        content.addView(Ui.text(context, section.description));
        content.addView(Ui.spacer(context, 14));

        switch (section) {
            case TOOLBAR -> toolbar(context, content);
            case CARDS -> cards(context, content);
            case DIVIDERS -> dividers(context, content);
            case BADGES -> badges(context, content);
            case BUTTONS -> buttons(context, content);
            case SEGMENTED -> segmented(context, content);
            case SWITCHES -> switches(context, content);
            case CHECKBOXES -> checkboxes(context, content);
            case RADIO -> radio(context, content);
            case CHIPS -> chips(context, content);
            case TEXT_FIELDS -> textFields(context, content);
            case SLIDERS -> sliders(context, content);
            case PROGRESS -> progress(context, content);
            case SNACKBARS -> snackbars(activity, content);
            case DIALOGS -> dialogs(activity, content);
            case BOTTOM_SHEETS -> bottomSheets(activity, content);
            case NAVIGATION -> navigation(context, content);
            case TABS -> tabs(context, content);
            case SEARCH -> search(activity, content);
            case FAB -> fab(context, content);
            case DATE_TIME -> dateTime(activity, content);
            case MOTION -> motion(activity, content, card, section);
            case ADAPTIVE -> adaptive(context, content);
        }

        content.addView(Ui.spacer(context, 8));
        content.addView(Ui.label(context, "LIGHT / DARK RENDERING"));
        content.addView(Ui.spacer(context, 8));
        Ui.lightDarkPreview(context, content);

        content.addView(Ui.snippet(context, section.snippet));
        content.addView(Ui.spacer(context, 10));
        io.material4.widget.MaterialButton detail = Ui.outlinedButton(context, "Open detail");
        detail.setOnClickListener(view -> openDetail(activity, card, section));
        content.addView(detail);

        card.addView(content);
    }

    private static void openDetail(Activity activity, View shared, Sections section) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITLE, section.title);
        intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, section.description);
        intent.putExtra(DetailActivity.EXTRA_TRANSITION, shared.getTransitionName());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, new Pair<>(shared, shared.getTransitionName()));
        activity.startActivity(intent, options.toBundle());
    }

    private static void toolbar(Context context, LinearLayout content) {
        MaterialToolbar toolbar = new MaterialToolbar(context);
        toolbar.setTitle("Aurora");
        toolbar.setSubtitle("Material 4 toolbar");
        toolbar.setNavigationIcon(R.drawable.ic_m4_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.inflateMenu(R.menu.menu_toolbar_demo);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(context, 64)));
        Ui.sample(context, content, "Toolbar + menu", toolbar);

        MaterialToolbar compact = new MaterialToolbar(context);
        compact.setTitle("Compact");
        compact.setNavigationIcon(R.drawable.ic_m4_back);
        compact.setNavigationContentDescription("Back");
        compact.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(context, 56)));
        Ui.sample(context, content, "Navigation + content description", compact);
    }

    private static void cards(Context context, LinearLayout content) {
        LinearLayout row = Ui.row(context);
        row.setWeightSum(2f);
        row.setGravity(Gravity.TOP);
        for (int i = 0; i < 2; i++) {
            MaterialCardView card = i == 0 ? Ui.card(context) : Ui.glassCard(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            params.setMarginEnd(Ui.dp(context, 8));
            card.setLayoutParams(params);
            card.addView(Ui.title(context, i == 0 ? "Elevated" : "Glass"));
            card.addView(Ui.spacer(context, 4));
            card.addView(Ui.caption(context, i == 0
                    ? "Surface container + 2dp elevation"
                    : "Translucent fill, gradient border, inner glow"));
            row.addView(card);
        }
        content.addView(row);
        content.addView(Ui.spacer(context, 12));

        MaterialCardView selected = Ui.card(context);
        selected.setCheckable(true);
        selected.setChecked(true);
        selected.addView(Ui.title(context, "Selected card"));
        selected.addView(Ui.caption(context, "Checked state morphs the corner and outline"));
        Ui.sample(context, content, "Selected state", selected);

        MaterialCardView disabled = Ui.card(context);
        disabled.setEnabled(false);
        disabled.addView(Ui.title(context, "Disabled card"));
        Ui.sample(context, content, "Disabled state", disabled);
    }

    private static void dividers(Context context, LinearLayout content) {
        Ui.sample(context, content, "Full width", new MaterialDivider(context));
        MaterialDivider inset = new MaterialDivider(context);
        inset.setDividerInsetStart(Ui.dp(context, 32));
        inset.setDividerInsetEnd(Ui.dp(context, 32));
        Ui.sample(context, content, "Inset", inset);
        MaterialDivider heavy = new MaterialDivider(context, null,
                com.google.android.material.R.attr.materialDividerHeavyStyle);
        Ui.sample(context, content, "Heavy", heavy);
    }

    private static void badges(Context context, LinearLayout content) {
        FrameLayout anchor = new FrameLayout(context);
        ImageView icon = new ImageView(context);
        icon.setImageResource(R.drawable.ic_m4_settings);
        icon.setContentDescription("Notifications");
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(
                Ui.dp(context, 40), Ui.dp(context, 40));
        iconParams.gravity = Gravity.CENTER;
        icon.setLayoutParams(iconParams);
        anchor.addView(icon);

        BadgeDrawable badge = Material4Badge.create(context, 12);
        Material4Badge.attach(badge, anchor);
        anchor.setLayoutParams(new LinearLayout.LayoutParams(
                Ui.dp(context, 96), Ui.dp(context, 64)));
        Ui.sample(context, content, "Badge on an anchor", anchor);

        FrameLayout second = new FrameLayout(context);
        ImageView icon2 = new ImageView(context);
        icon2.setImageResource(R.drawable.ic_m4_feedback);
        icon2.setContentDescription("Alerts");
        FrameLayout.LayoutParams icon2Params = new FrameLayout.LayoutParams(
                Ui.dp(context, 40), Ui.dp(context, 40));
        icon2Params.gravity = Gravity.CENTER;
        icon2.setLayoutParams(icon2Params);
        second.addView(icon2);
        BadgeDrawable large = Material4Badge.create(context, 99);
        Material4Badge.attach(large, second);
        second.setLayoutParams(new LinearLayout.LayoutParams(
                Ui.dp(context, 96), Ui.dp(context, 64)));
        Ui.sample(context, content, "Capped badge (99+)", second);
    }

    private static void buttons(Context context, LinearLayout content) {
        LinearLayout first = Ui.row(context);
        io.material4.widget.MaterialButton filled = Ui.button(context, "Filled");
        filled.setIconResource(R.drawable.ic_m4_add);
        filled.setContentDescription("Create");
        io.material4.widget.MaterialButton tonal = Ui.tonalButton(context, "Tonal");
        io.material4.widget.MaterialButton outlined = Ui.outlinedButton(context, "Outlined");
        first.addView(filled);
        first.addView(Ui.horizontalSpacer(context, 8));
        first.addView(tonal);
        first.addView(Ui.horizontalSpacer(context, 8));
        first.addView(outlined);
        Ui.sample(context, content, "Variants", first);

        LinearLayout second = Ui.row(context);
        io.material4.widget.MaterialButton text = Ui.textButton(context, "Text");
        io.material4.widget.MaterialButton disabled = Ui.button(context, "Disabled");
        disabled.setEnabled(false);
        io.material4.widget.MaterialButton loading = Ui.button(context, "Saving…");
        loading.setEnabled(false);
        second.addView(text);
        second.addView(Ui.horizontalSpacer(context, 8));
        second.addView(disabled);
        second.addView(Ui.horizontalSpacer(context, 8));
        second.addView(loading);
        Ui.sample(context, content, "States", second);
    }

    private static void segmented(Context context, LinearLayout content) {
        MaterialButtonToggleGroup group = new MaterialButtonToggleGroup(context);
        group.setSingleSelection(true);
        group.setSelectionRequired(true);
        String[] labels = {"Day", "Week", "Month"};
        for (int i = 0; i < labels.length; i++) {
            io.material4.widget.MaterialButton button = Ui.outlinedButton(context, labels[i]);
            button.setId(View.generateViewId());
            group.addView(button);
            if (i == 1) {
                group.check(button.getId());
            }
        }
        group.addOnButtonCheckedListener((g, checkedId, isChecked) -> {
            if (isChecked) {
                Material4Snackbar.show(content, "Segment selected");
            }
        });
        Ui.sample(context, content, "Single selection", group);
    }

    private static void switches(Context context, LinearLayout content) {
        MaterialSwitch on = new MaterialSwitch(context);
        on.setText("Aurora accents");
        on.setChecked(true);
        MaterialSwitch off = new MaterialSwitch(context);
        off.setText("Reduced motion");
        MaterialSwitch disabled = new MaterialSwitch(context);
        disabled.setText("Disabled");
        disabled.setEnabled(false);
        content.addView(on);
        content.addView(off);
        content.addView(disabled);
    }

    private static void checkboxes(Context context, LinearLayout content) {
        MaterialCheckBox checked = new MaterialCheckBox(context);
        checked.setText("Checked");
        checked.setChecked(true);
        MaterialCheckBox unchecked = new MaterialCheckBox(context);
        unchecked.setText("Unchecked");
        MaterialCheckBox indeterminate = new MaterialCheckBox(context);
        indeterminate.setText("Indeterminate");
        indeterminate.setCheckedState(MaterialCheckBox.STATE_INDETERMINATE);
        MaterialCheckBox disabled = new MaterialCheckBox(context);
        disabled.setText("Disabled");
        disabled.setEnabled(false);
        content.addView(checked);
        content.addView(unchecked);
        content.addView(indeterminate);
        content.addView(disabled);
    }

    private static void radio(Context context, LinearLayout content) {
        RadioGroup group = new RadioGroup(context);
        String[] labels = {"Compact", "Medium", "Expanded"};
        for (int i = 0; i < labels.length; i++) {
            MaterialRadioButton button = new MaterialRadioButton(context);
            button.setText(labels[i]);
            button.setId(View.generateViewId());
            group.addView(button);
            if (i == 2) {
                group.check(button.getId());
            }
        }
        content.addView(group);
        MaterialRadioButton disabled = new MaterialRadioButton(context);
        disabled.setText("Disabled option");
        disabled.setEnabled(false);
        content.addView(disabled);
    }

    private static void chips(Context context, LinearLayout content) {
        ChipGroup group = new ChipGroup(context);
        Chip assist = new Chip(context);
        assist.setText("Assist");
        assist.setChipIconResource(R.drawable.ic_m4_add);
        Chip filter = new Chip(context);
        filter.setText("Filter");
        filter.setCheckable(true);
        filter.setChecked(true);
        Chip input = new Chip(context);
        input.setText("Input");
        input.setCloseIconVisible(true);
        Chip suggestion = new Chip(context);
        suggestion.setText("Suggestion");
        group.addView(assist);
        group.addView(filter);
        group.addView(input);
        group.addView(suggestion);
        Ui.sample(context, content, "Chip group", group);

        ChipGroup single = new ChipGroup(context);
        single.setSingleSelection(true);
        single.setSelectionRequired(true);
        for (String label : new String[]{"One", "Two", "Three"}) {
            Chip chip = new Chip(context);
            chip.setText(label);
            chip.setCheckable(true);
            single.addView(chip);
        }
        Ui.sample(context, content, "Single selection", single);
    }

    private static void textFields(Context context, LinearLayout content) {
        TextInputLayout email = field(context, "Email", "you@example.com");
        email.setHelperText("We never share it");
        Ui.sample(context, content, "Outlined field", email);

        TextInputLayout error = field(context, "Handle", "@material");
        error.setError("Use only letters and numbers");
        Ui.sample(context, content, "Error state", error);

        TextInputLayout password = field(context, "Password", "");
        password.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        Ui.sample(context, content, "Password toggle", password);

        TextInputLayout dropdown = new TextInputLayout(context);
        dropdown.setHint("Window size class");
        dropdown.setEndIconMode(TextInputLayout.END_ICON_DROPDOWN_MENU);
        MaterialAutoCompleteTextView auto = new MaterialAutoCompleteTextView(context);
        auto.setSimpleItems(new String[]{"Compact", "Medium", "Expanded"});
        auto.setInputType(android.text.InputType.TYPE_NULL);
        dropdown.addView(auto);
        Ui.sample(context, content, "Exposed dropdown", dropdown);

        TextInputLayout counter = field(context, "Project name", "Material 4");
        counter.setCounterEnabled(true);
        counter.setCounterMaxLength(24);
        Ui.sample(context, content, "Counter", counter);
    }

    private static TextInputLayout field(Context context, String hint, String value) {
        TextInputLayout layout = new TextInputLayout(context);
        layout.setHint(hint);
        TextInputEditText edit = new TextInputEditText(context);
        edit.setText(value);
        layout.addView(edit, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    private static void sliders(Context context, LinearLayout content) {
        io.material4.widget.Slider slider = new io.material4.widget.Slider(context);
        slider.setValueFrom(0f);
        slider.setValueTo(100f);
        slider.setStepSize(10f);
        slider.setValue(60f);
        Ui.sample(context, content, "Single value", slider);

        RangeSlider range = new RangeSlider(context);
        range.setValueFrom(0f);
        range.setValueTo(100f);
        range.setValues(30f, 70f);
        Ui.sample(context, content, "Range", range);
    }

    private static void progress(Context context, LinearLayout content) {
        LinearProgressIndicator determinate = new LinearProgressIndicator(context);
        determinate.setProgress(64);
        Ui.sample(context, content, "Linear determinate", determinate);

        LinearProgressIndicator indeterminate = new LinearProgressIndicator(context);
        indeterminate.setIndeterminate(true);
        Ui.sample(context, content, "Linear indeterminate", indeterminate);

        CircularProgressIndicator circular = new CircularProgressIndicator(context);
        circular.setProgress(72);
        Ui.sample(context, content, "Circular determinate", circular);

        CircularProgressIndicator spinner = new CircularProgressIndicator(context);
        spinner.setIndeterminate(true);
        Ui.sample(context, content, "Circular indeterminate", spinner);
    }

    private static void snackbars(Activity activity, LinearLayout content) {
        io.material4.widget.MaterialButton action = Ui.button(activity, "Show with action");
        action.setOnClickListener(view -> Material4Snackbar.action(content, "Project saved",
                "Undo", v -> Material4Snackbar.show(content, "Undone")));
        Ui.sample(activity, content, "Snackbar with action", action);

        io.material4.widget.MaterialButton simple = Ui.outlinedButton(activity, "Show short");
        simple.setOnClickListener(view -> Material4Snackbar.show(content, "Aurora tokens applied"));
        Ui.sample(activity, content, "Short snackbar", simple);
    }

    private static void dialogs(Activity activity, LinearLayout content) {
        io.material4.widget.MaterialButton alert = Ui.button(activity, "Alert dialog");
        alert.setOnClickListener(view -> {
            Material4AlertDialog builder = new Material4AlertDialog(activity);
            builder.setTitle("Apply Material 4?");
            builder.setMessage("The theme overlay updates every component, not just this screen.");
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Apply", (dialog, which) ->
                    Material4Snackbar.show(content, "Material 4 applied"));
            builder.show();
        });
        Ui.sample(activity, content, "Alert", alert);

        io.material4.widget.MaterialButton custom = Ui.outlinedButton(activity, "Custom content");
        custom.setOnClickListener(view -> {
            LinearLayout body = Ui.column(activity);
            Ui.addDialogContent(activity, body);
            new Material4AlertDialog(activity)
                    .setTitle("Depth and contrast")
                    .setView(body)
                    .setPositiveButton("Close", null)
                    .show();
        });
        Ui.sample(activity, content, "Custom content", custom);
    }

    private static void bottomSheets(Activity activity, LinearLayout content) {
        io.material4.widget.MaterialButton sheet = Ui.button(activity, "Open bottom sheet");
        sheet.setOnClickListener(view -> {
            LinearLayout body = Ui.column(activity);
            body.setPadding(Ui.dp(activity, 24), Ui.dp(activity, 24),
                    Ui.dp(activity, 24), Ui.dp(activity, 32));
            body.addView(Ui.gradientTitle(activity, "Material 4 sheet"));
            body.addView(Ui.spacer(activity, 6));
            body.addView(Ui.text(activity, "A large radius, a translucent surface and a blur "
                    + "fallback keep the sheet legible on every API level."));
            body.addView(Ui.spacer(activity, 12));
            MaterialSwitch toggle = new MaterialSwitch(activity);
            toggle.setText("Haptic toggle");
            toggle.setChecked(true);
            body.addView(toggle);
            Material4BottomSheetDialog dialog = new Material4BottomSheetDialog(activity);
            dialog.showContent(body);
        });
        Ui.sample(activity, content, "Modal sheet", sheet);

        io.material4.widget.MaterialButton fragment = Ui.outlinedButton(activity,
                "Sheet fragment wrapper");
        fragment.setOnClickListener(view ->
                new Material4BottomSheetDialogFragment().show(
                        ((androidx.fragment.app.FragmentActivity) activity).getSupportFragmentManager(), "m4-sheet"));
        Ui.sample(activity, content, "Fragment wrapper", fragment);
    }

    private static void navigation(Context context, LinearLayout content) {
        BottomNavigationView bar = new BottomNavigationView(context);
        bar.inflateMenu(R.menu.menu_gallery_nav);
        bar.setSelectedItemId(R.id.nav_inputs);
        bar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Ui.sample(context, content, "Compact: bottom navigation", bar);

        NavigationRailView rail = new NavigationRailView(context);
        rail.inflateMenu(R.menu.menu_gallery_nav);
        rail.setSelectedItemId(R.id.nav_extras);
        LinearLayout railRow = Ui.row(context);
        railRow.addView(rail);
        LinearLayout railText = Ui.column(context);
        railText.setPadding(Ui.dp(context, 16), 0, 0, 0);
        railText.addView(Ui.title(context, "Expanded: rail"));
        railText.addView(Ui.caption(context, "The same menu drives both views. The shell swaps "
                + "them at the 600dp breakpoint."));
        railRow.addView(railText);
        Ui.sample(context, content, "Adaptive navigation", railRow);
    }

    private static void tabs(Context context, LinearLayout content) {
        TabLayout tabs = new TabLayout(context);
        tabs.addTab(tabs.newTab().setText("Overview"));
        tabs.addTab(tabs.newTab().setText("Tokens"));
        tabs.addTab(tabs.newTab().setText("Motion"));
        TextView selected = Ui.text(context, "Overview selected");
        tabs.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                selected.setText(tab.getText() + " selected");
            }

            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
            }
        });
        Ui.sample(context, content, "Primary tabs", tabs);
        content.addView(selected);

        TabLayout secondary = new TabLayout(context, null,
                com.google.android.material.R.attr.tabSecondaryStyle);
        secondary.addTab(secondary.newTab().setText("Design"));
        secondary.addTab(secondary.newTab().setText("Code"));
        Ui.sample(context, content, "Secondary tabs", secondary);
    }

    private static void search(Activity activity, LinearLayout content) {
        CoordinatorLayout container = new CoordinatorLayout(activity);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(activity, 300)));
        SearchView searchView = new SearchView(activity);
        SearchBar searchBar = new SearchBar(activity);
        searchBar.setHint("Search the catalogue");
        searchBar.setText("Aurora");
        searchBar.setNavigationContentDescription("Back");
        searchView.setupWithSearchBar(searchBar);
        searchView.setHint("Search components");
        container.addView(searchView);
        container.addView(searchBar);
        Ui.sample(activity, content, "Search bar + view", container);

        io.material4.widget.MaterialButton open = Ui.outlinedButton(activity, "Open search");
        open.setOnClickListener(view -> searchView.show());
        Ui.sample(activity, content, "Predictive back", open);
    }

    private static void fab(Context context, LinearLayout content) {
        LinearLayout row = Ui.row(context);
        FloatingActionButton fab = new FloatingActionButton(context);
        fab.setImageResource(R.drawable.ic_m4_add);
        fab.setContentDescription("Create component");
        row.addView(fab);
        row.addView(Ui.horizontalSpacer(context, 12));

        ExtendedFloatingActionButton extended = new ExtendedFloatingActionButton(context);
        extended.setText("Create");
        extended.setIconResource(R.drawable.ic_m4_add);
        extended.setContentDescription("Create a Material 4 component");
        row.addView(extended);
        Ui.sample(context, content, "Regular + extended", row);

        FloatingActionButton large = new FloatingActionButton(context, null,
                com.google.android.material.R.attr.floatingActionButtonLargePrimaryStyle);
        large.setImageResource(R.drawable.ic_m4_add);
        large.setContentDescription("Create large");
        Ui.sample(context, content, "Large", large);
    }

    private static void dateTime(Activity activity, LinearLayout content) {
        io.material4.widget.MaterialButton date = Ui.button(activity, "Pick a date");
        date.setOnClickListener(view ->
                Material4DatePicker.showSingle(((androidx.fragment.app.FragmentActivity) activity).getSupportFragmentManager(), "Pick a day"));
        Ui.sample(activity, content, "Single date", date);

        io.material4.widget.MaterialButton range = Ui.outlinedButton(activity, "Pick a range");
        range.setOnClickListener(view ->
                Material4DatePicker.showRange(((androidx.fragment.app.FragmentActivity) activity).getSupportFragmentManager(), "Pick a range"));
        Ui.sample(activity, content, "Date range", range);

        io.material4.widget.MaterialButton time = Ui.outlinedButton(activity, "Pick a time");
        time.setOnClickListener(view ->
                Material4TimePicker.show(((androidx.fragment.app.FragmentActivity) activity).getSupportFragmentManager(), "Pick a time"));
        Ui.sample(activity, content, "Time picker", time);
    }

    private static void motion(Activity activity, LinearLayout content, MaterialCardView card,
                               Sections section) {
        Material4Tokens tokens = Material4Tokens.from(activity);
        MaterialCardView target = Ui.glassCard(activity);
        target.addView(Ui.title(activity, "Press for spring physics"));
        Material4Motion.pressFeedback(target);
        Ui.sample(activity, content, "Spring press", target);

        LinearLayout chips = Ui.row(activity);
        List<View> staggerViews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Chip chip = new Chip(activity);
            chip.setText("Item " + (i + 1));
            chips.addView(chip);
            staggerViews.add(chip);
        }
        io.material4.widget.MaterialButton stagger = Ui.outlinedButton(activity, "Replay stagger");
        stagger.setOnClickListener(view -> Material4Motion.stagger(staggerViews));
        Ui.sample(activity, content, "Staggered reveal", chips);
        content.addView(stagger);
        content.addView(Ui.spacer(activity, 8));

        io.material4.widget.MaterialButton transform = Ui.outlinedButton(activity,
                "Container transform");
        transform.setOnClickListener(view -> openDetail(activity, card, section));
        Ui.sample(activity, content, "Container transform", transform);

        TextView status = Ui.caption(activity, "Reduced motion: "
                + (tokens.reducedMotion ? "on" : "off")
                + " · Predictive back is registered by the gallery activity.");
        content.addView(status);
    }

    private static void adaptive(Context context, LinearLayout content) {
        Material4Adaptive.SizeClass sizeClass = Material4Adaptive.sizeClass(context);
        Material4Tokens tokens = Material4Tokens.from(context);
        int widthDp = Math.round(context.getResources().getDisplayMetrics().widthPixels
                / context.getResources().getDisplayMetrics().density);
        TextView status = Ui.label(context, "WINDOW " + widthDp + "dp · " + sizeClass
                + " · " + (Material4Adaptive.isTwoPane(context) ? "TWO PANE" : "ONE PANE"));
        status.setTextColor(tokens.success);
        content.addView(status);
        content.addView(Ui.spacer(context, 10));

        LinearLayout panes = sizeClass == Material4Adaptive.SizeClass.EXPANDED
                ? Ui.row(context) : Ui.column(context);
        for (int i = 0; i < 2; i++) {
            MaterialCardView pane = Ui.card(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    sizeClass == Material4Adaptive.SizeClass.EXPANDED ? 0
                            : ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    sizeClass == Material4Adaptive.SizeClass.EXPANDED ? 1f : 0f);
            params.setMarginEnd(sizeClass == Material4Adaptive.SizeClass.EXPANDED
                    ? Ui.dp(context, 8) : 0);
            params.bottomMargin = Ui.dp(context, 8);
            pane.setLayoutParams(params);
            pane.addView(Ui.title(context, i == 0 ? "List pane" : "Detail pane"));
            pane.addView(Ui.caption(context, i == 0
                    ? "Starts at 840dp as the primary pane"
                    : "Shown beside the list on tablets and foldables"));
            panes.addView(pane);
        }
        content.addView(panes);
        content.addView(Ui.caption(context, "Foldable hinge awareness comes from "
                + "WindowInfoTracker; the shell observes the size class on every layout change."));
    }
}
