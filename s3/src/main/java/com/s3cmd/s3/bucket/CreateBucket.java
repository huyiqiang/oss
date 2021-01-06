package com.s3cmd.s3.bucket;
/*
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.s3cmd.util.S3Client;

import java.io.IOException;

//该类用来创建存储桶bucket
public class CreateBucket {
    public static void main(String args[]) throws IOException {
        String bucketName = "jjj";
        try{
            AmazonS3 s3Client = S3Client.getS3();
            if (!s3Client.doesBucketExistV2(bucketName)){
                //因为CreateBucketRequest对象没有指定区域，所以桶是在客户机中指定的区域中创建的。
                s3Client.createBucket(new CreateBucketRequest(bucketName));
                //通过检索桶并检查其位置检查是否成功创建了存储桶bucket
                String bucketLocaltion = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("存储桶位置："+bucketLocaltion);
            }

        }catch (AmazonServiceException e){
            e.printStackTrace();
        }catch (SdkClientException e){
            e.printStackTrace();
        }
    }
}*/
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.s3cmd.util.S3Client;

import java.io.IOException;

public class CreateBucket {

    public static void main(String[] args) throws IOException {
        String bucketName = "demo";

        try {
            AmazonS3 s3Client = S3Client.getS3();

            if (!s3Client.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(bucketName));

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}