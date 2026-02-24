package com.sprint.mission.discodeit.storage.s3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class AWSS3Test {

  private S3Client s3Client;
  private S3Presigner s3Presigner;
  private String bucketName;

  @BeforeEach
  void setUp() throws IOException {
    //Properties 클래스를 활용해 .env 정보 로드
    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream(".env")) {
      props.load(fis);
    }

    String accessKey = props.getProperty("AWS_S3_ACCESS_KEY");
    String secretKey = props.getProperty("AWS_S3_SECRET_KEY");
    String region = props.getProperty("AWS_S3_REGION");
    this.bucketName = props.getProperty("AWS_S3_BUCKET");

    //S3 클라이언트 설정
    AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

    this.s3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();

    this.s3Presigner = S3Presigner.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }

  @Test
  void uploadTest() {
    //대상혁 사진 업로드 테스트
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key("goat.png")
        .build();

    s3Client.putObject(putObjectRequest, Paths.get("faker.png"));
    System.out.println("업로드 성공!");
  }

  @Test
  void downloadTest() {
    //신상혁의 사진 다운로드
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key("goat.png")
        .build();

    s3Client.getObject(getObjectRequest, Paths.get("downloaded-faker.png"));
    System.out.println("다운로드 성공!");
  }

  @Test
  void presignedUrlTest() {
    //숭배url 확인 테스트
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key("goat.png")
        .build();

    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(10)) // 10분 유효
        .getObjectRequest(getObjectRequest)
        .build();

    PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
    System.out.println("생성된 URL: " + presignedRequest.url().toString());
  }
}