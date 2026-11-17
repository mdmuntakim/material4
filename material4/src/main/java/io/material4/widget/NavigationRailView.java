package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Depth;
import io.material4.Material4Tokens;

/** Material 4 navigation rail with the layered glass surface. */
public class NavigationRailView
        extends com.google.android.material.navigationrail.NavigationRailView {

    public NavigationRailView(@NonNull Context context) {
        super(context);
        init();
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Material4Widgets.init(this);
        Material4Tokens tokens = Material4Tokens.from(getContext());
        if (!tokens.comparisonMode) {
            Material4Depth.applyGlass(this, tokens, 0f);
        }
        setElevation(tokens.elevation2);
    }
}
