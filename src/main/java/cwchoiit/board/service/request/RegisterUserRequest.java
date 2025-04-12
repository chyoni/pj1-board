package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserRequest {
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isWithdraw;

    public RegisterUserRequest with(String encryptPassword) {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.password = encryptPassword;
        return this;
    }
}
