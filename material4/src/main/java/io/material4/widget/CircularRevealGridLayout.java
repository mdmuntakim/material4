package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class CircularRevealGridLayout extends com.google.android.material.circularreveal.CircularRevealGridLayout {

    public CircularRevealGridLayout(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public CircularRevealGridLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

}
