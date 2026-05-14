package com.example.fairtechtoten.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.fairtechtoten.data.remote.ApiService;
import com.example.fairtechtoten.data.remote.RetrofitClient;
import com.example.fairtechtoten.data.remote.dto.LoginRequestDto;
import com.example.fairtechtoten.data.remote.dto.LoginResponseDto;
import com.example.fairtechtoten.domain.model.Coordinator;
import com.example.fairtechtoten.domain.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {

    private final ApiService apiService;

    public AuthRepositoryImpl(Context context) {
        this.apiService = RetrofitClient.getApiService(context);
    }

    @Override
    public void login(String email, String password, AuthCallback callback){

        LoginRequestDto request = new LoginRequestDto(email, password);

        apiService.login(request).enqueue(new Callback<LoginResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseDto> call,
                                   @NonNull Response<LoginResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponseDto dto = response.body();
                    Coordinator coordinator = new Coordinator(dto.getToken(),
                            dto.getCoordinatorId(),
                            dto.getEmail(),
                            dto.getName());
                    callback.onSuccess(coordinator);
                } else {
                    callback.onError("Email ou Senha incorretos");
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseDto> call,
                                  @NonNull Throwable t) {
                callback.onError("Erro de conexão: " + t.getMessage());

            }
        });

    }

}
