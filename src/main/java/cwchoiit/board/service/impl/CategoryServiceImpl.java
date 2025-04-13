package cwchoiit.board.service.impl;

import cwchoiit.board.exception.NotFoundCategoryException;
import cwchoiit.board.exception.PayloadValidationException;
import cwchoiit.board.mapper.CategoryMapper;
import cwchoiit.board.model.Category;
import cwchoiit.board.service.CategoryService;
import cwchoiit.board.service.request.RegisterCategoryRequest;
import cwchoiit.board.service.request.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public void register(RegisterCategoryRequest request) {
        int insertCount = categoryMapper.register(Category.create(request.getName()));
        postValidationRegister(request, insertCount);
    }

    @Override
    public void update(Integer categoryId, UpdateCategoryRequest request) {
        categoryMapper.read(categoryId)
                .ifPresentOrElse(
                        (category) -> categoryMapper.update(category.withUpdateName(request.getName())),
                        () -> {
                            throw new NotFoundCategoryException("Category with id " + categoryId + " not found");
                        });
    }

    @Override
    public void delete(Integer categoryId) {
        categoryMapper.read(categoryId)
                .ifPresentOrElse(
                        (category) -> categoryMapper.delete(categoryId),
                        () -> {
                            throw new NotFoundCategoryException("Category with id " + categoryId + " not found");
                        });
    }

    private void postValidationRegister(RegisterCategoryRequest request, int insertCount) {
        if (insertCount != 1) {
            log.error("[postValidationRegister] Category registration failed. payload: {}", request);
            throw new PayloadValidationException("Category registration failed. payload: " + request);
        }
    }
}
