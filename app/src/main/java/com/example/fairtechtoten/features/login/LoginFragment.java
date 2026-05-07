package com.example.fairtechtoten.features.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fairtechtoten.R;
import com.example.fairtechtoten.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setupListener();


    }

    private void setupListener() {

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailTb.getText().toString();
            String password = binding.passwordTb.getText().toString();
            if(email.equals("admin") && password.equals("admin")) {

                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                Navigation.findNavController(v)
                        .navigate(R.id.action_login_to_home, bundle);

            } else {

                Toast.makeText(requireContext(),
                        "Email ou senha incorretos",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar memory leaks
    }
}