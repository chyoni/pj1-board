package cwchoiit.board.aop;

import cwchoiit.board.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static cwchoiit.board.aop.LoginCheck.UserType.ADMIN;
import static cwchoiit.board.aop.LoginCheck.UserType.USER;

@Slf4j
@Aspect
@Component
public class LoginCheckAspect {

    /**
     * {@link LoginCheck} 애노테이션이 붙은 컨트롤러 메서드에서, 현재 로그인한 유저 정보(ID)를 세션에서 가져와 해당 컨트롤러 메서드의 0번째 파라미터에 추가한다.
     * @param joinPoint joinPoint
     * @param loginCheck {@link LoginCheck}
     *
     * @throws Throwable 로그인 하지 않았거나, 알 수 없는 예외 발생 시
     */
    @Around("@annotation(cwchoiit.board.aop.LoginCheck) && @annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getSession();

        String id = getIdFromSession(loginCheck, session)
                .orElseThrow(() -> {
                    String packageAndClassName = joinPoint.getSignature().getDeclaringTypeName();
                    String methodName = joinPoint.getSignature().getName();
                    log.error("[adminLoginCheck:32] Unauthorized access this entry point: {}.{}", packageAndClassName, methodName);
                    return new RuntimeException("LoginCheck error this entry point: " + packageAndClassName + "." + methodName);
                });

        // joinPoint Args 정보를 가져온다. (LoginCheck 애노테이션이 달린 메서드의 Args 정보)
        Object[] args = joinPoint.getArgs();
        args[0] = id; // 0번째에 현재 로그인한 유저 정보를 삽입
        return joinPoint.proceed(args);
    }

    private Optional<String> getIdFromSession(LoginCheck loginCheck, HttpSession session) {
        if (loginCheck.type() == ADMIN) {
            return Optional.ofNullable(SessionUtil.getLoginAdminId(session));
        }
        if (loginCheck.type() == USER) {
            return Optional.ofNullable(SessionUtil.getLoginMemberId(session));
        }
        return Optional.empty();
    }
}
