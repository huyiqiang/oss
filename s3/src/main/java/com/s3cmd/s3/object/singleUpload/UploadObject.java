                                                                                                                                                                                                                                                                                                                                                                                 package com.s3cmd.s3.object.singleUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s3cmd.util.S3Client;

import java.io.File;
import java.io.IOException;
/*以下示例将创建两个对象。
 一：通过在对 AmazonS3Client.putObject() 的调用中直接指定存储桶名称、对象键和文本数据来创建第一个对象。
 二：通过使用指定存储桶、对象键和文件路径的 PutObjectRequest 来创建第二个对象。PutObjectRequest 还指定 ContentType 标头和标题元数据。*/
public class UploadObject {

    public static void main(String[] args) throws IOException {
        String bucketName = "hyqhyq";
        String stringObjKeyName = "Hb.zip";
        String fileObjKeyName = "apache-tomcat-7.0.81.zip";
        String fileName = "E:\\AA\\1122\\apache-tomcat-7.0.81.zip";
        String fileName1 ="";
        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = S3Client.getS3();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, new File("E:\\java\\Hb.zip"));
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (Exception e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}

