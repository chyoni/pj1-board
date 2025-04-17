package cwchoiit.board.service.impl;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import cwchoiit.board.exception.SlackSnsException;
import cwchoiit.board.service.request.SlackMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.bot.user.oauth.token}")
    private String oauthToken;

    public boolean sendSlackMessage(SlackMessageRequest request) {
        MethodsClient client = Slack.getInstance().methods(oauthToken);

        try {
            ChatPostMessageResponse chatPostMessageResponse = client.chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .channel(request.getChannel().getChannelName())
                            .text(request.getMessage())
                            .build()
            );

            if (!chatPostMessageResponse.isOk()) {
                throw new SlackSnsException(
                        "Send slack message is not OK. error: " + chatPostMessageResponse.getError() +
                                ", request message " + request.getMessage() +
                                ", request channel " + request.getChannel().getChannelName()
                );
            }

            return chatPostMessageResponse.isOk();
        } catch (IOException | SlackApiException e) {
            log.warn("[sendSlackMessage] Send slack message error, message = {}, channel = {}",
                    request.getMessage(), request.getChannel().getChannelName());
            throw new SlackSnsException(e);
        }
    }
}
