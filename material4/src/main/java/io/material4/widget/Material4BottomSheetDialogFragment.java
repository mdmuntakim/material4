package io.material4.widget;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/** Material 4 bottom sheet fragment that creates a {@link Material4BottomSheetDialog}. */
public class Material4BottomSheetDialogFragment extends BottomSheetDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Material4BottomSheetDialog(requireContext(), getTheme());
    }
}
