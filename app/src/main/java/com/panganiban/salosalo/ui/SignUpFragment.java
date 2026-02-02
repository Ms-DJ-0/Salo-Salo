package com.panganiban.salosalo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.panganiban.salosalo.R;
import com.panganiban.salosalo.databinding.FragmentSignUpBinding;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Initialize the Password Logic
        setupPasswordValidation();

        binding.btnSignUp.setOnClickListener(v -> signUpUser());

        binding.tvSignIn.setOnClickListener(v ->
                Navigation.findNavController(view).popBackStack());
    }

    private void setupPasswordValidation() {
        // We still need colors for the TEXT, but not the image tint
        int colorSuccess = Color.parseColor("#2ECC71"); // Green
        int colorDefault = Color.parseColor("#9FA5C0"); // Gray

        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();

                // --- CHECK 1: Length >= 6 ---
                if (password.length() >= 6) {
                    // Just swap the image (No tinting needed)
                    binding.ivCheckLength.setImageResource(R.drawable.checked_icon);
                    binding.tvCheckLength.setTextColor(colorSuccess);
                } else {
                    // Revert image
                    binding.ivCheckLength.setImageResource(R.drawable.unchecked_pw);
                    binding.tvCheckLength.setTextColor(colorDefault);
                }

                // --- CHECK 2: Contains a Number ---
                if (password.matches(".*\\d.*")) {
                    binding.ivCheckNumber.setImageResource(R.drawable.checked_icon);
                    binding.tvCheckNumber.setTextColor(colorSuccess);
                } else {
                    binding.ivCheckNumber.setImageResource(R.drawable.unchecked_pw);
                    binding.tvCheckNumber.setTextColor(colorDefault);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void signUpUser() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Optional: Block signup if criteria aren't met
        if (password.length() < 6 || !password.matches(".*\\d.*")) {
            Toast.makeText(getContext(), "Password must be 6+ chars and include a number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnSignUp.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        saveUserToFirestore(userId, name, email);
                    } else {
                        binding.btnSignUp.setEnabled(true);
                        Toast.makeText(getContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(String userId, String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    if (getView() != null) {
                        Navigation.findNavController(getView()).navigate(R.id.action_signUp_to_home);
                    }
                })
                .addOnFailureListener(e -> {
                    binding.btnSignUp.setEnabled(true);
                    Toast.makeText(getContext(), "Error saving user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}