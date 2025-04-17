package cwchoiit.board.service.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlackMessageRequest {
    private String message;
    private Channel channel;

    public static SlackMessageRequest of(String message, Channel channel) {
        SlackMessageRequest slackMessageRequest = new SlackMessageRequest();
        slackMessageRequest.message = message;
        slackMessageRequest.channel = channel;
        return slackMessageRequest;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Channel {
        ERROR("#board-alarm"),
        INFO("#all-slack-api-alarm"),
        NEW_FEATURE("#social");

        private final String channelName;
    }
}
