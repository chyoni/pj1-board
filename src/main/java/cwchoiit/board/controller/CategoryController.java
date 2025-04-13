package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.CategoryService;
import cwchoiit.board.service.request.RegisterCategoryRequest;
import cwchoiit.board.service.request.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.ADMIN;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @LoginCheck(type = ADMIN)
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterCategoryRequest request) {
        categoryService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok());
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = ADMIN)
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable("categoryId") Integer categoryId,
                                                    @RequestBody UpdateCategoryRequest request) {
        categoryService.update(categoryId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = ADMIN)
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("categoryId") Integer categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok());
    }
}
