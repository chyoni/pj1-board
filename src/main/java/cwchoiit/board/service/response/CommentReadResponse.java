package cwchoiit.board.service.response;

import cwchoiit.board.model.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentReadResponse {
    private Integer id;
    private String contents;
    private Integer postId;
    private Integer userId;
    private Integer subCommentId;

    public static CommentReadResponse from(Comment comment) {
        CommentReadResponse response = new CommentReadResponse();
        response.id = comment.getId();
        response.contents = comment.getContents();
        response.postId = comment.getPostId();
        response.userId = comment.getUserId();
        response.subCommentId = comment.getSubCommentId();
        return response;
    }
}
