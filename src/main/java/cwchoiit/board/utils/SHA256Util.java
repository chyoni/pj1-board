package cwchoiit.board.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public abstract class SHA256Util {
    public static final String ENCRYPTION_KEY = "SHA-256";

    public static String encryptSHA256(String input) {
        String SHA = null;

        try {
            MessageDigest digest = MessageDigest.getInstance(ENCRYPTION_KEY);
            digest.update(input.getBytes());
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            SHA = hexString.toString();
        } catch (Exception e) {
            log.error("[encryptSHA256:24] encryptSHA256 error", e);
        }
        return SHA;
    }
}
