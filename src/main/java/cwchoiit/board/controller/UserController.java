package cwchoiit.board.controller;

import cwchoiit.board.auth.annotation.LoginUserId;
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

import static cwchoiit.board.utils.SessionUtil.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request,
                                                   HttpSession session) {
        LoginUserResponse loginUser = userService.login(request);

        if (loginUser.isAdmin()) {
            setLoginAdminId(session, String.valueOf(loginUser.getId()));
        } else {
            setLoginMemberId(session, String.valueOf(loginUser.getId()));
        }

        return ResponseEntity.ok(loginUser);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> info(@LoginUserId String id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        clear(session);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@LoginUserId String id,
                                               @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginUserId String id,
                                       @RequestBody DeleteUserRequest request) {
        userService.deleteUser(id, request.getPassword());
        return ResponseEntity.ok().build();
    }
}
