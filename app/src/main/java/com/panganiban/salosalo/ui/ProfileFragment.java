package com.panganiban.salosalo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.panganiban.salosalo.R;
import com.panganiban.salosalo.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.bottomNav.setSelectedItemId(R.id.nav_profile);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
                return true;
            } else if (id == R.id.nav_upload) {
                Navigation.findNavController(view).navigate(R.id.uploadFragment);
                return true;
            }
            return false;
        });

        binding.btnLogout.setOnClickListener(v -> {
            // In a real app, sign out of Firebase here
            Navigation.findNavController(view).navigate(R.id.action_global_to_signIn);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
