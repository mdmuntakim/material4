package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public abstract class NavigationBarMenuView extends com.google.android.material.navigation.NavigationBarMenuView {

    protected NavigationBarMenuView(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }
}
