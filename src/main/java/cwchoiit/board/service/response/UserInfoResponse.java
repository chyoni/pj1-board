package cwchoiit.board.service.response;

import cwchoiit.board.model.User;
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

    public static UserInfoResponse of(User user) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.id = user.getId();
        userInfoResponse.userId = user.getUserId();
        userInfoResponse.password = user.getPassword();
        userInfoResponse.nickname = user.getNickname();
        userInfoResponse.isAdmin = user.isAdmin();
        userInfoResponse.createdAt = user.getCreatedAt();
        userInfoResponse.isWithdraw = user.isWithdraw();
        return userInfoResponse;
    }
}
