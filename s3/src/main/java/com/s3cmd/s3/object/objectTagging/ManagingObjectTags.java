package com.s3cmd.s3.object.objectTagging;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.s3cmd.util.S3Client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManagingObjectTags {

    public static void main(String[] args) {
        String access_key = "0A52F27732A14E82BFB03744C6324AC55773";
        String secret_key = "E7C1FC19586040D58C0AF8E494FC58EE1561";
        String endpoint = "http://s3.dev.com:8080";

        String bucketName = "123";
        String keyName = "12345.txt";
        String filePath = "E:\\AA\\12345.txt";

        try {
            AmazonS3 s3Client = S3Client.getS3Client(access_key,secret_key,endpoint);

            // 创建一个对象, 添加两个标签, 并且下载对象到Amazon S3.
//           PutObjectRequest putRequest = new PutObjectRequest(bucketName, keyName, new File(filePath));
//            List<Tag> tags = new ArrayList<Tag>();
//            tags.add(new Tag("aa", "aa"));
//            tags.add(new Tag("bb", "bb"));
//            putRequest.setTagging(new ObjectTagging(tags));
//            PutObjectResult putResult = s3Client.putObject(putRequest);

            // 检索对象的tag标签.
            GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(bucketName, keyName);
            GetObjectTaggingResult getTagsResult = s3Client.getObjectTagging(getTaggingRequest);
            System.out.println("对象标签为："+getTagsResult);

            // 替换对象标签.
            List<Tag> newTags = new ArrayList<Tag>();
            newTags.add(new Tag("Tag 3", "This is tag 3"));
            newTags.add(new Tag("Tag 4", "This is tag 4") );
            s3Client.setObjectTagging(new SetObjectTaggingRequest(bucketName, keyName, new ObjectTagging(newTags)));

            //删除对象标签
            /*DeleteObjectTaggingRequest deleteObjectTaggingRequest = new DeleteObjectTaggingRequest(bucketName, keyName);
            s3Client.deleteObjectTagging(deleteObjectTaggingRequest);*/

        } catch (AmazonServiceException e) {
            // 请求成功提交,但是Amazon S3不能处理它，所以返回一个错误响应
            e.printStackTrace();
        } catch (Exception e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
