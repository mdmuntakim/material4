package io.material4.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import io.material4.Material4Tokens;

/** Material 4 badge wrapper. Badge is a Drawable, so it is attached rather than subclassed. */
public final class Material4Badge {

    private Material4Badge() {
    }

    @NonNull
    public static BadgeDrawable create(@NonNull Context context, int number) {
        Material4Tokens tokens = Material4Tokens.from(context);
        BadgeDrawable badge = BadgeDrawable.create(context);
        badge.setBackgroundColor(tokens.tertiary);
        badge.setBadgeTextColor(tokens.surface);
        badge.setNumber(number);
        badge.setMaxCharacterCount(3);
        badge.setContentDescriptionQuantityStringsResource(
                io.material4.R.string.m4_badge_description);
        return badge;
    }

    public static void attach(@NonNull BadgeDrawable badge, @NonNull View anchor) {
        BadgeUtils.attachBadgeDrawable(badge, anchor);
    }

    public static void detach(@NonNull BadgeDrawable badge, @NonNull View anchor) {
        BadgeUtils.detachBadgeDrawable(badge, anchor);
    }

    public static void attachToToolbar(@NonNull BadgeDrawable badge,
                                       @NonNull androidx.appcompat.widget.Toolbar toolbar,
                                       int menuItemId) {
        BadgeUtils.attachBadgeDrawable(badge, toolbar, menuItemId);
    }
}
