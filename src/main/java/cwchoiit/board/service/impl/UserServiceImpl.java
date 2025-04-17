package cwchoiit.board.service.impl;

import cwchoiit.board.exception.DuplicateIdException;
import cwchoiit.board.exception.NotFoundUserException;
import cwchoiit.board.exception.PayloadValidationException;
import cwchoiit.board.mapper.UserMapper;
import cwchoiit.board.model.User;
import cwchoiit.board.service.UserService;
import cwchoiit.board.service.request.LoginUserRequest;
import cwchoiit.board.service.request.RegisterUserRequest;
import cwchoiit.board.service.request.UpdatePasswordRequest;
import cwchoiit.board.service.response.LoginUserResponse;
import cwchoiit.board.service.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(RegisterUserRequest request) {
        validationRegister(request);

        int insertCount = userMapper.insert(
                User.create(
                        request.getUserId(),
                        request.getNickname(),
                        request.getPassword(),
                        request.isAdmin(),
                        request.isWithdraw()
                )
        );

        postValidationRegister(request, insertCount);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        return userMapper.findByUserIdAndPassword(User.withUserIdAndPassword(request.getUserId(), request.getPassword()))
                .map(LoginUserResponse::of)
                .orElseThrow(() -> new NotFoundUserException(
                        "Could not find user with userId: " + request.getUserId() +
                                " and password: " + request.getPassword())
                );
    }

    @Override
    public UserInfoResponse getUserInfo(String id) {
        return userMapper.read(id)
                .map(UserInfoResponse::of)
                .orElseThrow(() -> new NotFoundUserException("Could not find user with id: " + id));
    }

    @Override
    @Transactional
    public void updatePassword(String id, UpdatePasswordRequest request) {
        User user = userMapper.findByIdAndPassword(User.withIdAndPassword(Integer.valueOf(id), request.getOldPassword()))
                .orElseThrow(() -> new NotFoundUserException(
                        "Could not find user with id: " + id +
                                " and password: " + request.getOldPassword())
                );

        userMapper.updatePassword(user.updateWithPassword(request.getNewPassword()));
    }

    @Override
    @Transactional
    public void deleteUser(String id, String password) {
        userMapper.findByIdAndPassword(User.withIdAndPassword(Integer.valueOf(id), password))
                .orElseThrow(() -> new NotFoundUserException(
                        "Could not find user with id: " + id +
                                " and password: " + password)
                );

        userMapper.delete(id);
    }

    private boolean isDuplicatedId(String userId) {
        return userMapper.idCheck(userId) == 1;
    }

    private void validationRegister(RegisterUserRequest request) {
        if (request.getUserId() == null || request.getPassword() == null || request.getNickname() == null) {
            throw new PayloadValidationException("Invalid request: " + request);
        }
        if (isDuplicatedId(request.getUserId())) {
            throw new DuplicateIdException("User with id " + request.getUserId() + " already exists");
        }
    }

    private void postValidationRegister(RegisterUserRequest request, int insertCount) {
        if (insertCount != 1) {
            log.error("[register:33] Register error: {} ", request);
            throw new PayloadValidationException("Register error with payload: " + request);
        }
    }
}
