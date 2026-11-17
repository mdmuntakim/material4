package io.material4.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.TimeZone;

/** Material 4 date picker factory around the public Material 3 date picker builder. */
public final class Material4DatePicker {

    private Material4DatePicker() {
    }

    @NonNull
    public static MaterialDatePicker<Long> showSingle(@NonNull FragmentManager manager,
                                                      @Nullable CharSequence title) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        if (title != null) {
            builder.setTitleText(title);
        }
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        builder.setSelection(today);
        MaterialDatePicker<Long> picker = builder.build();
        picker.show(manager, "material4-date-picker");
        return picker;
    }

    @NonNull
    public static MaterialDatePicker<androidx.core.util.Pair<Long, Long>> showRange(
            @NonNull FragmentManager manager, @Nullable CharSequence title) {
        MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> builder =
                MaterialDatePicker.Builder.dateRangePicker();
        if (title != null) {
            builder.setTitleText(title);
        }
        MaterialDatePicker<androidx.core.util.Pair<Long, Long>> picker = builder.build();
        picker.show(manager, "material4-date-range-picker");
        return picker;
    }

    /** A stable UTC midnight for today, useful for tests and initial selections. */
    public static long today() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
