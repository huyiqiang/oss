package com.s3cmd.s3.object.copyObject;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.s3cmd.util.S3Client;
import java.io.IOException;

public class CopyObjectSingleOperation {

    public static void main(String[] args) throws IOException {
        String access_key = "6DEB63B3F2314C57A5377C29D4782E5D1533";
        String secret_key = "261845A5C87543449964EFD5ED575AD41512";
        String endpoint = "http://s3.dev.com:8080";
        String bucketName = "hyqhyq";
        String sourceKey = "23.txt";
        String destinationKey = "235.txt";

        try {
            AmazonS3 s3Client = S3Client.getS3Client(access_key,secret_key,endpoint);
            // Copy the object into a new object in the same bucket.
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, sourceKey, bucketName, destinationKey);
            ObjectMetadata objectMetadata =new ObjectMetadata();
            objectMetadata.setHeader("x-amz-tagging-directive","REPLACE");
            objectMetadata.setHeader("x-amz-tagging","dd=dd,aa=aa");
            copyObjRequest.setNewObjectMetadata(objectMetadata);
            s3Client.copyObject(copyObjRequest);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}

