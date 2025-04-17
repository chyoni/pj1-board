package cwchoiit.board.service.impl;

import cwchoiit.board.IntegrationWithTestContainer;
import cwchoiit.board.exception.DuplicateIdException;
import cwchoiit.board.exception.PayloadValidationException;
import cwchoiit.board.mapper.UserMapper;
import cwchoiit.board.model.User;
import cwchoiit.board.service.UserService;
import cwchoiit.board.service.request.RegisterUserRequest;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest extends IntegrationWithTestContainer {

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

        assertNotNull(findUser);
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