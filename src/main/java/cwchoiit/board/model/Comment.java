package cwchoiit.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String contents;
    private Integer subCommentId;

    public static Comment create(Integer postId, Integer userId, String contents) {
        Comment comment = new Comment();
        comment.postId = postId;
        comment.userId = userId;
        comment.contents = contents;
        return comment;
    }

    public Comment withSubComment(Integer subCommentId) {
        this.subCommentId = subCommentId;
        return this;
    }

    public Comment updateWithContents(String contents) {
        this.contents = contents;
        return this;
    }
}
