package cwchoiit.board.service.impl;

import cwchoiit.board.mapper.PostSearchMapper;
import cwchoiit.board.service.PostSearchService;
import cwchoiit.board.service.request.PostSearchRequest;
import cwchoiit.board.service.response.PostReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    @Override
    @Cacheable(
            key = "'readAll:' + (#request.name ?: '') + ':' + (#request.categoryId ?: '')",
            value = "readAll"
    )
    public List<PostReadResponse> readAll(PostSearchRequest request) {
        log.debug("[readAll] Cache key = readAll:{}:{}", request.getName(), request.getCategoryId());
        return postSearchMapper.readAll(request).stream()
                .map(PostReadResponse::from)
                .toList();
    }
}
