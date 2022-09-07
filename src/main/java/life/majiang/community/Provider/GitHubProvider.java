package life.majiang.community.Provider;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import life.majiang.community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String accessToken = string.split("&")[0].split("=")[1];
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getGitHubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization","token "+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            JSONObject jsonObject = JSONObject.parseObject(string);
            GitHubUser gitHubUser = new GitHubUser();
            gitHubUser.setId(Long.valueOf(jsonObject.get("id").toString()));
            gitHubUser.setNickName((String)jsonObject.get("login"));
            gitHubUser.setEmail((String)jsonObject.get("email"));
            gitHubUser.setAvatar((String)jsonObject.get("avatar_url"));
            gitHubUser.setBio((String)jsonObject.get("bio"));
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
