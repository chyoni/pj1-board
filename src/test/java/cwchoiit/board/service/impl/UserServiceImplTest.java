package cwchoiit.board.service.impl;

import cwchoiit.board.SpringBootTestWithTestContainer;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Service - UserService")
class UserServiceImplTest extends SpringBootTestWithTestContainer {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        "T_123",
                        "T_Nickname",
                        false,
                        false
                )
        );

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo("T_User");
        assertThat(findUser.getNickname()).isEqualTo("T_Nickname");
        assertThat(findUser.getPassword()).isNotEqualTo("T_123"); // Password 암호화 상태여야 한다.
    }

    @Test
    @DisplayName("회원가입 실패 - 필수필드 값 null")
    void register_fail_required_fields_null() {
        assertThatThrownBy(() -> userService.register(
                RegisterUserRequest.of(
                        null,
                        "T_123",
                        "T_Nickname",
                        false,
                        false
                )
        )).isInstanceOf(PayloadValidationException.class);

        assertThatThrownBy(() -> userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        null,
                        "T_Nickname",
                        false,
                        false
                )
        )).isInstanceOf(PayloadValidationException.class);

        assertThatThrownBy(() -> userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        "T_123",
                        null,
                        false,
                        false
                )
        )).isInstanceOf(PayloadValidationException.class);
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 아이디")
    void register_fail_duplicated_userId() {
        userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        "T_123",
                        "T_Nickname",
                        false,
                        false
                )
        );

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        assertThat(findUser.getUserId()).isEqualTo("T_User");
        assertThat(findUser.getNickname()).isEqualTo("T_Nickname");
        assertThat(findUser.getPassword()).isNotEqualTo("T_123"); // Password 암호화 상태여야 한다.

        assertThatThrownBy(() -> userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        "T_123",
                        "T_Nickname",
                        false,
                        false
                )
        )).isInstanceOf(DuplicateIdException.class)
                .hasMessageContaining("User with id " + "T_User" + " already exists");
    }

    @Test
    @DisplayName("회원가입 실패 - 포스트 밸리데이터")
    void register_fail_post_validation() {
        userService.register(
                RegisterUserRequest.of(
                        "T_User",
                        "T_123",
                        "T_Nickname",
                        false,
                        false
                )
        );
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        register_success();

        LoginUserResponse loggedInUser = userService.login(LoginUserRequest.of("T_User", "T_123"));

        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUserId()).isEqualTo("T_User");
        assertThat(loggedInUser.getNickname()).isEqualTo("T_Nickname");
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 아이디")
    void login_fail_invalid_id() {
        register_success();

        assertThatThrownBy(() -> userService.login(LoginUserRequest.of("INVALID", "T_123")))
                .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 패스워드")
    void login_fail_invalid_password() {
        register_success();

        assertThatThrownBy(() -> userService.login(LoginUserRequest.of("T_User", "INVALID")))
                .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("유저 정보 조회 - 성공")
    void get_user_info_success() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        UserInfoResponse userInfo = userService.getUserInfo(String.valueOf(findUser.getId()));

        assertThat(userInfo).isNotNull();
        assertThat(userInfo.getUserId()).isEqualTo("T_User");
        assertThat(userInfo.getNickname()).isEqualTo("T_Nickname");
        assertThat(userInfo.getPassword()).isNotEqualTo("T_123");
    }

    @Test
    @DisplayName("유저 정보 조회 - 실패")
    void get_user_info_fail() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        String invalidId = "INVALID";
        assertThatThrownBy(() -> userService.getUserInfo(invalidId))
                .isInstanceOf(NotFoundUserException.class);

        assertThat(findUser.getUserId()).isNotEqualTo(invalidId);
    }

    @Test
    @DisplayName("패스워드 업데이트 - 성공")
    void update_password_success() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        userService.updatePassword(
                String.valueOf(findUser.getId()),
                UpdatePasswordRequest.of("T_123", "T_456")
        );

        assertThatThrownBy(() -> userService.login(LoginUserRequest.of("T_User", "T_123")))
                .isInstanceOf(NotFoundUserException.class);

        LoginUserResponse loggedInUser = userService.login(LoginUserRequest.of("T_User", "T_456"));

        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUserId()).isEqualTo("T_User");
        assertThat(loggedInUser.getNickname()).isEqualTo("T_Nickname");
    }

    @Test
    @DisplayName("패스워드 업데이트 - 실패 - 잘못된 아이디")
    void update_password_fail_invalid_id() {
        register_success();

        userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        String invalidId = "123";

        assertThatThrownBy(() -> userService.updatePassword(
                        invalidId,
                        UpdatePasswordRequest.of("T_123", "T_456")
                )
        ).isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("패스워드 업데이트 - 실패 - 잘못된 기존 패스워드")
    void update_password_fail_invalid_old_password() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        String invalidOldPassword = "INVALID";

        assertThatThrownBy(() -> userService.updatePassword(
                        String.valueOf(findUser.getId()),
                        UpdatePasswordRequest.of(invalidOldPassword, "T_456")
                )
        ).isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("유저 삭제 - 성공")
    void delete_user_success() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        userService.deleteUser(String.valueOf(findUser.getId()), "T_123");

        assertThatThrownBy(() -> userService.login(LoginUserRequest.of("T_User", "T_123")))
                .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("유저 삭제 - 실패 - 잘못된 아이디")
    void delete_user_fail_invalid_id() {
        register_success();

        userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        String invalidId = "123";

        assertThatThrownBy(() -> userService.deleteUser(invalidId, "T_123"))
                .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("유저 삭제 - 실패 - 잘못된 패스워드")
    void delete_user_fail_invalid_password() {
        register_success();

        User findUser = userMapper.findByUserIdAndPassword(
                User.withUserIdAndPassword("T_User", "T_123")
        ).orElseThrow(() -> new RuntimeException("Could not find user with id: " + "T_User"));

        String invalidPassword = "INVALID";

        assertThatThrownBy(() -> userService.deleteUser(String.valueOf(findUser.getId()), invalidPassword))
                .isInstanceOf(NotFoundUserException.class);
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    @DisplayName("회원가입 실패 - DB Insert 시 affectedRow = 0 케이스")
    class RegisterFailWithMocking {
        @Mock
        private UserMapper userMapper;

        @InjectMocks
        private UserServiceImpl userService;

        @Test
        @DisplayName("insertCount 가 1이 아닐 경우 예외 발생")
        void register_fail_insert_count_is_not_1() {
            RegisterUserRequest request = RegisterUserRequest.of(
                    "T_User",
                    "T_123",
                    "T_Nickname",
                    false,
                    false
            );

            when(userMapper.insert(any(User.class))).thenReturn(0);

            assertThatThrownBy(() -> userService.register(request))
                    .as("insertCount 가 1이 아닌 경우, PayloadValidationException 이 발생해야 함")
                    .isInstanceOf(PayloadValidationException.class)
                    .hasMessageContaining("Register error with payload:");
        }
    }
}