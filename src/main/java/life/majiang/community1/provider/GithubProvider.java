package life.majiang.community1.provider;

import life.majiang.community1.dto.AccessTokenDTO;
import okhttp3.*;
import com.alibaba.fastjson.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        RequestBody body = RequestBody.create(String.valueOf(json),JSON);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch(IOException e){
        }
        return null;
    }
}
