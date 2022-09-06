package life.majiang.community.Provider;

import lombok.Data;

@Data
public class GitHubUser {
    private long id;
    private String nickName;
    private String avatar;
    private String email;
    private  String bio;
}
