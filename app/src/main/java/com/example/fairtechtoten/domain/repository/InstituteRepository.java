package com.example.fairtechtoten.domain.repository;

import com.example.fairtechtoten.domain.model.Institute;

import java.util.List;

public interface InstituteRepository {

    void getCoordinatorInstitutes(Long coordinatorId,InstituteCallback callback);

    interface InstituteCallback {
        void onSuccess(List<Institute> institutes);
        void onError(String message);
    }

}
