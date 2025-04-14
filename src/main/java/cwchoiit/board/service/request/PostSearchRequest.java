package cwchoiit.board.service.request;

import cwchoiit.board.model.Category.SortStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSearchRequest {
    private Integer id;
    private String name;
    private String contents;
    private Integer views;
    private Integer categoryId;
    private Integer userId;
    private SortStatus sortStatus;
}
