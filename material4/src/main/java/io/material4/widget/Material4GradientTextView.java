package io.material4.widget;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.material4.Material4Tokens;

/**
 * A Material 4 display text view painted with the aurora token gradient. This is an additive
 * convenience component, not a Material 3 counterpart, hence the Material4 prefix.
 */
public class Material4GradientTextView extends MaterialTextView {

    public Material4GradientTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public Material4GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Material4GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs,
                                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(0xFFFFFFFF);
        post(this::applyShader);
    }

    private void applyShader() {
        Material4Tokens tokens = Material4Tokens.from(getContext());
        Shader shader = new LinearGradient(0, 0, Math.max(getWidth(), 1), getHeight(),
                new int[]{tokens.auroraStart, tokens.auroraMid, tokens.auroraEnd},
                null, Shader.TileMode.CLAMP);
        getPaint().setShader(shader);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            applyShader();
        }
    }

    public static void gradient(@NonNull TextView view) {
        Material4Tokens tokens = Material4Tokens.from(view.getContext());
        Shader shader = new LinearGradient(0, 0, Math.max(view.getWidth(), 1),
                Math.max(view.getHeight(), 1),
                new int[]{tokens.auroraStart, tokens.auroraMid, tokens.auroraEnd},
                null, Shader.TileMode.CLAMP);
        view.getPaint().setShader(shader);
        view.invalidate();
    }
}
