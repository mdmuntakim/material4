package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class CollapsingToolbarLayout extends com.google.android.material.appbar.CollapsingToolbarLayout {

    public CollapsingToolbarLayout(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public CollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

    public CollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Material4Widgets.init(this);
    }

}
