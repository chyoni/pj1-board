package cwchoiit.board.controller;

import cwchoiit.board.aop.annotation.LoginCheck;
import cwchoiit.board.controller.response.ApiResponse;
import cwchoiit.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static cwchoiit.board.aop.annotation.LoginCheck.UserType.ADMIN;
import static cwchoiit.board.aop.annotation.LoginCheck.UserType.LOGGED_IN;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @LoginCheck(type = LOGGED_IN)
    public ResponseEntity<ApiResponse<String>> upload(@RequestPart("file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(fileService.upload(file).getPath()));
    }
}
