package com.panganiban.salosalo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.panganiban.salosalo.backend.PostRepository;
import com.panganiban.salosalo.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {
    private FragmentUploadBinding binding;
    private PostRepository postRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postRepository = new PostRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnUpload.setOnClickListener(v -> uploadPost());
    }

    private void uploadPost() {
        String title = binding.etTitle.getText().toString().trim();
        String description = binding.etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnUpload.setEnabled(false);

        postRepository.uploadPost(title, description)
                .addOnSuccessListener(documentReference -> {
                    binding.btnUpload.setEnabled(true);
                    binding.etTitle.setText("");
                    binding.etDescription.setText("");
                    SuccessDialogFragment dialog = new SuccessDialogFragment();
                    dialog.show(getParentFragmentManager(), "success_dialog");
                })
                .addOnFailureListener(e -> {
                    binding.btnUpload.setEnabled(true);
                    Toast.makeText(getContext(), "Failed to upload: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
