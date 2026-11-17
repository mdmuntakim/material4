package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Material 4 base for navigation bar implementations. Material 3 declares this class abstract;
 * the concrete Material 4 subclasses are {@link BottomNavigationView} and
 * {@link NavigationRailView}.
 */
public abstract class NavigationBarView
        extends com.google.android.material.navigation.NavigationBarView {

    protected NavigationBarView(@NonNull Context context, @Nullable AttributeSet attrs,
                                int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Material4Widgets.init(this);
    }
}
