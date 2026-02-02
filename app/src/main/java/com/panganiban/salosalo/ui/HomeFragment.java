package com.panganiban.salosalo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast; // Optional, for "Home" click
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.panganiban.salosalo.R;
import com.panganiban.salosalo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //CATEGORY BUTTONS
        selectCategory(binding.btnAll);

        binding.btnAll.setOnClickListener(v -> selectCategory(binding.btnAll));
        binding.btnFood.setOnClickListener(v -> selectCategory(binding.btnFood));
        binding.btnDrink.setOnClickListener(v -> selectCategory(binding.btnDrink));

        //CREATE BUTTON
        binding.fabUpload.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_upload));

        // Since it's an EditText, we treat a click as "Go to Search Screen"
        binding.etSearch.setFocusable(false); // Optional: prevent typing here, force navigation
        binding.etSearch.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_search));

        setupBottomNavigation(view); //CUSTOM NAVIGATION (bottom part)
    }

    private void setupBottomNavigation(View view) { //changed this navigation part to a custom one
        binding.navHome.setOnClickListener(v -> {
            // Optional: Scroll to top or refresh
            // binding.rvItems.smoothScrollToPosition(0);
        });

        // MESSAGE BTN
        binding.navMessage.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_message));

        // NOTIFICATION BTN
        binding.navNotif.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_notification));

        // PROFILE BTN
        binding.navProfile.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_profile));
    }

    //FOR UI AESTHETICS (changing the colors of the button)
    private void selectCategory(AppCompatButton selectedButton) {
        // A. Reset ALL buttons to "Unselected" (Grey Background, Grey Text)
        resetButton(binding.btnAll);
        resetButton(binding.btnFood);
        resetButton(binding.btnDrink);

        // B. Set the CLICKED button to "Selected" (Orange Background, White Text)
        selectedButton.setBackgroundResource(R.drawable.bg_button_orange);
        selectedButton.setTextColor(Color.WHITE);
    }

    private void resetButton(AppCompatButton button) {
        button.setBackgroundResource(R.drawable.bg_button_grey);
        button.setTextColor(Color.parseColor("#9FA5C0")); // Grey Text
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}