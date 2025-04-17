package cwchoiit.board.exception;

public class SlackSnsException extends RuntimeException {
    public SlackSnsException(Throwable cause) {
        super(cause);
    }

    public SlackSnsException(String message) {
        super(message);
    }
}
