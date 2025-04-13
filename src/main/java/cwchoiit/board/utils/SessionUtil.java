package cwchoiit.board.utils;

import cwchoiit.board.aop.annotation.LoginCheck;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.*;

public abstract class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private SessionUtil() {}

    public static String getLoginMemberId(HttpSession session) {
        Object id = session.getAttribute(LOGIN_MEMBER_ID);
        return id == null ? null : id.toString();
    }

    public static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }

    public static String getLoginAdminId(HttpSession session) {
        Object id = session.getAttribute(LOGIN_ADMIN_ID);
        return id == null ? null : id.toString();
    }

    public static void setLoginAdminId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }

    public static Optional<String> getIdFromSession(LoginCheck loginCheck, HttpSession session) {
        if (loginCheck.type() == ADMIN) {
            return Optional.ofNullable(SessionUtil.getLoginAdminId(session));
        }
        if (loginCheck.type() == USER) {
            return Optional.ofNullable(SessionUtil.getLoginMemberId(session));
        }
        if (loginCheck.type() == LOGGED_IN) {
            return Optional.ofNullable(SessionUtil.getLoginAdminId(session))
                    .or(() -> Optional.ofNullable(SessionUtil.getLoginMemberId(session)));
        }
        return Optional.empty();
    }
}
