package com.example.fairtechtoten.features.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fairtechtoten.core.utils.Resource;
import com.example.fairtechtoten.data.repository.AuthRepositoryImpl;
import com.example.fairtechtoten.domain.model.Coordinator;
import com.example.fairtechtoten.domain.repository.AuthRepository;

public class LoginViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<Resource<Coordinator>> loginResult = new MutableLiveData<>();

    public LoginViewModel() {
        this.authRepository = new AuthRepositoryImpl();
    }

    public LiveData<Resource<Coordinator>> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        loginResult.setValue(Resource.loading());
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(Coordinator coordinator) {
                loginResult.setValue(Resource.success(coordinator));
            }
            @Override
            public void onError(String message) {
                loginResult.setValue(Resource.error(message));
            }
        });
    }

}
