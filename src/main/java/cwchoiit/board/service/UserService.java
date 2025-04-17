package cwchoiit.board.service;

import cwchoiit.board.service.request.LoginUserRequest;
import cwchoiit.board.service.request.RegisterUserRequest;
import cwchoiit.board.service.request.UpdatePasswordRequest;
import cwchoiit.board.service.response.LoginUserResponse;
import cwchoiit.board.service.response.UserInfoResponse;

public interface UserService {
    void register(RegisterUserRequest request);

    LoginUserResponse login(LoginUserRequest request);

    UserInfoResponse getUserInfo(String id);

    void updatePassword(String id, UpdatePasswordRequest request);

    void deleteUser(String id, String password);
}
