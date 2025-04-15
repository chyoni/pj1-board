package cwchoiit.board.mapper;

import cwchoiit.board.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface TagMapper {
    Optional<Tag> read(Integer tagId);

    int create(Tag tag);

    void update(Tag tag);

    void delete(@Param("tagId") Integer tagId);

    void mapPostTag(@Param("tagId") Integer tagId, @Param("postId") Integer postId);
}
