package org.hogel.anyevents.constant;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class AwsConstant {
    public static final Regions COGNITO_REGION = Regions.US_EAST_1;
    public static final String COGNITO_IDENTITY_POOL_ID =  "us-east-1:11111111-2222-3333-4444-555555555555";

    public static final Region SNS_REGION = Region.getRegion(Regions.AP_NORTHEAST_1);
    public static final String SNS_APPLICATION_ARN = "arn:aws:sns:ap-northeast-1:123456789012:app/GCM/AndroidPushApp";
    public static final String SNS_TOPIC_ARN = "arn:aws:sns:ap-northeast-1:123456789012:campaign-development";

    public static final Region DDB_REGION = Region.getRegion(Regions.AP_NORTHEAST_1);
    public static final String DDB_TABLE_NAME = "push_tokens";
}
