package com.example.quicknote.note.presentation.ui;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quicknote.auth.presentation.login.ui.LoginActivity;
import com.example.quicknote.common.presentation.auth.AuthStateViewModel;
import com.example.quicknote.common.presentation.utils.NavHost;
import com.example.quicknote.common.presentation.utils.ThemeHelper;
import com.example.quicknote.databinding.FragmentProfileBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    @Inject
    public AuthStateViewModel authStateViewModel;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set username and email in the TextViews
        binding.usernameTextView.setText(AuthStateViewModel.userCache.getName());
        binding.emailTextView.setText(AuthStateViewModel.userCache.getEmail());

        // Handle theme toggle button click
//        binding.themeToggleButton.setOnClickListener(v -> {
//            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
//            String currentTheme = sharedPreferences.getString("selected_theme", "light");
//
//            if ("light".equals(currentTheme)) {
//                ThemeHelper.applyTheme("dark", requireContext());
//            } else {
//                ThemeHelper.applyTheme("light", requireContext());
//            }
//
//            // Recreate the activity to apply the theme change
//            requireActivity().recreate();
//        });

        authStateViewModel.getCurrentAuthState().observe(getViewLifecycleOwner(), v -> {
                    if (v) {
                        NavHost.navigateTo(getContext(), LoginActivity.class);
                    }
                }
        );

        binding.logoutButton.setOnClickListener(v -> authStateViewModel.logout(AuthStateViewModel.userCache.getEmail()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}