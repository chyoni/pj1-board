package cwchoiit.board.mapper;

import cwchoiit.board.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    Optional<Post> read(@Param("id") Integer id);

    List<Post> readAllMy(@Param("userId") String userId);

    int insert(Post post);

    void update(Post post);

    void delete(@Param("id") Integer id);
}
