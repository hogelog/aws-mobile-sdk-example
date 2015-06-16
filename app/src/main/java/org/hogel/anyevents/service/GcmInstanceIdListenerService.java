package org.hogel.anyevents.service;

import android.util.Log;
import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmInstanceIdListenerService extends InstanceIDListenerService {
    private static final String TAG = GcmInstanceIdListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        Log.i(TAG, "Refresh token.");
        PushTokenService.refresh();
    }
}
