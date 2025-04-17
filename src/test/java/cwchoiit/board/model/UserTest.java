package cwchoiit.board.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Model - User")
class UserTest {

    @Test
    @DisplayName("유저 생성 - 팩토리 메서드")
    void create() {
        User newUser = User.create(
                "User",
                "NickName",
                "123",
                true,
                false
        );

        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isNull();
        assertThat(newUser.getUserId()).isEqualTo("User");
        assertThat(newUser.getNickname()).isEqualTo("NickName");
        assertThat(newUser.getPassword()).isNotEqualTo("123"); // 암호화된 상태여야 한다.
        assertThat(newUser.isAdmin()).isTrue();
        assertThat(newUser.isWithdraw()).isFalse();
    }

    @Test
    @DisplayName("유저 생성 - UserId with Password")
    void create_withUserIdAndPassword() {
        User user = User.withUserIdAndPassword("User", "123");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getUserId()).isEqualTo("User");
        assertThat(user.getPassword()).isNotEqualTo("123"); // 암호화된 상태여야 한다.
        assertThat(user.getNickname()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.isWithdraw()).isFalse();
    }

    @Test
    @DisplayName("유저 생성 - ID with Password")
    void create_withIdAndPassword() {
        User user = User.withIdAndPassword(1, "123");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getPassword()).isNotEqualTo("123"); // 암호화된 상태여야 한다.
        assertThat(user.getUserId()).isNull();
        assertThat(user.getNickname()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.isWithdraw()).isFalse();
    }

    @Test
    @DisplayName("유저 패스워드 업데이트")
    void updateWithPassword() {
        User newUser = User.withUserIdAndPassword("User", "123");

        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isNull();
        assertThat(newUser.getUserId()).isEqualTo("User");
        assertThat(newUser.getPassword()).isNotEqualTo("123");
        assertThat(newUser.isAdmin()).isFalse();
        assertThat(newUser.isWithdraw()).isFalse();

        String oldPassword = newUser.getPassword();

        User updateWithPasswordUser = newUser.updateWithPassword("9999");
        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isNull();
        assertThat(newUser.getUserId()).isEqualTo("User");
        assertThat(newUser.getPassword()).isNotEqualTo("9999");
        assertThat(newUser.isAdmin()).isFalse();
        assertThat(newUser.isWithdraw()).isFalse();

        assertThat(oldPassword).isNotEqualTo(updateWithPasswordUser.getPassword());
    }

    @Test
    @DisplayName("유저 패스워드 암호화")
    void encryptPassword() {
        String rawPassword = "123";
        User withUserIdAndPasswordUser = User.withUserIdAndPassword("User", rawPassword);

        assertThat(withUserIdAndPasswordUser).isNotNull();
        assertThat(withUserIdAndPasswordUser.getPassword()).isNotEqualTo(rawPassword);
        assertThat(withUserIdAndPasswordUser.getPassword().length()).isEqualTo(64); // SHA-256, 64자리

        User withIdAndPasswordUser = User.withIdAndPassword(1, rawPassword);

        assertThat(withIdAndPasswordUser).isNotNull();
        assertThat(withIdAndPasswordUser.getPassword()).isNotEqualTo(rawPassword);
        assertThat(withIdAndPasswordUser.getPassword().length()).isEqualTo(64); // SHA-256, 64자리

        User newUser = User.create(
                "User",
                "NickName",
                rawPassword,
                true,
                false
        );

        assertThat(newUser).isNotNull();
        assertThat(newUser.getPassword()).isNotEqualTo(rawPassword);
        assertThat(newUser.getPassword().length()).isEqualTo(64); // SHA-256, 64자리

        String oldPassword = newUser.getPassword();

        User updateWithPasswordUser = newUser.updateWithPassword("9999");

        assertThat(oldPassword).isNotEqualTo(updateWithPasswordUser.getPassword());
        assertThat(updateWithPasswordUser).isNotNull();
        assertThat(updateWithPasswordUser.getPassword()).isNotEqualTo(rawPassword);
        assertThat(updateWithPasswordUser.getPassword().length()).isEqualTo(64); // SHA-256, 64자리
    }
}