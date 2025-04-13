package cwchoiit.board.mapper;

import cwchoiit.board.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CategoryMapper {
    Optional<Category> read(@Param("id") Integer id);

    int register(Category category);

    void update(Category category);

    void delete(@Param("id") Integer id);
}
