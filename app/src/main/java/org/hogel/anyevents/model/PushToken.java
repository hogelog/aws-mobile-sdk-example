package org.hogel.anyevents.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import org.hogel.anyevents.constant.AwsConstant;

@DynamoDBTable(tableName = AwsConstant.DDB_TABLE_NAME)
public class PushToken {
    @DynamoDBHashKey(attributeName = "CognitoIdentityId")
    private final String cognitoIdentityId;

    @DynamoDBAttribute(attributeName = "GcmToken")
    private final String gcmToken;

    @DynamoDBAttribute(attributeName = "SnsEndpoint")
    private final String snsEndpoint;

    public PushToken(String cognitoIdentityId, String gcmToken, String snsEndpoint) {
        this.cognitoIdentityId = cognitoIdentityId;
        this.gcmToken = gcmToken;
        this.snsEndpoint = snsEndpoint;
    }

    public String getCognitoIdentityId() {
        return cognitoIdentityId;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public String getSnsEndpoint() {
        return snsEndpoint;
    }
}
