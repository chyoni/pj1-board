package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterPostRequest {
    private String name;
    private String contents;
    private Integer categoryId;
    private Integer fileId;
    private boolean admin;
}
