package cwchoiit.board.mapper;

import cwchoiit.board.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> read(@Param("id") String id);

    int insert(User user);

    void delete(@Param("id") String id);

    Optional<User> findByUserIdAndPassword(User user);

    Optional<User> findByIdAndPassword(User user);

    int idCheck(@Param("userId") String userId);

    void updatePassword(User user);
}
