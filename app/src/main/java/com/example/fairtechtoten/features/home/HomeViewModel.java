package com.example.fairtechtoten.features.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fairtechtoten.core.utils.Resource;
import com.example.fairtechtoten.data.repository.InstituteRepositoryImpl;
import com.example.fairtechtoten.domain.model.Institute;
import com.example.fairtechtoten.domain.repository.InstituteRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final InstituteRepository instituteRepository;
    private final MutableLiveData<Resource<List<Institute>>> instituteResult = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.instituteRepository = new InstituteRepositoryImpl(application);
    }

    public LiveData<Resource<List<Institute>>> getInstituteResult() {
        return instituteResult;
    }

    public void loadInstitutes(Long coordinatorId) {
        instituteResult.setValue(Resource.loading());

        instituteRepository.getCoordinatorInstitutes(coordinatorId,
                new InstituteRepository.InstituteCallback() {
                    @Override
                    public void onSuccess(List<Institute> institutes) {
                        instituteResult.postValue(Resource.success(institutes));
                    }

                    @Override
                    public void onError(String message) {
                        instituteResult.postValue(Resource.error(message));
                    }
                });
    }

}
