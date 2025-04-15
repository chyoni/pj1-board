package cwchoiit.board.service.impl;

import cwchoiit.board.exception.NotFoundCommentException;
import cwchoiit.board.exception.PayloadValidationException;
import cwchoiit.board.mapper.CommentMapper;
import cwchoiit.board.model.Comment;
import cwchoiit.board.service.CommentService;
import cwchoiit.board.service.request.CreateCommentRequest;
import cwchoiit.board.service.request.UpdateCommentRequest;
import cwchoiit.board.service.response.CommentReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public CommentReadResponse read(Integer postId, Integer commentId) {
        return commentMapper.read(postId, commentId)
                .map(CommentReadResponse::from)
                .orElseThrow(() -> new NotFoundCommentException("Comment not found with id " + commentId));
    }

    @Override
    @Transactional
    public Integer create(String authorId, Integer postId, CreateCommentRequest request) {
        Comment newComment = Comment.create(
                postId,
                Integer.valueOf(authorId),
                request.getContents()
        );
        commentMapper.create(newComment);

        return newComment.getId();
    }

    @Override
    @Transactional
    public void appendSubComment(String authorId,
                                 Integer superCommentId,
                                 Integer postId,
                                 CreateCommentRequest request) {
        Integer subCommentId = create(authorId, postId, request);

        Comment superComment = commentMapper.read(postId, superCommentId)
                .orElseThrow(() -> new NotFoundCommentException("Comment not found with id " + superCommentId));

        commentMapper.appendSubComment(superComment.withSubComment(subCommentId));
    }

    @Override
    @Transactional
    public void delete(String authorId, Integer postId, Integer commentId) {
        CommentReadResponse comment = read(postId, commentId);
        if (!comment.getUserId().equals(Integer.valueOf(authorId))) {
            throw new PayloadValidationException("Permission denied for deletion of comment with id " + commentId + ", author: " + authorId);
        }
        commentMapper.delete(commentId);

        if (comment.getSubCommentId() != null) {
            commentMapper.delete(comment.getSubCommentId());
        }
    }

    @Override
    @Transactional
    public void update(String authorId, Integer postId, Integer commentId, UpdateCommentRequest request) {
        Comment comment = commentMapper.read(postId, commentId)
                .orElseThrow(() -> new NotFoundCommentException("Comment not found with id " + commentId));

        if (!comment.getUserId().equals(Integer.valueOf(authorId))) {
            throw new PayloadValidationException("Permission denied for update of comment with id: " + commentId + ", author: " + authorId);
        }

        commentMapper.update(
                comment.updateWithContents(request.getContents())
        );
    }
}
