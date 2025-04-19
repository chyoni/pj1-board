package cwchoiit.board.service;

import cwchoiit.board.model.File;
import cwchoiit.board.service.response.FileReadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    File upload(MultipartFile file);

    void mapToPost(Integer fileId, Integer postId);

    List<FileReadResponse> readAllByPost(Integer postId);
}
