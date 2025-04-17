package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;

    public static UpdatePasswordRequest of(String oldPassword, String newPassword) {
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.oldPassword = oldPassword;
        request.newPassword = newPassword;
        return request;
    }
}
