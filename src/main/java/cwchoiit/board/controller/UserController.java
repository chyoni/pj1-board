package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.auth.annotation.LoginUserId;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.UserService;
import cwchoiit.board.service.request.DeleteUserRequest;
import cwchoiit.board.service.request.LoginUserRequest;
import cwchoiit.board.service.request.RegisterUserRequest;
import cwchoiit.board.service.request.UpdatePasswordRequest;
import cwchoiit.board.service.response.LoginUserResponse;
import cwchoiit.board.service.response.UserInfoResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.LOGGED_IN;
import static cwchoiit.board.utils.SessionUtil.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserResponse>> login(@RequestBody LoginUserRequest request,
                                                                HttpSession session) {
        LoginUserResponse loginUser = userService.login(request);

        if (loginUser.isAdmin()) {
            setLoginAdminId(session, String.valueOf(loginUser.getId()));
        } else {
            setLoginMemberId(session, String.valueOf(loginUser.getId()));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(loginUser));
    }

    @GetMapping("/info")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<UserInfoResponse>> info(@LoginUserId String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(userService.getUserInfo(id)));
    }

    @PutMapping("/logout")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        clear(session);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }

    @PatchMapping("/password")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> updatePassword(@LoginUserId String id,
                                                            @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }

    @DeleteMapping
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> delete(@LoginUserId String id,
                                                    @RequestBody DeleteUserRequest request) {
        userService.deleteUser(id, request.getPassword());
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}
