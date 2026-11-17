package io.material4.gallery;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/** The catalogue sections. Mirrors the acceptance table in the issue. */
public enum Sections {
    TOOLBAR("Toolbar", "Material 4 toolbars use the glass depth token, an expressive title and a "
            + "navigation icon with a real content description.",
            "MaterialToolbar toolbar = new MaterialToolbar(context);\ntoolbar.setTitle(\"Material 4\");",
            Group.FOUNDATIONS),
    CARDS("Cards", "Elevated, filled, outlined and glass cards share one 24dp morphing radius.",
            "MaterialCardView card = new MaterialCardView(context);\ncard.setChecked(true);",
            Group.FOUNDATIONS),
    DIVIDERS("Dividers", "Hairline, inset and heavy dividers resolve their colour from the theme.",
            "MaterialDivider divider = new MaterialDivider(context);\ndivider.setDividerInsetStart(16);",
            Group.FOUNDATIONS),
    BADGES("Badges", "Badges are Material 3 drawables attached through a Material 4 wrapper.",
            "BadgeDrawable badge = Material4Badge.create(context, 12);\nMaterial4Badge.attach(badge, anchor);",
            Group.FOUNDATIONS),

    BUTTONS("Buttons", "Filled, tonal, outlined and text buttons plus disabled and loading states.",
            "MaterialButton button = new MaterialButton(context);\nbutton.setText(\"Continue\");",
            Group.INPUTS),
    SEGMENTED("Segmented buttons", "A single-select toggle group for compact choices.",
            "MaterialButtonToggleGroup group = new MaterialButtonToggleGroup(context);\ngroup.setSingleSelection(true);",
            Group.INPUTS),
    SWITCHES("Switches", "On, off and disabled switches with a haptic confirmation.",
            "MaterialSwitch toggle = new MaterialSwitch(context);\ntoggle.setChecked(true);",
            Group.INPUTS),
    CHECKBOXES("Checkboxes", "Checked, unchecked, indeterminate and disabled checkboxes.",
            "MaterialCheckBox box = new MaterialCheckBox(context);\nbox.setChecked(true);",
            Group.INPUTS),
    RADIO("Radio buttons", "A radio group with selection, disabled and error states.",
            "MaterialRadioButton radio = new MaterialRadioButton(context);\nradio.setChecked(true);",
            Group.INPUTS),
    CHIPS("Chips", "Assist, filter, input and suggestion chips in a wrapping group.",
            "Chip chip = new Chip(context);\nchip.setCheckable(true);",
            Group.INPUTS),
    TEXT_FIELDS("Text fields", "Outlined fields with helper, error, counter, password and dropdown states.",
            "TextInputLayout field = new TextInputLayout(context);\nfield.setError(\"Required\");",
            Group.INPUTS),
    SLIDERS("Sliders", "Single and range sliders with labels, steps and haptic ticks.",
            "Slider slider = new Slider(context);\nslider.setValue(64f);",
            Group.INPUTS),

    PROGRESS("Progress indicators", "Circular and linear, determinate and indeterminate.",
            "LinearProgressIndicator bar = new LinearProgressIndicator(context);\nbar.setProgress(64);",
            Group.FEEDBACK),
    SNACKBARS("Snackbars", "Snackbars with an action, an inverse surface and the M4 shape.",
            "Material4Snackbar.action(view, \"Saved\", \"Undo\", listener);",
            Group.FEEDBACK),
    DIALOGS("Dialogs", "Material 4 alert dialogs with translucent surfaces and API 31 blur.",
            "new Material4AlertDialog(context).setTitle(\"Title\").show();",
            Group.FEEDBACK),
    BOTTOM_SHEETS("Bottom sheets", "Modal bottom sheets with a large top radius and blur fallback.",
            "new Material4BottomSheetDialog(context).showContent(content);",
            Group.FEEDBACK),

    NAVIGATION("Navigation bar / rail", "The same menu swaps between a bottom bar and a rail.",
            "Material4Adaptive.observe(activity, info -> {\n  rail.setVisibility(info.sizeClass == EXPANDED ? VISIBLE : GONE);\n});",
            Group.NAVIGATION),
    TABS("Tabs", "Primary and secondary tabs with an animated indicator.",
            "TabLayout tabs = new TabLayout(context);\ntabs.addTab(tabs.newTab().setText(\"Overview\"));",
            Group.NAVIGATION),
    SEARCH("Search", "Search bar and search view with predictive back support.",
            "SearchView search = new SearchView(context);\nsearch.setupWithSearchBar(searchBar);",
            Group.NAVIGATION),

    FAB("FAB", "Small, regular, large and extended floating action buttons.",
            "FloatingActionButton fab = new FloatingActionButton(context);\nfab.setContentDescription(\"Create\");",
            Group.EXTRAS),
    DATE_TIME("Date / time pickers", "Material 3 date and time picker builders wrapped for Material 4.",
            "Material4DatePicker.showSingle(getSupportFragmentManager(), \"Pick a day\");",
            Group.EXTRAS),
    MOTION("Motion", "Spring press, stagger, container transform and predictive back.",
            "Material4Motion.stagger(views);\nMaterial4Motion.containerTransform(context);",
            Group.EXTRAS),
    ADAPTIVE("Adaptive layout", "Window size classes, folds and two-pane decisions.",
            "Material4Adaptive.SizeClass size = Material4Adaptive.sizeClass(context);",
            Group.EXTRAS);

    public final String title;
    public final String description;
    public final String snippet;
    public final Group group;

    Sections(String title, String description, String snippet, Group group) {
        this.title = title;
        this.description = description;
        this.snippet = snippet;
        this.group = group;
    }

    public enum Group {
        FOUNDATIONS("Foundations", R.drawable.ic_m4_foundations),
        INPUTS("Inputs", R.drawable.ic_m4_inputs),
        FEEDBACK("Feedback", R.drawable.ic_m4_feedback),
        NAVIGATION("Navigation", R.drawable.ic_m4_navigation),
        EXTRAS("Extras", R.drawable.ic_m4_extras);

        public final String label;
        @DrawableRes public final int icon;

        Group(String label, @DrawableRes int icon) {
            this.label = label;
            this.icon = icon;
        }

        @NonNull
        public List<Sections> sections() {
            List<Sections> result = new ArrayList<>();
            for (Sections section : Sections.values()) {
                if (section.group == this) {
                    result.add(section);
                }
            }
            return result;
        }

        public int firstPosition() {
            List<Sections> all = List.of(Sections.values());
            return all.indexOf(sections().get(0));
        }
    }

    public int position() {
        return ordinal();
    }
}
