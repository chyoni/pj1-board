package cwchoiit.board.service.request;

import cwchoiit.board.model.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterPostRequest {
    private String name;
    private String contents;
    private Integer categoryId;
    private Integer fileId;
    private List<Tag> tags;
    private boolean admin;
}
