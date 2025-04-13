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
}
