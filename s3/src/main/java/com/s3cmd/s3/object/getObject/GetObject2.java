package com.s3cmd.s3.object.getObject;

import com.amazonaws.AmazonServiceException;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.s3cmd.util.S3Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/*当通过AWS SDK for Java下载某个对象时，Amazon S3 将返回该对象的所有元数据以及从中读取该对象的内容的输入流。*/
public class GetObject2 {

    public static void main(String[] args) throws IOException {
        String bucketName = "hyqhyq";
        String key = "政治生日感言.txt";

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try {
            AmazonS3 s3Client = S3Client.getS3();

            // 获取一个对象并输出其内容.
            System.out.println("开始下载一个对象");
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            System.out.println("内容: ");
            displayTextInputStream(fullObject.getObjectContent());
            System.out.println("***************************");
            System.out.println("***************************");


            // 获取一个对象的字节范围并打印该范围内的字节
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key)
                    .withRange(0, 9);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            System.out.println("打印接收到的字节");
            displayTextInputStream(objectPortion.getObjectContent());
            System.out.println("***************************");
            System.out.println("***************************");


            // 获取整个对象、覆盖指定的响应头、并打印对象的内容
            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
                    .withCacheControl("No-cache")
                    .withContentDisposition("attachment; filename=example.txt");
            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
                    .withResponseHeaders(headerOverrides);
            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
            displayTextInputStream(headerOverrideObject.getObjectContent());


        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (Exception e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
            if (objectPortion != null) {
                objectPortion.close();
            }
            if (headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // 从输入流读取一行并展示每一行
        //解决读取文件中文乱码问题
        InputStreamReader isr = new InputStreamReader(input, "GBK");
        BufferedReader reader = new BufferedReader(isr);
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
}
