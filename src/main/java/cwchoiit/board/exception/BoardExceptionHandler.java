package cwchoiit.board.exception;

import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.impl.SlackService;
import cwchoiit.board.service.request.SlackMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BoardExceptionHandler {

    private final SlackService slackService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateIdException.class)
    public ApiResponse<?> handleDuplicateIdException(DuplicateIdException e) {
        log.error("[handleDuplicateIdException] DuplicateIdException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-DuplicateIdException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(LoginCheckException.class)
    public ApiResponse<?> handleLoginCheckException(LoginCheckException e) {
        log.error("[handleLoginCheckException] LoginCheckException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-LoginCheckException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCategoryException.class)
    public ApiResponse<?> handleNotFoundCategoryException(NotFoundCategoryException e) {
        log.error("[handleNotFoundCategoryException] NotFoundCategoryException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-NotFoundCategoryException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundUserException.class)
    public ApiResponse<?> handleNotFoundUserException(NotFoundUserException e) {
        log.error("[handleNotFoundUserException] NotFoundUserException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-NotFoundUserException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundPostException.class)
    public ApiResponse<?> handleNotFoundPostException(NotFoundPostException e) {
        log.error("[handleNotFoundPostException] NotFoundPostException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-NotFoundPostException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCommentException.class)
    public ApiResponse<?> handleNotFoundCommentException(NotFoundCommentException e) {
        log.error("[handleNotFoundCommentException] NotFoundCommentException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-NotFoundCommentException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundTagException.class)
    public ApiResponse<?> handleNotFoundTagException(NotFoundTagException e) {
        log.error("[handleNotFoundTagException] NotFoundTagException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-NotFoundTagException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayloadValidationException.class)
    public ApiResponse<?> handlePayloadValidationException(PayloadValidationException e) {
        log.error("[handlePayloadValidationException] PayloadValidationException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-PayloadValidationException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SnsException.class)
    public ApiResponse<?> handleSnsException(SnsException e) {
        log.error("[handleSnsException] SnsException ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-SnsException] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SlackSnsException.class)
    public ApiResponse<?> handleSlackSnsException(SlackSnsException e) {
        log.error("[handleSlackSnsException] SlackSnsException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("[handleException] Exception ", e);
        slackService.sendSlackMessage(
                SlackMessageRequest.of(
                        "[Board-Exception] Check Server log ASAP.\n\n " +
                                "log error message: " + e.getMessage(),
                        SlackMessageRequest.Channel.ERROR
                )
        );
        return ApiResponse.error("Sorry, something went wrong. Please try again in a few moments.");
    }
}
