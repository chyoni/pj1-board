package cwchoiit.board.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.time.LocalDateTime;

@Slf4j
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Integer id;
    private String userId;
    private String nickname;
    private String password;
    private boolean admin;
    private boolean withdraw;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User create(String userId,
                              String nickname,
                              String password,
                              boolean admin,
                              boolean withdraw) {
        User user = new User();
        user.userId = userId;
        user.nickname = nickname;
        user.password = user.encryptPassword(password);
        user.admin = admin;
        user.withdraw = withdraw;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }

    public static User withUserIdAndPassword(String userId, String password) {
        User user = new User();
        user.userId = userId;
        user.password = user.encryptPassword(password);
        return user;
    }

    public static User withIdAndPassword(Integer id, String password) {
        User user = new User();
        user.id = id;
        user.password = user.encryptPassword(password);
        return user;
    }

    public User updateWithPassword(String password) {
        this.password = encryptPassword(password);
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    private String encryptPassword(String plainPassword) {
        final String ENCRYPTION_KEY = "SHA-256";
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCRYPTION_KEY);
            digest.update(plainPassword.getBytes());
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();
        } catch (Exception e) {
            log.error("[encryptSHA256:24] encryptSHA256 error", e);
            throw new RuntimeException("Encrypt password error: " + e);
        }
    }
}
