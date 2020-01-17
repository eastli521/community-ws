package life.majiang.community1.Controller;

import life.majiang.community1.dto.AccessTokenDTO;
import life.majiang.community1.dto.GithubUser;
import life.majiang.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    //从配置文件中读取数值，以后修改不需要改代码，只需要改配置文件即可
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //这是一个模拟登录的过程，实际上，我们在第一次访问github时除了code,其他参数传过去了（index.html)
        //code应该是github为了确认时返回的code（返回页面在GitHub中定义了）
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(access_token);
        System.out.println(githubUser.getName());
        return "Index";
    }
}
