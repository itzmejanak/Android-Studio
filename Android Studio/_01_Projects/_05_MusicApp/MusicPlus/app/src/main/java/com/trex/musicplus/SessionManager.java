package com.trex.musicplus;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private static final String PREF_FILE_NAME = "profile";
    private static final int PRIVATE_MODE = 0;

    private static final String KEY_NAME = "key_session_name";
    private static final String KEY_PROFILE_IMAGE_URI = "key_session_profile_image_uri";
    private static final String KEY_COVER_IMAGE_URI = "key_session_cover_image_uri";
    private static final String KEY_IF_LOGGED_IN = "key_logged_in";

    public SessionManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(PREF_FILE_NAME, PRIVATE_MODE);
        editor = sp.edit();
    }

    public boolean checkSession() {
        return sp.contains(KEY_IF_LOGGED_IN);
    }

    public String getSessionDetails(String key) {
        return sp.getString(key, null);
    }

    public void createSession(String name, String profileImageUri, String coverImageUri) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PROFILE_IMAGE_URI, profileImageUri);
        editor.putString(KEY_COVER_IMAGE_URI, coverImageUri);
        editor.putBoolean(KEY_IF_LOGGED_IN, true);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
