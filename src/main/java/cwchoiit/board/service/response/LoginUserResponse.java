package cwchoiit.board.service.response;

import cwchoiit.board.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserResponse {
    private int id;
    private String userId;
    private String nickname;
    private boolean isAdmin;

    public static LoginUserResponse of(User user) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.id = user.getId();
        loginUserResponse.userId = user.getUserId();
        loginUserResponse.nickname = user.getNickname();
        loginUserResponse.isAdmin = user.isAdmin();
        return loginUserResponse;
    }
}
