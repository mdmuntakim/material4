package io.material4.gallery;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.material4.Material4Adaptive;
import io.material4.Material4Motion;
import io.material4.Material4Theme;
import io.material4.Material4ThemeConfig;
import io.material4.Material4Tokens;
import io.material4.widget.BottomNavigationView;
import io.material4.widget.Material4AlertDialog;
import io.material4.widget.Material4GradientTextView;
import io.material4.widget.Material4Snackbar;
import io.material4.widget.MaterialCardView;
import io.material4.widget.MaterialTextView;
import io.material4.widget.MaterialToolbar;
import io.material4.widget.NavigationRailView;

/**
 * Material4Gallery activity. The shell is adaptive: a bottom bar on compact windows, a rail on
 * medium/expanded windows and a second detail pane on expanded windows.
 */
public class MainActivity extends AppCompatActivity {

    private Material4ThemeConfig config;
    private MaterialToolbar toolbar;
    private RecyclerView list;
    private CatalogAdapter adapter;
    private BottomNavigationView bottomBar;
    private NavigationRailView rail;
    private LinearLayout contentColumn;
    private MaterialCardView detailPane;
    private MaterialTextView detailTitle;
    private MaterialTextView detailBody;
    private int currentPosition = -1;
    private boolean syncingNav;
    private Material4Adaptive.SizeClass currentSizeClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = Material4ThemeConfig.load(this);
        Material4Theme.apply(this, config);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        buildShell();

        Material4Motion.predictiveBack(this, () -> {
            // The system plays the predictive back animation before this callback.
        });
    }

    private void buildShell() {
        Material4Tokens tokens = Material4Tokens.from(this);
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(tokens.surface);

        LinearLayout shell = new LinearLayout(this);
        shell.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(shell, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        rail = new NavigationRailView(this);
        rail.inflateMenu(R.menu.menu_gallery_nav);
        rail.setLayoutParams(new LinearLayout.LayoutParams(
                Ui.dp(this, 96), ViewGroup.LayoutParams.MATCH_PARENT));
        rail.setVisibility(View.GONE);
        rail.setOnItemSelectedListener(item -> {
            groupSelected(menuGroup(item.getItemId()));
            return true;
        });
        shell.addView(rail);

        contentColumn = new LinearLayout(this);
        contentColumn.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams columnParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        shell.addView(contentColumn, columnParams);

        toolbar = new MaterialToolbar(this);
        toolbar.setTitle("Material 4");
        toolbar.setSubtitle("Unofficial extension of Material 3");
        toolbar.setNavigationIcon(R.drawable.ic_m4_settings);
        toolbar.setNavigationContentDescription("Catalogue settings");
        toolbar.setNavigationOnClickListener(view -> openSettings());
        toolbar.inflateMenu(R.menu.menu_toolbar_demo);
        toolbar.setOnMenuItemClickListener(this::onToolbarMenu);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentColumn.addView(toolbar);

        list = new RecyclerView(this);
        adapter = new CatalogAdapter(this, config.comparisonMode);
        list.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setClipToPadding(false);
        list.setPadding(0, Ui.dp(this, 8), 0, Ui.dp(this, 12));
        list.setItemViewCacheSize(4);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int first = manager.findFirstVisibleItemPosition();
                if (first != RecyclerView.NO_POSITION && first != currentPosition) {
                    currentPosition = first;
                    updateForPosition(first, false);
                }
            }
        });
        contentColumn.addView(list, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        bottomBar = new BottomNavigationView(this);
        bottomBar.inflateMenu(R.menu.menu_gallery_nav);
        bottomBar.setOnItemSelectedListener(item -> {
            groupSelected(menuGroup(item.getItemId()));
            return true;
        });
        contentColumn.addView(bottomBar);

        detailPane = Ui.card(this);
        detailPane.setLayoutParams(new LinearLayout.LayoutParams(
                Ui.dp(this, 300), ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout detailContent = Ui.column(this);
        MaterialTextView detailEyebrow = Ui.label(this, "TWO-PANE PREVIEW");
        detailTitle = Ui.title(this, "Select a section");
        detailBody = Ui.text(this, "The expanded layout shows a live summary of the section "
                + "currently at the top of the list.");
        detailContent.addView(detailEyebrow);
        detailContent.addView(Ui.spacer(this, 8));
        detailContent.addView(detailTitle);
        detailContent.addView(Ui.spacer(this, 8));
        detailContent.addView(detailBody);
        detailContent.addView(Ui.spacer(this, 14));
        detailContent.addView(Ui.snippet(this, "Material4Adaptive.observe(activity,\n"
                + "    info -> pane.setVisibility(info.sizeClass == EXPANDED ? VISIBLE : GONE));"));
        detailPane.addView(detailContent);
        detailPane.setVisibility(View.GONE);
        shell.addView(detailPane);

        setContentView(root);

        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            androidx.core.graphics.Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        Material4Adaptive.observe(this, info -> applyLayout(info));
        updateForPosition(0, true);
    }

    private void applyLayout(@NonNull Material4Adaptive.LayoutInfo info) {
        currentSizeClass = info.sizeClass;
        boolean compact = info.sizeClass == Material4Adaptive.SizeClass.COMPACT;
        boolean expanded = info.sizeClass == Material4Adaptive.SizeClass.EXPANDED;
        rail.setVisibility(compact ? View.GONE : View.VISIBLE);
        bottomBar.setVisibility(compact ? View.VISIBLE : View.GONE);
        detailPane.setVisibility(expanded ? View.VISIBLE : View.GONE);
        if (expanded) {
            list.setPadding(0, Ui.dp(this, 8), 0, Ui.dp(this, 12));
        }
    }

    private void groupSelected(Sections.Group group) {
        if (syncingNav) {
            return;
        }
        openSection(group.firstPosition());
    }

    private void updateForPosition(int position, boolean scroll) {
        Sections section = Sections.values()[position];
        if (detailTitle != null) {
            detailTitle.setText(section.title);
            detailBody.setText(section.description);
        }
        Sections.Group group = section.group;
        int selectedId = groupId(group);
        syncingNav = true;
        if (bottomBar.getSelectedItemId() != selectedId) {
            bottomBar.setSelectedItemId(selectedId);
        }
        if (rail.getSelectedItemId() != selectedId) {
            rail.setSelectedItemId(selectedId);
        }
        syncingNav = false;
    }

    private Sections.Group menuGroup(int itemId) {
        if (itemId == R.id.nav_foundations) {
            return Sections.Group.FOUNDATIONS;
        }
        if (itemId == R.id.nav_feedback) {
            return Sections.Group.FEEDBACK;
        }
        if (itemId == R.id.nav_navigation) {
            return Sections.Group.NAVIGATION;
        }
        if (itemId == R.id.nav_extras) {
            return Sections.Group.EXTRAS;
        }
        return Sections.Group.INPUTS;
    }

    private int groupId(Sections.Group group) {
        return switch (group) {
            case FOUNDATIONS -> R.id.nav_foundations;
            case FEEDBACK -> R.id.nav_feedback;
            case NAVIGATION -> R.id.nav_navigation;
            case EXTRAS -> R.id.nav_extras;
            case INPUTS -> R.id.nav_inputs;
        };
    }

    private boolean onToolbarMenu(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            openSettings();
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            openSection(Sections.SEARCH.ordinal());
            return true;
        }
        if (item.getItemId() == R.id.action_overflow) {
            showSectionPicker();
            return true;
        }
        return false;
    }

    private void showSectionPicker() {
        List<Sections> sections = List.of(Sections.values());
        CharSequence[] titles = new CharSequence[sections.size()];
        for (int i = 0; i < sections.size(); i++) {
            titles[i] = sections.get(i).title;
        }
        new Material4AlertDialog(this)
                .setTitle("Jump to a section")
                .setItems(titles, (dialog, which) -> openSection(which))
                .setNegativeButton("Close", null)
                .show();
    }

    private void openSettings() {
        SettingsSheet.show(this, config, () -> {
            config.save(this);
            recreate();
        });
    }

    /** Public entry point used by the emulator smoke test and deep links. */
    public void openSection(int position) {
        int safe = Math.max(0, Math.min(position, adapter.getItemCount() - 1));
        currentPosition = safe;
        list.scrollToPosition(safe);
        list.post(() -> {
            list.scrollToPosition(safe);
            updateForPosition(safe, true);
        });
    }

    /** True when the section at {@code position} is attached and has content. */
    public boolean isSectionRendered(int position) {
        RecyclerView.ViewHolder holder = list.findViewHolderForAdapterPosition(position);
        return holder instanceof CatalogAdapter.Holder && ((CatalogAdapter.Holder) holder).card.getChildCount() > 0;
    }

    public int getSectionCount() {
        return adapter.getItemCount();
    }

    @NonNull
    public String getSectionTitle(int position) {
        return adapter.sectionAt(position).title;
    }

    @NonNull
    public String getCurrentGroupTitle() {
        return menuGroup(bottomBar.getSelectedItemId()).label;
    }
}
