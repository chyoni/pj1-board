package cwchoiit.board.service.impl;

import cwchoiit.board.SpringBootTestWithTestContainer;
import cwchoiit.board.service.request.SlackMessageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Service - SlackService")
class SlackServiceTest extends SpringBootTestWithTestContainer {

    @Autowired
    private SlackService slackService;

    @Test
    @DisplayName("슬랙 메시지 - 전송 성공")
    void send_message() {
        boolean isOK = slackService.sendSlackMessage(
                SlackMessageRequest.of("Board Message\n\nHello Slack?!", SlackMessageRequest.Channel.ERROR)
        );

        assertThat(isOK).isTrue();
    }
}