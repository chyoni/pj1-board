package cwchoiit.board.mapper;

import cwchoiit.board.model.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {

    Optional<File> readById(@Param("fileId") Integer fileId);

    Optional<File> readByPath(@Param("path") String path);

    List<File> readAllByPostId(@Param("postId") Integer postId);

    int create(File file);

    void updatePostId(@Param("fileId") Integer fileId, @Param("postId") Integer postId);

    void delete(@Param("fileId") Integer fileId);
}
