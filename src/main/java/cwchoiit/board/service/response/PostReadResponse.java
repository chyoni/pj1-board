package cwchoiit.board.service.response;

import cwchoiit.board.model.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostReadResponse {
    private Integer id;
    private String name;
    private String contents;
    private Integer categoryId;
    private Integer userId;
    private Integer fileId;
    private Integer views;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostReadResponse from(Post post) {
        PostReadResponse response = new PostReadResponse();
        response.id = post.getId();
        response.name = post.getName();
        response.contents = post.getContents();
        response.categoryId = post.getCategoryId();
        response.userId = post.getUserId();
        response.fileId = post.getFileId();
        response.views = post.getViews();
        response.admin = post.isAdmin();
        response.createdAt = post.getCreatedAt();
        response.updatedAt = post.getUpdatedAt();
        return response;
    }
}
