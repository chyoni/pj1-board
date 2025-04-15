package cwchoiit.board.service;

import cwchoiit.board.service.request.PostSearchRequest;
import cwchoiit.board.service.response.PostReadResponse;

import java.util.List;

public interface PostSearchService {

    List<PostReadResponse> readAll(PostSearchRequest request);

    List<PostReadResponse> readAllByTag(String tagName);
}
