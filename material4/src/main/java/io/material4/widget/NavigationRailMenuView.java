package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class NavigationRailMenuView extends com.google.android.material.navigationrail.NavigationRailMenuView {

    protected NavigationRailMenuView(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }
}
