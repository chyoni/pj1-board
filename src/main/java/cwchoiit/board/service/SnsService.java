package cwchoiit.board.service;

import cwchoiit.board.service.request.PublishSnsRequest;
import cwchoiit.board.service.request.SubscribeSnsRequest;

public interface SnsService {
    String createTopic(final String topicName);

    String subscribeTopic(final SubscribeSnsRequest request);

    String publish(final PublishSnsRequest request);
}
