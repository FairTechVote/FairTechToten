package com.example.fairtechtoten.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.fairtechtoten.data.local.database.AppDataBase;
import com.example.fairtechtoten.data.mapper.CoordinatorMapper;
import com.example.fairtechtoten.data.remote.ApiService;
import com.example.fairtechtoten.data.remote.RetrofitClient;
import com.example.fairtechtoten.data.remote.dto.CoordinatorDto;
import com.example.fairtechtoten.data.remote.dto.CoordinatorUpdateRequestDto;
import com.example.fairtechtoten.domain.model.Coordinator;
import com.example.fairtechtoten.domain.repository.CoordinatorRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinatorRepositoryImpl implements CoordinatorRepository {

    private final ApiService apiService;
    private final AppDataBase database;
    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();
    public CoordinatorRepositoryImpl(Context context) {
        this.apiService = RetrofitClient.getApiService(context);
        this.database = AppDataBase.getInstance(context);
    }
    @Override
    public void pullFromRemote(long coordinatorId, SyncCallback callback) {
        apiService.getCoordinator(coordinatorId).enqueue(new Callback<CoordinatorDto>() {
            @Override
            public void onResponse(@NonNull Call<CoordinatorDto> call,
                                   @NonNull Response<CoordinatorDto> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError("Não foi possível sincronizar o coordenador");
                    return;
                }
                Coordinator entity = CoordinatorMapper.toEntity(response.body());
                ioExecutor.execute(() -> {
                    database.coordinatorDAO().upsert(entity);
                    callback.onSuccess(entity);
                });
            }
            @Override
            public void onFailure(@NonNull Call<CoordinatorDto> call, @NonNull Throwable t) {
                callback.onError("Erro de conexão: " + t.getMessage());
            }
        });
    }
    @Override
    public void pushToRemote(Coordinator local, SyncCallback callback) {
        CoordinatorUpdateRequestDto body = CoordinatorMapper.toUpdateRequest(local);
        apiService.updateCoordinator(local.getId(), body)
                .enqueue(new Callback<CoordinatorDto>() {
                    @Override
                    public void onResponse(@NonNull Call<CoordinatorDto> call,
                                           @NonNull Response<CoordinatorDto> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            callback.onError("Falha ao enviar dados para a API");
                            return;
                        }
                        Coordinator synced = CoordinatorMapper.toEntity(response.body());
                        ioExecutor.execute(() -> {
                            database.coordinatorDAO().upsert(synced);
                            callback.onSuccess(synced);
                        });
                    }
                    @Override
                    public void onFailure(@NonNull Call<CoordinatorDto> call, @NonNull Throwable t) {
                        callback.onError("Erro de conexão: " + t.getMessage());
                    }
                });
    }
    @Override
    public void saveLocal(Coordinator coordinator, Runnable onDone) {
        ioExecutor.execute(() -> {
            database.coordinatorDAO().upsert(coordinator);
            if (onDone != null) onDone.run();
        });
    }
    @Override
    public Coordinator getLocal(long id) {
        return database.coordinatorDAO().getById(id);
    }
    @Override
    public Coordinator getCurrentLocal() {
        return database.coordinatorDAO().getCurrent();
    }
    @Override
    public void clearLocal(Runnable onDone) {
        ioExecutor.execute(() -> {
            database.coordinatorDAO().deleteAll();
            if (onDone != null) onDone.run();
        });
    }

}
