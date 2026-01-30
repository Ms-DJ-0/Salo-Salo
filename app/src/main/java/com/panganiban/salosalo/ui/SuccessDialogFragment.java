package com.panganiban.salosalo.ui;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import com.panganiban.salosalo.R;

public class SuccessDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Your post has been uploaded successfully!")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (getActivity() != null) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack(R.id.homeFragment, false);
                    }
                })
                .create();
    }
}
