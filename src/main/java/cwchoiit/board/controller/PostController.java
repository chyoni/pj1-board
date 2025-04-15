package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.auth.annotation.LoginUserId;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.CommentService;
import cwchoiit.board.service.PostSearchService;
import cwchoiit.board.service.PostService;
import cwchoiit.board.service.request.*;
import cwchoiit.board.service.response.PostReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.LOGGED_IN;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearchService;
    private final CommentService commentService;

    @PostMapping
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> register(@LoginUserId String userId,
                                                      @RequestBody RegisterPostRequest request) {
        postService.register(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostReadResponse>> read(@PathVariable("postId") Integer postId) {
        return ResponseEntity.ok(ApiResponse.ok(postService.read(postId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostReadResponse>>> readAllByTag(@RequestParam("tagName") String tagName) {
        return ResponseEntity.ok(ApiResponse.ok(postSearchService.readAllByTag(tagName)));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<PostReadResponse>>> readAll(@RequestBody PostSearchRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(postSearchService.readAll(request)));
    }

    @GetMapping("/my")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<List<PostReadResponse>>> readAllMy(@LoginUserId String userId) {
        return ResponseEntity.ok(ApiResponse.ok(postService.readAllMy(userId)));
    }

    @PutMapping("/{postId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> update(@LoginUserId String userId,
                                                    @PathVariable("postId") Integer postId,
                                                    @RequestBody UpdatePostRequest request) {
        postService.update(userId, postId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }

    @DeleteMapping("/{postId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> delete(@LoginUserId String userId,
                                                    @PathVariable("postId") Integer postId) {
        postService.delete(userId, postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }

    @PostMapping("/{postId}/comments")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> addComment(@LoginUserId String userId,
                                                        @PathVariable("postId") Integer postId,
                                                        @RequestBody CreateCommentRequest request) {
        commentService.create(userId, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok());
    }

    @PostMapping("/{postId}/comments/{superCommentId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> addSubComment(@LoginUserId String userId,
                                                           @PathVariable("postId") Integer postId,
                                                           @PathVariable("superCommentId") Integer superCommentId,
                                                           @RequestBody CreateCommentRequest request) {
        commentService.appendSubComment(userId, superCommentId, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok());
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> updateComment(@LoginUserId String userId,
                                                           @PathVariable("postId") Integer postId,
                                                           @PathVariable("commentId") Integer commentId,
                                                           @RequestBody UpdateCommentRequest request) {
        commentService.update(userId, postId, commentId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<Void>> deleteComment(@LoginUserId String userId,
                                                           @PathVariable("postId") Integer postId,
                                                           @PathVariable("commentId") Integer commentId) {
        commentService.delete(userId, postId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }
}
