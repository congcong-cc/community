package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private String creator;
    private Long interest;
    private Long commentCount;
    private String comment;
    private Long viewCount;
    private Long redHeart;
    private String gmtCreated;
    private String gmtModified;
    private User user;
}
