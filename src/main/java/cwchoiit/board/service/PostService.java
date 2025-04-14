package cwchoiit.board.service;

import cwchoiit.board.service.request.RegisterPostRequest;
import cwchoiit.board.service.request.UpdatePostRequest;
import cwchoiit.board.service.response.PostReadResponse;

import java.util.List;

public interface PostService {
    void register(String userId, RegisterPostRequest request);

    PostReadResponse read(Integer postId);

    List<PostReadResponse> readAllMy(String userId);

    void update(String userId, Integer postId, UpdatePostRequest request);

    void delete(String userId, Integer postId);
}
