package cwchoiit.board.service;

import cwchoiit.board.service.request.CreateTagRequest;
import cwchoiit.board.service.request.UpdateTagRequest;

public interface TagService {

    void create(CreateTagRequest request);

    void update(Integer tagId, UpdateTagRequest request);

    void delete(Integer tagId);
}
