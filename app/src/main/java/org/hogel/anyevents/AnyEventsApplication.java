package org.hogel.anyevents;

import android.app.Application;
import org.hogel.anyevents.service.AwsService;
import org.hogel.anyevents.service.PreferenceService;
import org.hogel.anyevents.service.PushTokenService;

public class AnyEventsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceService.initialize(this);
        AwsService.initialize(this);
        PushTokenService.initialize(this);
    }
}
