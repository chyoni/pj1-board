package cwchoiit.board.service;

import cwchoiit.board.service.request.RegisterCategoryRequest;
import cwchoiit.board.service.request.UpdateCategoryRequest;

public interface CategoryService {

    void register(RegisterCategoryRequest request);

    void update(Integer categoryId, UpdateCategoryRequest request);

    void delete(Integer categoryId);
}
