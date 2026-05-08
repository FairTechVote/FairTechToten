package com.example.fairtechtoten.features.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fairtechtoten.R;
import com.example.fairtechtoten.databinding.FragmentLoginBinding;
import com.example.fairtechtoten.domain.model.Coordinator;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setupObservers();
        setupListener();


    }

    public void setupObservers() {

        viewModel.getLoginResult().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    binding.loginBtn.setEnabled(false);
                    binding.loginBtn.setText("Carregando ...");
                    break;
                case SUCCESS:
                    binding.loginBtn.setEnabled(true);
                    binding.loginBtn.setText("Login");

                    Coordinator coordinator = resource.getData();
                    navigateToHome(coordinator);

                    break;
                case ERROR:
                    binding.loginBtn.setEnabled(true);
                    binding.loginBtn.setText("Login");
                    Toast.makeText(requireContext(),
                            resource.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }

    private void setupListener() {

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailTb.getText().toString();
            String password = binding.passwordTb.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.login(email, password);

        });

    }

    private void navigateToHome(Coordinator coordinator) {
        Bundle bundle = new Bundle();
        bundle.putString("email", coordinator.getEmail());
        bundle.putString("name", coordinator.getName());
        bundle.putString("token", coordinator.getToken());
        bundle.putLong("coordinatorId", coordinator.getCoordinatorId());
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_login_to_home, bundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar memory leaks
    }
}