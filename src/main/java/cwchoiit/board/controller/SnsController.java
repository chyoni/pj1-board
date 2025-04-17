package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.SnsService;
import cwchoiit.board.service.request.PublishSnsRequest;
import cwchoiit.board.service.request.SubscribeSnsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sns")
public class SnsController {

    private final SnsService snsService;

    @PostMapping("/topic")
    @LoginCheck(type = ADMIN)
    public ResponseEntity<ApiResponse<String>> createTopic(@RequestParam("topicName") final String topicName) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(snsService.createTopic(topicName)));
    }

    @PostMapping("/subscribe")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<String>> subscribe(@RequestBody final SubscribeSnsRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(snsService.subscribeTopic(request)));
    }

    @PostMapping("/publish")
    @LoginCheck(type = ADMIN)
    public ResponseEntity<ApiResponse<String>> publish(@RequestBody final PublishSnsRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(snsService.publish(request)));
    }
}
