package com.example.fairtechtoten.domain.repository;

import com.example.fairtechtoten.domain.model.Coordinator;

public interface CoordinatorRepository {

    void pullFromRemote(long coordinatorId, SyncCallback callback);
    void pushToRemote(Coordinator local, SyncCallback callback);
    void saveLocal(Coordinator coordinator, Runnable onDone);
    Coordinator getLocal(long id);
    Coordinator getCurrentLocal();
    void clearLocal(Runnable onDone);
    interface SyncCallback {
        void onSuccess(Coordinator coordinator);
        void onError(String message);
    }

}
