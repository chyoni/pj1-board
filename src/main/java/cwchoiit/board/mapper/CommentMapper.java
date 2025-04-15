package cwchoiit.board.mapper;

import cwchoiit.board.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CommentMapper {

    Optional<Comment> read(@Param("postId") Integer postId, @Param("commentId") Integer commentId);

    int create(Comment comment);

    void update(Comment comment);

    void appendSubComment(Comment comment);

    void delete(@Param("commentId") Integer commentId);
}
