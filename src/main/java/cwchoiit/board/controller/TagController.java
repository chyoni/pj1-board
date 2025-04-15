package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.TagService;
import cwchoiit.board.service.request.CreateTagRequest;
import cwchoiit.board.service.request.UpdateTagRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.LOGGED_IN;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody CreateTagRequest request) {
        tagService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok());
    }

    @PatchMapping("/{tagId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable("tagId") Integer tagId,
                                                    @RequestBody UpdateTagRequest request) {
        tagService.update(tagId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }

    @DeleteMapping("/{tagId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("tagId") Integer tagId) {
        tagService.delete(tagId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }
}
