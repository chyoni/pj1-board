package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserRequest {
    private String userId;
    private String password;
    private String nickname;
    private boolean admin;
    private boolean withdraw;

    public static RegisterUserRequest of(String userId,
                                         String password,
                                         String nickname,
                                         boolean admin,
                                         boolean withdraw) {
        RegisterUserRequest request = new RegisterUserRequest();
        request.userId = userId;
        request.password = password;
        request.nickname = nickname;
        request.admin = admin;
        request.withdraw = withdraw;
        return request;
    }
}
