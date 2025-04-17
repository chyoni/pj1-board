package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserRequest {
    private String userId;
    private String password;

    public static LoginUserRequest of(String userId, String password) {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.userId = userId;
        loginUserRequest.password = password;
        return loginUserRequest;
    }
}
