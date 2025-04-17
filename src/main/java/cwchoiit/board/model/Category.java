package cwchoiit.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    private Integer id;
    private String name;
    private SortStatus sortStatus;
    private Integer searchCount;
    private Integer pagingStartOffset;

    public static Category create(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }

    public Category updateWithName(String name) {
        this.name = name;
        return this;
    }

    public enum SortStatus {
        CATEGORIES, NEWEST, OLDEST
    }
}
