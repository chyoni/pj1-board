package cwchoiit.board.mapper;

import cwchoiit.board.service.request.RegisterUserRequest;
import cwchoiit.board.service.request.UpdatePasswordRequest;
import cwchoiit.board.service.response.UserInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserInfoResponse read(@Param("id") String id);

    int insert(RegisterUserRequest request);

    void delete(@Param("id") String id);

    UserInfoResponse findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    UserInfoResponse findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(@Param("userId") String userId);

    void updatePassword(@Param("id") String id, @Param("req") UpdatePasswordRequest request);
}
