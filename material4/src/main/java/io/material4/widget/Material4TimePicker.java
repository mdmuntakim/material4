package io.material4.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

/** Material 4 time picker factory around the public Material 3 time picker builder. */
public final class Material4TimePicker {

    private Material4TimePicker() {
    }

    @NonNull
    public static MaterialTimePicker show(@NonNull FragmentManager manager,
                                          @Nullable CharSequence title) {
        Calendar now = Calendar.getInstance();
        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(now.get(Calendar.HOUR_OF_DAY))
                .setMinute(now.get(Calendar.MINUTE));
        if (title != null) {
            builder.setTitleText(title);
        }
        MaterialTimePicker picker = builder.build();
        picker.show(manager, "material4-time-picker");
        return picker;
    }

    @NonNull
    public static MaterialTimePicker build(int hour, int minute) {
        return new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();
    }
}
