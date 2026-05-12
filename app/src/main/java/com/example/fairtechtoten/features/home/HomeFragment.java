package com.example.fairtechtoten.features.home;

import static com.example.fairtechtoten.core.utils.Resource.Status.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fairtechtoten.data.local.TokenManager;
import com.example.fairtechtoten.databinding.FragmentHomeBinding;
import com.example.fairtechtoten.features.home.adapter.InstituteAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private InstituteAdapter adapter;
    private TokenManager tokenManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        tokenManager = new TokenManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupObservers();
        loadUserData();
    }

    private void setupRecyclerView() {
        adapter = new InstituteAdapter();
        binding.institutesRecyclerView.setAdapter(adapter);

        adapter.setOnInstituteClickListener(institute -> {
            Toast.makeText(requireContext(),
                    "Selecionado: " + institute.getName(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void setupObservers() {
        viewModel.getInstituteResult().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.institutesRecyclerView.setVisibility(View.GONE);
                    binding.emptyStateTxt.setVisibility(View.GONE);
                    break;

                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);

                    if (resource.getData() != null && !resource.getData().isEmpty()) {
                        binding.institutesRecyclerView.setVisibility(View.VISIBLE);
                        binding.emptyStateTxt.setVisibility(View.GONE);
                        adapter.setInstitutes(resource.getData());
                    } else {
                        binding.institutesRecyclerView.setVisibility(View.GONE);
                        binding.emptyStateTxt.setVisibility(View.VISIBLE);
                    }
                    break;

                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.institutesRecyclerView.setVisibility(View.GONE);
                    binding.emptyStateTxt.setVisibility(View.VISIBLE);

                    Toast.makeText(requireContext(),
                            resource.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void loadUserData() {
        String name = tokenManager.getName();
        Long coordinatorId = tokenManager.getCoordinatorId();

        if (name != null) {
            binding.welcomeTxt.setText("Bem-vindo, " + name + "!");
        }

        if (coordinatorId != null && coordinatorId > 0) {
            viewModel.loadInstitutes(coordinatorId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}