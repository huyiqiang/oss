package com.s3cmd.s3.object.lowApiUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.s3cmd.util.S3Client;

import java.util.List;

/*使用低级别 Java API 检索正在进行的分段上传的列表*/
public class ListMultipartUploads {

    public static void main(String[] args) {
        AmazonS3 s3Client = S3Client.getS3();
        String bucketName = "hyqhyq";
        try {
            // 检索所有正在进行的多部分上传的列表。
            ListMultipartUploadsRequest allMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
            MultipartUploadListing multipartUploadListing = s3Client.listMultipartUploads(allMultipartUploadsRequest);
            List<MultipartUpload> uploads = multipartUploadListing.getMultipartUploads();
            // 显示所有正在进行的多部分上传的信息。
            System.out.println(uploads.size() + " multipart upload(s) in progress.");
            for (MultipartUpload u : uploads) {
                System.out.println("Upload in progress: Key = \"" + u.getKey() + "\", id = " + u.getUploadId());
            }
        } catch (AmazonServiceException e) {
            // 调用被成功传输，但是Amazon S3不能处理它，所以它返回一个错误响应。
            e.printStackTrace();
        } catch (Exception e) {
            // 无法联系到Amazon S3获取响应，或者客户端无法解析来自Amazon S3的响应。
            e.printStackTrace();
        }
    }
}

