package com.s3cmd.s3.lifecycleConfiguration;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Transition;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleAndOperator;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecyclePrefixPredicate;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;
import com.s3cmd.util.S3Client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LifecycleConfiguration {

    public static void main(String[] args) throws IOException {
        String bucketName = "hyqhyq";

       //创建一个规则，将带有“glacierobjects/”前缀的对象立即归档到 Glacier
        BucketLifecycleConfiguration.Rule rule1 = new BucketLifecycleConfiguration.Rule()
                .withId("Archive immediately rule")
                .withFilter(new LifecycleFilter(new LifecyclePrefixPredicate("glacierobjects/")))
                .addTransition(new Transition().withDays(1).withStorageClass(StorageClass.Glacier))
                .withStatus(BucketLifecycleConfiguration.ENABLED);

        // 创建一个规则，在30天后将对象转换为标准的不频繁访问存储类，然后在365天后转换为Glacier。Amazon S3将在3650天后删除这些对象。该规则适用于标签“archive”设置为“true”的所有对象。
        BucketLifecycleConfiguration.Rule rule2 = new BucketLifecycleConfiguration.Rule()
                .withId("Archive and then delete rule")
                .withFilter(new LifecycleFilter(new LifecycleTagPredicate(new Tag("archive", "true"))))
                .addTransition(new Transition().withDays(30).withStorageClass(StorageClass.StandardInfrequentAccess))
                .addTransition(new Transition().withDays(365).withStorageClass(StorageClass.Glacier))
                .withExpirationInDays(3650)
                .withStatus(BucketLifecycleConfiguration.ENABLED);

        // 将规则添加到新的bucketlifecyclconfiguration。
        BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
                .withRules(Arrays.asList(rule1, rule2));

        try {

            String access_key = "6DEB63B3F2314C57A5377C29D4782E5D1533";
            String secret_key = "261845A5C87543449964EFD5ED575AD41512";
            String endpoint = "http://s3.dev.com:8080";
            AmazonS3 s3Client = S3Client.getS3Client(access_key,secret_key,endpoint);


            //保存配置。
            s3Client.setBucketLifecycleConfiguration(bucketName, configuration);

            // 检索配置。
            configuration = s3Client.getBucketLifecycleConfiguration(bucketName);

            // 添加一个具有前缀谓词和标记谓词的新规则。
            configuration.getRules().add(new BucketLifecycleConfiguration.Rule().withId("NewRule")
                    .withFilter(new LifecycleFilter(new LifecycleAndOperator(
                            Arrays.asList(new LifecyclePrefixPredicate("YearlyDocuments/"),
                                    new LifecycleTagPredicate(new Tag("expire_after", "ten_years"))))))
                    .withExpirationInDays(3650)
                    .withStatus(BucketLifecycleConfiguration.ENABLED));

            // 保存配置。
            s3Client.setBucketLifecycleConfiguration(bucketName, configuration);

            // 检索配置。
            configuration = s3Client.getBucketLifecycleConfiguration(bucketName);
            List<BucketLifecycleConfiguration.Rule> rules = configuration.getRules();
            System.out.println(rules);
            for (BucketLifecycleConfiguration.Rule i:rules) {
                System.out.println("现有规则"+i.getFilter().getPredicate());
            }

            //验证配置现在有三条规则。
            configuration = s3Client.getBucketLifecycleConfiguration(bucketName);
            System.out.println("现有规则数量; found: " + configuration.getRules().size());


            //删除配置。
            s3Client.deleteBucketLifecycleConfiguration(bucketName);

            //尝试检索配置，以验证配置是否已被删除。
            configuration = s3Client.getBucketLifecycleConfiguration(bucketName);
            String s = (configuration == null) ? "No configuration found." : "Configuration found.";
            System.out.println("删除后查看规则"+s);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
