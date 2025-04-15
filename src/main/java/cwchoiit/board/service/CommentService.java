package cwchoiit.board.service;

import cwchoiit.board.service.request.CreateCommentRequest;
import cwchoiit.board.service.request.UpdateCommentRequest;
import cwchoiit.board.service.response.CommentReadResponse;

public interface CommentService {
    CommentReadResponse read(Integer postId, Integer commentId);

    Integer create(String authorId, Integer postId, CreateCommentRequest request);

    void appendSubComment(String authorId, Integer superCommentId, Integer postId, CreateCommentRequest request);

    void delete(String authorId, Integer postId, Integer commentId);

    void update(String authorId, Integer postId, Integer commentId, UpdateCommentRequest request);
}
