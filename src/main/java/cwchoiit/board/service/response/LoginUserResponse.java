package cwchoiit.board.service.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.User;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserResponse {
    private int id;
    private String userId;
    private String nickname;
    private boolean isAdmin;

    public static LoginUserResponse of(UserInfoResponse userInfoResponse) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.id = userInfoResponse.getId();
        loginUserResponse.userId = userInfoResponse.getUserId();
        loginUserResponse.nickname = userInfoResponse.getNickname();
        loginUserResponse.isAdmin = userInfoResponse.isAdmin();
        return loginUserResponse;
    }
}
