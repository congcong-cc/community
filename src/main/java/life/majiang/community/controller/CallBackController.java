package life.majiang.community.controller;

import com.alibaba.fastjson2.JSONObject;
import life.majiang.community.Provider.GitHubProvider;
import life.majiang.community.Provider.GitHubUser;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, HttpServletRequest request, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redictUrl);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);

        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if(gitHubUser!=null){
            User user = new User();
            user.setName(gitHubUser.getNickName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setAvatarUrl(gitHubUser.getAvatar());
            user.setGmtCreated(Long.valueOf(System.currentTimeMillis()));
            user.setGmtModified(Long.valueOf(System.currentTimeMillis()));
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            return "redirect:/";

        }
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        Cookie newCookie=new Cookie("token",null);
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);
        request.getSession().setAttribute("user",null);
        return "redirect:/";
    }
}
