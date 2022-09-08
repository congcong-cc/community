package life.majiang.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private String creator;
    private Integer interest;
    private Integer commentCount;
    private String comment;
    private Integer viewCount;
    private Integer redHeart;
    private String gmtCreated;
    private String gmtModified;
}
