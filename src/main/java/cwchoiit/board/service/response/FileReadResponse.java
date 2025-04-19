package cwchoiit.board.service.response;

import cwchoiit.board.model.File;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileReadResponse {
    private String path;
    private String name;
    private String extension;
    private Integer postId;

    public static FileReadResponse from(File file) {
        FileReadResponse fileReadResponse = new FileReadResponse();
        fileReadResponse.path = file.getPath();
        fileReadResponse.name = file.getName();
        fileReadResponse.extension = file.getExtension();
        fileReadResponse.postId = file.getPostId();
        return fileReadResponse;
    }
}
