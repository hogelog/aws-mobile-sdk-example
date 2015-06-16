package org.hogel.anyevents.service;

import android.content.Context;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.hogel.anyevents.constant.AwsConstant;

public class AwsService {
    private static AwsService INSTANCE;

    private final CognitoCachingCredentialsProvider credentialsProvider;

    private final AmazonSNSClient snsClient;

    private final DynamoDBMapper ddbMapper;

    public static synchronized void initialize(Context context) {
        if (INSTANCE != null) {
            throw new IllegalStateException("already initialized.");
        }
        INSTANCE = new AwsService(context);
    }

    public static CognitoCachingCredentialsProvider getCredentialsProvider() {
        return INSTANCE.credentialsProvider;
    }

    public static AmazonSNSClient getSnsClient() {
        return INSTANCE.snsClient;
    }

    public static DynamoDBMapper getDdbMapper() {
        return INSTANCE.ddbMapper;
    }

    private AwsService(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
            context.getApplicationContext(),
            AwsConstant.COGNITO_IDENTITY_POOL_ID,
            AwsConstant.COGNITO_REGION
        );

        snsClient = new AmazonSNSClient(credentialsProvider);
        snsClient.setRegion(AwsConstant.SNS_REGION);

        AmazonDynamoDB ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(AwsConstant.DDB_REGION);
        ddbMapper = new DynamoDBMapper(ddbClient);
    }
}
