package io.material4.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class CircularRevealCardView extends com.google.android.material.circularreveal.cardview.CircularRevealCardView {

    public CircularRevealCardView(@NonNull Context context) {
        super(context);
        Material4Widgets.init(this);
    }

    public CircularRevealCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Material4Widgets.init(this);
    }

}
