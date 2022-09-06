package life.majiang.community.controller;

import com.alibaba.fastjson2.JSONObject;
import life.majiang.community.Provider.GitHubProvider;
import life.majiang.community.Provider.GitHubUser;
import life.majiang.community.dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CallBackController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redictUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redictUrl);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);

        String string = gitHubProvider.getGitHubUser(accessToken);

        JSONObject jsonObject = JSONObject.parseObject(string);
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setId(Long.valueOf(jsonObject.get("id").toString()));
        gitHubUser.setNickName((String)jsonObject.get("login"));
        gitHubUser.setEmail((String)jsonObject.get("email"));
        gitHubUser.setAvatar((String)jsonObject.get("avatar_url"));
        gitHubUser.setBio((String)jsonObject.get("bio"));
        System.out.println(gitHubUser.toString());

        return "index";
    }
}
