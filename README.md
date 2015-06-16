# aws-mobile-example
AWS Mobile SDK Example Application (Push Notification)

## How-to
Configure [AwsConstant.java](https://github.com/hogelog/aws-mobile-sdk-example/blob/master/app/src/main/java/org/hogel/anyevents/constant/AwsConstant.java)
and [strings.xml](https://github.com/hogelog/aws-mobile-sdk-example/blob/master/app/src/main/res/values/strings.xml).

## Publish notification for this app.
Call [SNS Publish API](http://docs.aws.amazon.com/sns/latest/APIReference/API_Publish.html)
to your SNS Topic[#](https://github.com/hogelog/aws-mobile-sdk-example/blob/master/app/src/main/java/org/hogel/anyevents/constant/AwsConstant.java#L12).
