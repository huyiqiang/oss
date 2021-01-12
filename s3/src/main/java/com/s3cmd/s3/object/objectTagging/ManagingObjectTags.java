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
    public static PutObjectResult uploadAndputObjectTagging(AmazonS3 s3Client,String bucketName,String keyName,String filepath){
        //创建一个对象，添加两个标签，并且下载对象到Amazon S3
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, new File(filepath));
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("aa","aa"));
        tags.add(new Tag("bb","bb"));
        ObjectMetadata objectMetadata =new ObjectMetadata();
       objectMetadata.setHeader("x-amz-tagging","aa=aa");
       putObjectRequest.setTagging(new ObjectTagging(tags));
        putObjectRequest.setMetadata(objectMetadata);
        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
        return putObjectResult;
    }


    public static GetObjectTaggingResult getObjectTagging(AmazonS3 s3Client,String bucketName,String keyName,String versionId){
        //检索对象的Tag标签
        GetObjectTaggingRequest getObjectTaggingRequest = new GetObjectTaggingRequest(bucketName, keyName,versionId);
        GetObjectTaggingResult objectTagging = s3Client.getObjectTagging(getObjectTaggingRequest);
        List<Tag> tagSet = objectTagging.getTagSet();
        return objectTagging;
    }
    public static GetObjectTaggingResult getObjectTagging1(AmazonS3 s3Client,String bucketName,String keyName){
        //检索对象的Tag标签
        GetObjectTaggingRequest getObjectTaggingRequest = new GetObjectTaggingRequest(bucketName, keyName);
        GetObjectTaggingResult objectTagging = s3Client.getObjectTagging(getObjectTaggingRequest);
        List<Tag> tagSet = objectTagging.getTagSet();
        return objectTagging;
    }

    public static SetObjectTaggingResult setObjectTagging(AmazonS3 s3Client,String bucket,String keyName){
        //设置对象标签
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("aa","aa"));
        tags.add(new Tag("bb","bb"));
        tags.add(new Tag("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccAAAAAAAAAA","aaAAAAAAdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"));
        tags.add(new Tag("dd","dd"));
        tags.add(new Tag("ee","ee"));
        tags.add(new Tag("ff","ff"));
        tags.add(new Tag("gg","gg"));
        SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(bucket, keyName, new ObjectTagging(tags));
        SetObjectTaggingResult setObjectTaggingResult = s3Client.setObjectTagging(setObjectTaggingRequest);
        System.out.println("版本号"+setObjectTaggingRequest.getVersionId());
        return setObjectTaggingResult;
    }

     public static DeleteObjectTaggingResult deleteObjectTagging(AmazonS3 s3Cliet,String bucketName,String keyName){
        //删除对象标签
         DeleteObjectTaggingRequest deleteObjectTaggingRequest = new DeleteObjectTaggingRequest(bucketName, keyName);
         DeleteObjectTaggingResult deleteObjectTaggingResult = s3Cliet.deleteObjectTagging(deleteObjectTaggingRequest);
         return deleteObjectTaggingResult;
     }

    public static DeleteObjectTaggingResult deleteObjectTagging(AmazonS3 s3Cliet,String bucketName,String keyName,String VersionId){
        //删除对象标签
        DeleteObjectTaggingRequest deleteObjectTaggingRequest = new DeleteObjectTaggingRequest(bucketName,keyName);
        DeleteObjectTaggingResult deleteObjectTaggingResult = s3Cliet.deleteObjectTagging(deleteObjectTaggingRequest);
        System.out.println(deleteObjectTaggingResult.getVersionId());
        return deleteObjectTaggingResult;
    }

    public static void main(String[] args) {
        try {
            String access_key = "6DEB63B3F2314C57A5377C29D4782E5D1533";
            String secret_key = "261845A5C87543449964EFD5ED575AD41512";
            String endpoint = "http://s3.dev.com:8080";
            String bucketName = "hyqhyq";
            String keyName = "pip-master.zip";
            String filePath = "E:\\AA\\12345.txt";
            AmazonS3 s3Client = S3Client.getS3Client(access_key,secret_key,endpoint);
//         PutObjectResult putObjectResult = ManagingObjectTags.uploadAndputObjectTagging(s3Client, bucketName, keyName, filePath);
//            System.out.println("上传结果："+putObjectResult);
//           SetObjectTaggingResult setObjectTaggingResult = ManagingObjectTags.setObjectTagging(s3Client, bucketName, keyName);
//           ManagingObjectTags.deleteObjectTagging(s3Client,bucketName,keyName);
            GetObjectTaggingResult objectTagging = ManagingObjectTags.getObjectTagging1(s3Client, bucketName, keyName);
            System.out.println("版本号"+objectTagging.getVersionId());
            List<Tag> tagSet = objectTagging.getTagSet();
            for (Tag i:tagSet) {
                System.out.println("对象标签："+i.getKey()+":"+i.getValue());
            }
            System.out.println("对象标签个数："+tagSet.size());
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
