package cwchoiit.board.mapper;

import cwchoiit.board.model.Post;
import cwchoiit.board.service.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostSearchMapper {

    List<Post> readAll(PostSearchRequest request);

    List<Post> readAllByTag(@Param("tagName") String tagName);
}
