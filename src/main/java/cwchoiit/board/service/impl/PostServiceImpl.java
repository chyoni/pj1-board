package cwchoiit.board.service.impl;

import cwchoiit.board.exception.NotFoundPostException;
import cwchoiit.board.exception.PayloadValidationException;
import cwchoiit.board.mapper.PostMapper;
import cwchoiit.board.mapper.TagMapper;
import cwchoiit.board.model.Post;
import cwchoiit.board.service.PostService;
import cwchoiit.board.service.UserService;
import cwchoiit.board.service.request.RegisterPostRequest;
import cwchoiit.board.service.request.UpdatePostRequest;
import cwchoiit.board.service.response.PostReadResponse;
import cwchoiit.board.service.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final TagMapper tagMapper;
    private final UserService userService;

    @Override
    @Transactional
    public void register(String userId, RegisterPostRequest request) {
        UserInfoResponse user = userService.getUserInfo(userId);

        synchronized (this) {
            Post newPost = Post.create(
                    request.getName(),
                    request.getContents(),
                    request.getCategoryId(),
                    user.getId(),
                    request.getFileId(),
                    user.isAdmin()
            );

            // TODO: 파일이 있는 경우엔, 파일 생성 후 포스트 생성
            int insertCount = postMapper.insert(newPost);
            postValidationRegister(userId, request, insertCount);

            if (request.getTags() != null) {
                request.getTags().forEach(tag -> {
                    tagMapper.create(tag);
                    tagMapper.mapPostTag(tag.getId(), newPost.getId());
                });
            }
        }
    }

    @Override
    public PostReadResponse read(Integer postId) {
        return postMapper.read(postId)
                .map(PostReadResponse::from)
                .orElseThrow(() -> new NotFoundPostException("Post not found with id: " + postId));
    }

    @Override
    public List<PostReadResponse> readAllMy(String userId) {
        return postMapper.readAllMy(userId).stream()
                .map(PostReadResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void update(String userId, Integer postId, UpdatePostRequest request) {
        Post post = postMapper.read(postId)
                .orElseThrow(() -> new NotFoundPostException("Post not found with id: " + postId));

        if (!post.getUserId().equals(Integer.valueOf(userId))) {
            throw new PayloadValidationException("Permission denied for user id: " + userId + " update post id: " + postId);
        }

        postMapper.update(
                post.updateWith(
                        request.getName(),
                        request.getContents(),
                        request.getCategoryId(),
                        request.getFileId(),
                        request.getViews()
                )
        );
    }

    @Override
    @Transactional
    public void delete(String userId, Integer postId) {
        postMapper.read(postId)
                .ifPresentOrElse(
                        (post) -> {
                            if (!post.getUserId().equals(Integer.valueOf(userId))) {
                                throw new PayloadValidationException(
                                        "Permission denied for user id: " + userId +
                                                " delete post id: " + postId);
                            }
                            postMapper.delete(post.getId());
                        },
                        () -> {
                            throw new NotFoundPostException("Post not found with id: " + postId);
                        }
                );
    }

    private void postValidationRegister(String userId, RegisterPostRequest request, int insertCount) {
        if (insertCount != 1) {
            log.error("[postValidationRegister] Post register failed. user id : {}, request : {}", userId, request);
            throw new PayloadValidationException("Post register failed. user id : " + userId + ", request : " + request);
        }
    }
}
