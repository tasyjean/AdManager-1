package models.custom_helper;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import play.Application;
import play.Logger;
import play.Plugin;

//untuk mengelola penyimpanan AWS
public class S3Manager extends Plugin {

    public static final String AWS_S3_BUCKET = "aws.s3.bucket";
    public static final String AWS_ACCESS_KEY = "aws.access.key";
    public static final String AWS_SECRET_KEY = "aws.secret.key";
    public static final String AWS_ENDPOINT ="aws.endpoint";
    private final Application application;

    public static AmazonS3 amazonS3;

    public static String s3Bucket;

    public S3Manager(Application application) {
        this.application = application;
    }

    @Override
    public void onStart() {
     /*   String accessKey = application.configuration().getString(AWS_ACCESS_KEY);
        String secretKey = application.configuration().getString(AWS_SECRET_KEY);
        s3Bucket = application.configuration().getString(AWS_S3_BUCKET);

        if ((accessKey != null) && (secretKey != null)) {
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            amazonS3 = new AmazonS3Client(awsCredentials);
            amazonS3.setEndpoint(application.configuration().getString(AWS_ENDPOINT));            
            amazonS3.createBucket(s3Bucket);
            Logger.info("Using S3 Bucket: " + s3Bucket);
        } */
    }

    @Override
    public boolean enabled() {
    	return false;
        /*return (application.configuration().keys().contains(AWS_ACCESS_KEY) &&
                application.configuration().keys().contains(AWS_SECRET_KEY) &&
                application.configuration().keys().contains(AWS_S3_BUCKET)); */
    }
    
}