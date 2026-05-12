package com.example.fairtechtoten.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "FairTechPrefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_COORDINATOR_ID = "coordinator_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";

    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserData(String token, Long coordinatorId, String email, String name) {
        prefs.edit()
                .putString(KEY_TOKEN, token)
                .putLong(KEY_COORDINATOR_ID, coordinatorId)
                .putString(KEY_EMAIL, email)
                .putString(KEY_NAME, name)
                .apply();
    }

    public void saveToken(String token,
                          long coordinatorId,
                          String email,
                          String name) {
        prefs.edit()
                .putString(KEY_TOKEN, token)
                .putLong(KEY_COORDINATOR_ID, coordinatorId)
                .putString(KEY_EMAIL, email)
                .putString(KEY_NAME, name)
                .apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public Long getCoordinatorId() {
        return prefs.getLong(KEY_COORDINATOR_ID, 0L);
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public String getName() {
        return prefs.getString(KEY_NAME, null);
    }

    public void clearData() {
        prefs.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

}
