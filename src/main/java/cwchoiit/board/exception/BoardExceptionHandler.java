package cwchoiit.board.exception;

import cwchoiit.board.controller.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateIdException.class)
    public ApiResponse<?> handleDuplicateIdException(DuplicateIdException e) {
        log.error("[handleDuplicateIdException] DuplicateIdException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(LoginCheckException.class)
    public ApiResponse<?> handleLoginCheckException(LoginCheckException e) {
        log.error("[handleLoginCheckException] LoginCheckException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCategoryException.class)
    public ApiResponse<?> handleNotFoundCategoryException(NotFoundCategoryException e) {
        log.error("[handleNotFoundCategoryException] NotFoundCategoryException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundUserException.class)
    public ApiResponse<?> handleNotFoundUserException(NotFoundUserException e) {
        log.error("[handleNotFoundUserException] NotFoundUserException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayloadValidationException.class)
    public ApiResponse<?> handlePayloadValidationException(PayloadValidationException e) {
        log.error("[handlePayloadValidationException] PayloadValidationException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("[handleRuntimeException] RuntimeException ", e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("[handleException] Exception ", e);
        return ApiResponse.error(e.getMessage());
    }
}
