package cwchoiit.board.service.impl;

import cwchoiit.board.exception.SnsException;
import cwchoiit.board.service.SnsService;
import cwchoiit.board.service.request.PublishSnsRequest;
import cwchoiit.board.service.request.SubscribeSnsRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSnsServiceImpl implements SnsService {

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private SnsClient client;

    @PostConstruct
    public void init() {
        client = SnsClient.builder()
                .credentialsProvider(getCredentialsProvider())
                .region(Region.of(region))
                .build();
    }

    @Override
    public String createTopic(String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        CreateTopicResponse createTopicResponse = client.createTopic(createTopicRequest);

        if (!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            log.warn("[createTopic] Create topic failed. topicName: {}", topicName);

            throw new SnsException(
                    "Create sns topic error. status code: " + createTopicResponse.sdkHttpResponse().statusCode() +
                            ", status text: " + createTopicResponse.sdkHttpResponse().statusText()
            );
        }

        log.debug("[createTopic] Topic ARN: {}", createTopicResponse.topicArn());
        return createTopicResponse.topicArn();
    }

    /**
     * 사용자가 SNS 구독.
     * <p>
     * {@code Protocol}은 다양한데, {@code email}, {@code https} 등 여러 프로토콜을 가질 수 있고,
     * {@code Endpoint}는 각 설정한 프로토콜에 맞게 이메일 또는 URL(예: 웹훅)을 입력하면 된다.
     *
     * @param request {@link SubscribeSnsRequest}
     * @return {@code subscriptionArn}
     */
    @Override
    public String subscribeTopic(SubscribeSnsRequest request) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("email")
                .topicArn(request.getTopicArn())
                .endpoint(request.getEndpoint())
                .build();

        final SubscribeResponse subscribeResponse = client.subscribe(subscribeRequest);
        if (!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            log.warn("[subscribeTopic] Subscribe topic failed. topicArn: {}, endpoint: {}", request.getTopicArn(), request.getEndpoint());

            throw new SnsException(
                    "Create subscribe error. status code: " + subscribeResponse.sdkHttpResponse().statusCode() +
                            ", status text: " + subscribeResponse.sdkHttpResponse().statusText()
            );
        }

        log.debug("[subscribe] Topic ARN to subscribe = {}", subscribeResponse.subscriptionArn());
        return subscribeResponse.subscriptionArn();
    }

    @Override
    public String publish(PublishSnsRequest request) {
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(request.getTopicArn())
                .subject("[PJ1 Board] AWS SNS")
                .message(request.getMessage())
                .build();

        PublishResponse publishResponse = client.publish(publishRequest);
        if (!publishResponse.sdkHttpResponse().isSuccessful()) {
            log.warn("[publish] Publish message failed. topicArn: {}, message: {}", request.getTopicArn(), request.getMessage());

            throw new SnsException(
                    "SNS Publish error. status code: " + publishResponse.sdkHttpResponse().statusCode() +
                            ", status text: " + publishResponse.sdkHttpResponse().statusText()
            );
        }
        return publishResponse.messageId();
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        return () -> AwsBasicCredentials.create(accessKey, secretKey);
    }
}
