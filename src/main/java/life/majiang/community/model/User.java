package life.majiang.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private String avatarUrl;
    private Long gmtCreated;
    private Long gmtModified;

}
