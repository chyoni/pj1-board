package cwchoiit.board.aop.aspect;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.exception.LoginCheckException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.ADMIN;
import static cwchoiit.board.aop.annotation.LoginCheck.UserType.USER;
import static cwchoiit.board.utils.SessionUtil.getIdFromSession;

@Slf4j
@Aspect
@Component
public class LoginCheckAspect {

    /**
     * {@link LoginCheck} 애노테이션이 붙은 컨트롤러 메서드에서, 현재 로그인한 유저 정보(ID)를 세션에서 가져와 인증/인가 처리를 한다. <br>
     * {@link LoginCheck} 애노테이션의 {@code type} 프로퍼티의 값이 {@code ADMIN}으로 설정된 진입점인 경우, ADMIN 유저만 접근 가능하다. <br>
     * {@link LoginCheck} 애노테이션의 {@code type} 프로퍼티의 값이 {@code USER}로 설정된 진입점인 경우, 일반 유저여야 한다. <br>
     * {@link LoginCheck} 애노테이션의 {@code type} 프로퍼티의 값이 {@code LOGGED_IN}로 설정된 진입점인 경우, 로그인한 유저여야 한다. <br>
     *
     * @param joinPoint  joinPoint
     * @param loginCheck {@link LoginCheck}
     * @throws Throwable 로그인 하지 않았거나, 인가 처리에 실패했거나, 알 수 없는 예외 발생 시
     */
    @Around("@annotation(cwchoiit.board.aop.annotation.LoginCheck) && @annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getSession();

        getIdFromSession(loginCheck, session)
                .orElseThrow(() -> {
                    String packageAndClassName = joinPoint.getSignature().getDeclaringTypeName();
                    String methodName = joinPoint.getSignature().getName();

                    if (loginCheck.type() == ADMIN) {
                        log.error("[adminLoginCheck] Unauthorized access this entry point: {}.{}. You should logged in 'ADMIN' user.",
                                packageAndClassName, methodName);
                        return new LoginCheckException(
                                "Forbidden access this entry point: " + packageAndClassName + "." + methodName +
                                        ". You should logged in 'ADMIN' user.");
                    } else if (loginCheck.type() == USER) {
                        log.error("[adminLoginCheck] Unauthorized access this entry point: {}.{}. You should logged in 'USER' user.",
                                packageAndClassName, methodName);
                        return new LoginCheckException(
                                "Forbidden access this entry point: " + packageAndClassName + "." + methodName +
                                        ". You should logged in 'USER' user.");
                    } else {
                        log.error("[adminLoginCheck] Unauthorized access this entry point: {}.{}. You should logged in.",
                                packageAndClassName, methodName);
                        return new LoginCheckException(
                                "Unauthorized access this entry point: " + packageAndClassName + "." + methodName +
                                        ". You should logged in.");
                    }
                });

        return joinPoint.proceed();
    }
}
