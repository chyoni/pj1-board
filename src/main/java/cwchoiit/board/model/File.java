package cwchoiit.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class File {
    private Integer id;
    private String path;
    private String name;
    private String extension;
    private Integer postId;

    public static File create(String path, String name, String extension) {
        File file = new File();
        file.path = path;
        file.name = name;
        file.extension = extension;
        file.postId = null;
        return file;
    }

    public File updateWithPostId(Integer postId) {
        this.postId = postId;
        return this;
    }
}
