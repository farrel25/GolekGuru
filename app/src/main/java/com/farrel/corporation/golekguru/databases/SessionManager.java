package com.farrel.corporation.golekguru.databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    // variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FULL_NAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_BIRTH_DATE = "birthDate";



    public SessionManager(Context context) {
        this.context = context;
        this.userSession = this.context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        this.editor = userSession.edit();
    }

    public void createLoginSession(String fullName, String username, String email, String phone, String password, String gender, String birthDate) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_BIRTH_DATE, birthDate);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULL_NAME, userSession.getString(KEY_FULL_NAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE, userSession.getString(KEY_PHONE, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        userData.put(KEY_BIRTH_DATE, userSession.getString(KEY_BIRTH_DATE, null));

        return userData;
    }

    public boolean checkLogin() {
        return userSession.getBoolean(IS_LOGIN, false);
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
