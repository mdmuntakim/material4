package io.material4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.graphics.RectF;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.material4.widget.Chip;
import io.material4.widget.MaterialCheckBox;
import io.material4.widget.MaterialRadioButton;
import io.material4.widget.Slider;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35, qualifiers = "w411dp-h891dp-xhdpi")
public class Material4WidgetsTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        context.setTheme(R.style.Theme_Material4);
    }

    @Test
    public void everyWidgetExtendsTheMaterial3Counterpart() throws Exception {
        List<String> names = List.of(
                "AppBarLayout", "CollapsingToolbarLayout", "MaterialToolbar", "BottomAppBar",
                "BottomNavigationView", "BottomSheetDragHandleView", "MaterialButton",
                "MaterialButtonGroup", "MaterialButtonToggleGroup", "MaterialSplitButton",
                "MaterialCardView", "MaskableFrameLayout", "MaterialCheckBox", "Chip", "ChipGroup",
                "CircularRevealFrameLayout", "CircularRevealGridLayout",
                "CircularRevealLinearLayout", "CircularRevealRelativeLayout", "CircularRevealCardView",
                "CircularRevealCoordinatorLayout", "MaterialDivider", "DockedToolbarLayout",
                "ExtendedFloatingActionButton", "FloatingActionButton", "FloatingToolbarLayout",
                "ShapeableImageView", "ListItemCardView", "ListItemLayout", "ListItemRevealLayout",
                "MaterialSwitch", "NavigationBarView", "NavigationView", "NavigationRailView",
                "NavigationRailMenuView", "CircularProgressIndicator", "LinearProgressIndicator",
                "MaterialRadioButton", "SearchBar", "SearchView", "Slider", "RangeSlider",
                "SwitchMaterial", "TabItem", "TabLayout", "MaterialAutoCompleteTextView",
                "TextInputEditText", "TextInputLayout", "MaterialTextView", "TransformationChildCard",
                "TransformationChildLayout", "OverflowLinearLayout");
        for (String name : names) {
            assertFalse("No *Patch classes", name.contains("Patch"));
            Class<?> type = Class.forName("io.material4.widget." + name);
            String superName = type.getSuperclass().getName();
            assertTrue(name + " extends " + superName,
                    superName.startsWith("com.google.android.material."));
        }
    }

    @Test
    public void switchAppliesTouchTargetAndHapticToggle() {
        MaterialSwitch view = new MaterialSwitch(context);
        assertTrue(view instanceof com.google.android.material.materialswitch.MaterialSwitch);
        assertTrue(view.getMinimumHeight() >= Material4Tokens.dpi(context, 48));
        view.setChecked(false);
        view.toggle();
        assertTrue(view.isChecked());
    }

    @Test
    public void checkboxAndRadioToggleStatesChange() {
        MaterialCheckBox box = new MaterialCheckBox(context);
        box.setChecked(false);
        box.toggle();
        assertTrue(box.isChecked());

        MaterialRadioButton radio = new MaterialRadioButton(context);
        radio.setChecked(false);
        radio.toggle();
        assertTrue(radio.isChecked());
    }

    @Test
    public void cardAndButtonUseSquircleShape() {
        MaterialCardView card = new MaterialCardView(context);
        Material4Shape.apply(card, context, 24f);
        float radius = Material4Shape.cornerRadius(card);
        assertEquals(24f * context.getResources().getDisplayMetrics().density, radius, 0.5f);

        MaterialButton button = new MaterialButton(context);
        Material4Shape.apply(button, context, 16f);
        assertTrue(Material4Shape.cornerRadius(button) > 0f);
    }

    @Test
    public void squircleModelHasAllCorners() {
        com.google.android.material.shape.ShapeAppearanceModel model =
                Material4Shape.squircle(Material4Tokens.dp(context, 20f));
        RectF rect = new RectF(0, 0, 100, 100);
        assertEquals(Material4Tokens.dp(context, 20f),
                model.getTopLeftCornerSize().getCornerSize(rect), 0.5f);
        assertEquals(model.getTopLeftCornerSize().getCornerSize(rect),
                model.getBottomRightCornerSize().getCornerSize(rect), 0.5f);
    }

    @Test
    public void inputAndSliderAreMaterial4Subclasses() {
        assertTrue(new TextInputLayout(context) instanceof com.google.android.material.textfield.TextInputLayout);
        assertTrue(new Slider(context) instanceof com.google.android.material.slider.Slider);
        assertTrue(new Chip(context) instanceof com.google.android.material.chip.Chip);
    }
}
