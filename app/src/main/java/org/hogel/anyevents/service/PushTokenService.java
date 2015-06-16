package org.hogel.anyevents.service;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;

import org.hogel.anyevents.R;
import org.hogel.anyevents.constant.AwsConstant;
import org.hogel.anyevents.model.PushToken;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PushTokenService {
    private static final String TAG = PushTokenService.class.getSimpleName();

    private static PushTokenService INSTANCE;

    private final Context context;

    private final Executor executor;

    public static synchronized void initialize(Context context) {
        if (INSTANCE != null) {
            throw new IllegalStateException("already initialized.");
        }
        INSTANCE = new PushTokenService(context);
    }

    public static void refresh() {
        INSTANCE.refreshToken();
    }

    private PushTokenService(Context context) {
        this.context = context.getApplicationContext();
        executor = Executors.newSingleThreadExecutor();

        initializeToken();
    }

    private void refreshToken() {
        PreferenceService.putGcmToken(null);
        PreferenceService.putSnsEndpoint(null);
        initializeToken();
    }

    private void initializeToken() {
        if (PreferenceService.getGcmToken() != null) {
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                registerToken();
            }
        });
    }

    private void registerToken() {
        try {
            String cognitoIdentityId = AwsService.getCredentialsProvider().getIdentityId();
            String gcmToken = registerGcmToken();
            String snsEndpoint = registerSnsEndpoint(gcmToken);

            Log.i(TAG, "GCM Registration Token: " + gcmToken);
            Log.i(TAG, "SNS Endpoint: " + snsEndpoint);

            PushToken pushToken = new PushToken(cognitoIdentityId, gcmToken, snsEndpoint);
            putPushToken(pushToken);

            PreferenceService.putGcmToken(gcmToken);
            PreferenceService.putSnsEndpoint(snsEndpoint);
        } catch (IOException|AmazonClientException e) {
            Log.e(TAG, "Registration Failure", e);
            return;
        }
    }

    private String registerGcmToken() throws IOException {
        InstanceID instanceID = InstanceID.getInstance(context);
        return instanceID.getToken(context.getString(R.string.gcm_sender_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
    }

    private String registerSnsEndpoint(String gcmToken) {
        AmazonSNSClient snsClient = AwsService.getSnsClient();

        CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();
        platformEndpointRequest.setPlatformApplicationArn(AwsConstant.SNS_APPLICATION_ARN);
        platformEndpointRequest.setToken(gcmToken);
        CreatePlatformEndpointResult platformEndpoint = snsClient.createPlatformEndpoint(platformEndpointRequest);
        String snsEndpoint = platformEndpoint.getEndpointArn();

        snsClient.subscribe(AwsConstant.SNS_TOPIC_ARN, "application", snsEndpoint);
        return snsEndpoint;
    }

    private void putPushToken(PushToken pushToken) {
        DynamoDBMapper ddbMapper = AwsService.getDdbMapper();
        ddbMapper.save(pushToken);
    }
}
