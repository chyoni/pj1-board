package cwchoiit.board.service.impl;

import cwchoiit.board.exception.NotFoundTagException;
import cwchoiit.board.mapper.TagMapper;
import cwchoiit.board.model.Tag;
import cwchoiit.board.service.PostService;
import cwchoiit.board.service.TagService;
import cwchoiit.board.service.request.CreateTagRequest;
import cwchoiit.board.service.request.UpdateTagRequest;
import cwchoiit.board.service.response.PostReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final PostService postService;

    @Override
    public void create(CreateTagRequest request) {
        PostReadResponse post = postService.read(request.getPostId());

        Tag newTag = Tag.create(request.getName());

        tagMapper.create(newTag);
        tagMapper.mapPostTag(newTag.getId(), post.getId());
    }

    @Override
    public void update(Integer tagId, UpdateTagRequest request) {
        Tag tag = tagMapper.read(tagId)
                .orElseThrow(() -> new NotFoundTagException("Tag not found with id " + tagId));

        tagMapper.update(tag.updateWithName(request.getName()));
    }

    @Override
    public void delete(Integer tagId) {
        tagMapper.read(tagId)
                .ifPresentOrElse(
                        tag -> tagMapper.delete(tag.getId()),
                        () -> {
                            throw new NotFoundTagException("Tag not found with id " + tagId);
                        });
    }
}
