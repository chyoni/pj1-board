package cwchoiit.board.service.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {
    private int id;
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private LocalDateTime createdAt;
    private boolean isWithdraw;

    public static UserInfoResponse of(UserInfoResponse userInfoResponse) {
        UserInfoResponse user = new UserInfoResponse();
        user.id = userInfoResponse.id;
        user.userId = userInfoResponse.userId;
        user.password = userInfoResponse.password;
        user.nickname = userInfoResponse.nickname;
        user.isAdmin = userInfoResponse.isAdmin;
        user.createdAt = userInfoResponse.createdAt;
        user.isWithdraw = userInfoResponse.isWithdraw;
        return user;
    }
}
