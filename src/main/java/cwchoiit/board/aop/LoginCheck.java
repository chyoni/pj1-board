package cwchoiit.board.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static cwchoiit.board.aop.LoginCheck.UserType.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
    UserType type() default USER;

    enum UserType {
        USER, ADMIN
    }
}
