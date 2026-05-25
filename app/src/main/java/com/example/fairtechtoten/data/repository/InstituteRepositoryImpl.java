package com.example.fairtechtoten.data.repository;

import android.content.Context;

import com.example.fairtechtoten.data.remote.ApiService;
import com.example.fairtechtoten.data.remote.RetrofitClient;
import com.example.fairtechtoten.data.remote.dto.CoordinatorInstituteLisResponseDto;
import com.example.fairtechtoten.data.remote.dto.SummaryInstituteDto;
import com.example.fairtechtoten.domain.model.Institute;
import com.example.fairtechtoten.domain.repository.InstituteRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstituteRepositoryImpl implements InstituteRepository {

    private final ApiService apiService;

    public InstituteRepositoryImpl(Context context) {

        apiService = RetrofitClient.getApiService(context);

    }

    @Override
    public void getCoordinatorInstitutes(Long coordinatorId, InstituteCallback callback) {
        apiService.getCoordinatorInstitutes(coordinatorId)
                .enqueue(new Callback<CoordinatorInstituteLisResponseDto>() {
                    @Override
                    public void onResponse(Call<CoordinatorInstituteLisResponseDto> call,
                                           Response<CoordinatorInstituteLisResponseDto> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            List<Institute> institutes = new ArrayList<>();
                            for (SummaryInstituteDto dto : response.body().getInstitutes()) {
                                institutes.add(new Institute(
                                        dto.getId(),
                                        dto.getName(),
                                        dto.getCnpj()
                                ));
                            }

                            callback.onSuccess(institutes);
                        } else {
                            callback.onError("Erro ao obter os instituições");
                        }
                    }

                    @Override
                    public void onFailure(Call<CoordinatorInstituteLisResponseDto> call,
                                          Throwable t) {

                        callback.onError("Erro de conexão: " + t.getMessage());

                    }

                });

    }

}
