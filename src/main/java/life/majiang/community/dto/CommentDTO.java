package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Integer commentator;
    private Long likeCount;
    private Date gmtCreated;
    private Date gmtModified;
    private User user;
}
