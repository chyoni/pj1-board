package cwchoiit.board.service.impl;

import cwchoiit.board.exception.DuplicateIdException;
import cwchoiit.board.mapper.UserMapper;
import cwchoiit.board.service.UserService;
import cwchoiit.board.service.request.LoginUserRequest;
import cwchoiit.board.service.request.RegisterUserRequest;
import cwchoiit.board.service.request.UpdatePasswordRequest;
import cwchoiit.board.service.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cwchoiit.board.utils.SHA256Util.encryptSHA256;

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
                request.with(encryptSHA256(request.getPassword()))
        );

        if (insertCount != 1) {
            log.error("[register:33] Register error: {} ", request);
            throw new RuntimeException("Register error with payload: " + request);
        }
    }

    @Override
    public UserInfoResponse login(LoginUserRequest request) {
        return userMapper.findByUserIdAndPassword(
                request.getUserId(),
                encryptSHA256(request.getPassword())
        );
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        return userMapper.idCheck(userId) == 1;
    }

    @Override
    public UserInfoResponse getUserInfo(String id) {
        return userMapper.read(id);
    }

    @Override
    @Transactional
    public void updatePassword(String id, UpdatePasswordRequest request) {
        UserInfoResponse findUser = userMapper.findByIdAndPassword(
                id,
                encryptSHA256(request.getOldPassword())
        );

        if (findUser == null) {
            log.error("[updatePassword:58] User with id {} not found or password: {} error. ", id, request.getOldPassword());
            throw new RuntimeException("User with id " + id + " not found or password error: " + request.getOldPassword());
        }

        userMapper.updatePassword(
                id,
                UpdatePasswordRequest.of(
                        encryptSHA256(request.getOldPassword()),
                        encryptSHA256(request.getNewPassword())
                )
        );
    }

    @Override
    @Transactional
    public void deleteUser(String id, String password) {
        UserInfoResponse findUser = userMapper.findByIdAndPassword(id, encryptSHA256(password));

        if (findUser == null) {
            log.error("[deleteUser:79] User with id {} not found or password: {} error. ", id, password);
            throw new RuntimeException("User with id " + id + " not found or password error: " + password);
        }

        userMapper.delete(id);
    }

    private void validationRegister(RegisterUserRequest request) {
        if (request.getUserId() == null || request.getPassword() == null || request.getNickname() == null) {
            throw new RuntimeException("Invalid request: " + request);
        }
        if (isDuplicatedId(request.getUserId())) {
            throw new DuplicateIdException("User with id " + request.getUserId() + " already exists");
        }
    }
}
