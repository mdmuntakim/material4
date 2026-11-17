package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class BottomAppBar extends com.google.android.material.bottomappbar.BottomAppBar {

    public BottomAppBar(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public BottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

    public BottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Material4Widgets.init(this);
    }

}
