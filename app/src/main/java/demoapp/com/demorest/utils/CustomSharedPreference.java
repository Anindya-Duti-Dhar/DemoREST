package demoapp.com.demorest.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class CustomSharedPreference {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _mcontext;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "demo_rest";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    //update and get user preference for CURRENT USER LOGIN
    private static final String PREFERENCE_USER_LOGIN = "user_login";
    private static final String APPLICATION_USER_LOGIN = "ap_user_login";

    //update and get user preference for CURRENT USER FULL NAME
    private static final String PREFERENCE_USER_FULL_NAME = "user_full_name";
    private static final String APPLICATION_USER_FULL_NAME = "ap_user_full_name";

    //update and get user preference for CURRENT USER PASSWORD
    private static final String PREFERENCE_USER_PASSWORD = "user_password";
    private static final String APPLICATION_USER_PASSWORD = "ap_user_password";

    //update and get user preference for LOGIN_STATUS
    private static final String PREFERENCE_USER_LOGIN_STATUS = "user_login_status";
    private static final String APPLICATION_USER_LOGIN_STATUS = "ap_user_login_status";


    public CustomSharedPreference(Context context) {
        this._mcontext = context;
        pref = _mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public static void setUserLogin(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_LOGIN, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CustomSharedPreference.APPLICATION_USER_LOGIN, user);
        editor.commit();
    }

    public static String getUserLogin(final Context ctx) {
        return ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_LOGIN, Context.MODE_PRIVATE)
                .getString(CustomSharedPreference.APPLICATION_USER_LOGIN, "");
    }

    public static void setUserFullName(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_FULL_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CustomSharedPreference.APPLICATION_USER_FULL_NAME, user);
        editor.commit();
    }

    public static String getUserFullName(final Context ctx) {
        return ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_FULL_NAME, Context.MODE_PRIVATE)
                .getString(CustomSharedPreference.APPLICATION_USER_FULL_NAME, "");
    }

    public static void setUserPassword(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_PASSWORD, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CustomSharedPreference.APPLICATION_USER_PASSWORD, user);
        editor.commit();
    }

    public static String getUserPassword(final Context ctx) {
        return ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_PASSWORD, Context.MODE_PRIVATE)
                .getString(CustomSharedPreference.APPLICATION_USER_PASSWORD, "");
    }

    public static void setUserLoginStatus(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_LOGIN_STATUS, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CustomSharedPreference.APPLICATION_USER_LOGIN_STATUS, user);
        editor.commit();
    }

    public static String getUserLoginStatus(final Context ctx) {
        return ctx.getSharedPreferences(
                CustomSharedPreference.PREFERENCE_USER_LOGIN_STATUS, Context.MODE_PRIVATE)
                .getString(CustomSharedPreference.APPLICATION_USER_LOGIN_STATUS, "");
    }
}