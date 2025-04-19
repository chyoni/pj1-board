package cwchoiit.board.service.impl;

import cwchoiit.board.exception.S3Exception;
import cwchoiit.board.mapper.FileMapper;
import cwchoiit.board.model.File;
import cwchoiit.board.service.response.FileReadResponse;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import cwchoiit.board.service.FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private S3Client client;

    private AwsCredentialsProvider getCredentialsProvider() {
        return () -> AwsBasicCredentials.create(accessKey, secretKey);
    }

    private String generateImageUrl(String key) {
        return "https://%s.s3.%s.amazonaws.com/%s".formatted(bucketName, region, key);
    }

    private Optional<String> extractExt(String originalFileName) {
        if (originalFileName == null) {
            return Optional.empty();
        }

        return Optional.of(
                originalFileName.substring(originalFileName.lastIndexOf('.') + 1)
        );
    }

    @PostConstruct
    public void init() {
        client = S3Client.builder()
                .credentialsProvider(getCredentialsProvider())
                .region(Region.of(region))
                .build();
    }

    @Override
    @Transactional
    public File upload(MultipartFile file) {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            PutObjectResponse putObjectResponse = client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );

            if (!putObjectResponse.sdkHttpResponse().isSuccessful()) {
                log.warn("[upload] Upload to S3 failed. filename: {}", file.getOriginalFilename());
                throw new S3Exception(
                        "Upload to S3 failed. status code: " + putObjectResponse.sdkHttpResponse().statusCode()
                        + ", status text: " + putObjectResponse.sdkHttpResponse().statusText()
                );
            }

            File newFile = File.create(
                    generateImageUrl(key),
                    file.getOriginalFilename(),
                    extractExt(file.getOriginalFilename()).orElse("")
            );
            fileMapper.create(newFile);

            return newFile;
        } catch (IOException e) {
            log.warn("[upload] Upload to S3 failed when call file.getBytes()");
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void mapToPost(Integer fileId, Integer postId) {
        fileMapper.readById(fileId)
                .orElseThrow(() -> new S3Exception("File not found with id: " + fileId));

        fileMapper.updatePostId(fileId, postId);
    }

    @Override
    public List<FileReadResponse> readAllByPost(Integer postId) {
        return fileMapper.readAllByPostId(postId).stream()
                .map(FileReadResponse::from)
                .toList();
    }
}
