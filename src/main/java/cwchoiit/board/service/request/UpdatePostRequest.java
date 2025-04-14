package cwchoiit.board.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePostRequest {
    private String name;
    private String contents;
    private Integer categoryId;
    private Integer fileId;
    private Integer views;
    private LocalDateTime updatedAt;
}
