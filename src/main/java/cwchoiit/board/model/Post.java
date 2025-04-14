package cwchoiit.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {
    private Integer id;
    private String name;
    private String contents;
    private Integer categoryId;
    private Integer userId;
    private Integer fileId;
    private Integer views;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Post create(String name,
                              String contents,
                              Integer categoryId,
                              Integer userId,
                              Integer fileId,
                              boolean admin) {
        Post post = new Post();
        post.name = name;
        post.contents = contents;
        post.categoryId = categoryId;
        post.userId = userId;
        post.fileId = fileId;
        post.views = 0;
        post.admin = admin;
        post.createdAt = LocalDateTime.now();
        post.updatedAt = LocalDateTime.now();
        return post;
    }

    public Post withUpdate(String name, String contents, Integer categoryId, Integer fileId, Integer views) {
        this.name = this.name.equals(name) || name == null ?
                this.name :
                name;
        this.contents = this.contents.equals(contents) || contents == null ?
                this.contents :
                contents;
        this.categoryId = this.categoryId.equals(categoryId) || categoryId == null ?
                this.categoryId :
                categoryId;
        this.fileId = (this.fileId != null && this.fileId.equals(fileId) || fileId == null) ?
                this.fileId :
                fileId;
        this.views = this.views.equals(views) || views == null ?
                this.views :
                views;
        this.updatedAt = LocalDateTime.now();

        return this;
    }
}
