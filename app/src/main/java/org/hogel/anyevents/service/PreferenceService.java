package org.hogel.anyevents.service;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceService {
    private static PreferenceService INSTANCE = null;

    private final SharedPreferences prefs;

    private enum Key {
        GCM_TOKEN,
        SNS_ENDPOINT,
        ;
    }

    public static synchronized void initialize(Context context) {
        if (INSTANCE != null) {
            throw new IllegalStateException("already initialized.");
        }
        INSTANCE = new PreferenceService(context);
    }

    private PreferenceService(Context context) {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    private static String get(Key key) {
        return INSTANCE.prefs.getString(key.name(), null);
    }

    private static void put(Key key, String value) {
        INSTANCE.prefs.edit().putString(key.name(), value).apply();
    }

    public static String getGcmToken() {
        return get(Key.GCM_TOKEN);
    }

    public static void putGcmToken(String gcmToken) {
        put(Key.GCM_TOKEN, gcmToken);
    }

    public static String getSnsEndpoint() {
        return get(Key.SNS_ENDPOINT);
    }

    public static void putSnsEndpoint(String snsEndpoint) {
        put(Key.SNS_ENDPOINT, snsEndpoint);
    }
}
