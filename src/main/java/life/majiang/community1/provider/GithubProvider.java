package life.majiang.community1.provider;

import life.majiang.community1.dto.AccessTokenDTO;
import life.majiang.community1.dto.GithubUser;
import okhttp3.*;
import com.alibaba.fastjson.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     *这里的getAccessTokenDTO模拟的是再次访问GitHub确认登陆时的过程，这时github给我们返回access_token,赋予登录身份
     * @param accessTokenDTO
     * @return String access_token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string(); //string:access_token=15e311ef9386a3e26df3cf2268c17bce9f851ea4&scope=user&token_type=bearer
            String[] split = string.split("&");
            String access_token = split[0].split("=")[1];
            return access_token;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这里是模拟带着access_token访问GitHub获取user身份的过程
     * @param access_token
     * @return GithubUser githubUser
     */
    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }
}
