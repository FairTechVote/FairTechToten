package com.example.fairtechtoten.domain.repository;

import com.example.fairtechtoten.domain.model.Coordinator;

public interface AuthRepository {

    void login(String email, String password, AuthCallback callback);

    interface AuthCallback {
        void onSuccess(Coordinator coordinator);
        void onError(String message);
    }

}
