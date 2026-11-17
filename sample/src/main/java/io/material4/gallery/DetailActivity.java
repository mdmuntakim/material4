package io.material4.gallery;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import io.material4.Material4Motion;
import io.material4.Material4Theme;
import io.material4.Material4ThemeConfig;
import io.material4.Material4Tokens;
import io.material4.widget.Material4GradientTextView;
import io.material4.widget.MaterialButton;
import io.material4.widget.MaterialDivider;
import io.material4.widget.MaterialTextView;
import io.material4.widget.MaterialToolbar;

/** Detail screen reached with a Material container transform. */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_TRANSITION = "transition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Material4ThemeConfig config = Material4ThemeConfig.load(this);
        Material4Theme.apply(this, config);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        getWindow().setSharedElementEnterTransition(Material4Motion.containerTransformPlatform(this));
        getWindow().setSharedElementReturnTransition(Material4Motion.containerTransformPlatform(this));

        Material4Tokens tokens = Material4Tokens.from(this);
        LinearLayout root = Ui.column(this);
        root.setBackgroundColor(tokens.surface);
        root.setPadding(Ui.dp(this, 16), 0, Ui.dp(this, 16), Ui.dp(this, 16));

        MaterialToolbar toolbar = new MaterialToolbar(this);
        toolbar.setTitle("Detail");
        toolbar.setNavigationIcon(R.drawable.ic_m4_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(view -> finishAfterTransition());
        root.addView(toolbar, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(Ui.spacer(this, 12));

        Material4GradientTextView heading = Ui.gradientTitle(this,
                getIntent().getStringExtra(EXTRA_TITLE));
        heading.setTextSize(38f);
        heading.setAccessibilityHeading(true);
        root.addView(heading);
        root.addView(Ui.spacer(this, 12));

        MaterialTextView body = Ui.text(this, getIntent().getStringExtra(EXTRA_DESCRIPTION));
        body.setTextSize(17f);
        root.addView(body);
        root.addView(Ui.spacer(this, 20));

        MaterialDivider divider = new MaterialDivider(this);
        root.addView(divider);
        root.addView(Ui.spacer(this, 20));

        MaterialTextView transitionNote = Ui.label(this, "SHARED ELEMENT + PREDICTIVE BACK");
        root.addView(transitionNote);
        root.addView(Ui.spacer(this, 8));
        root.addView(Ui.text(this, "This screen is the destination of a Material container "
                + "transform. Press back to return; the transition is shortened or skipped when "
                + "reduced motion is enabled."));
        root.addView(Ui.spacer(this, 20));

        MaterialButton close = Ui.button(this, "Back to catalogue");
        close.setOnClickListener(view -> finishAfterTransition());
        root.addView(close);

        String transition = getIntent().getStringExtra(EXTRA_TRANSITION);
        if (transition != null && !transition.isEmpty()) {
        }
        root.setTransitionName(transition == null ? "m4_detail" : transition);

        setContentView(root);
    }
}
