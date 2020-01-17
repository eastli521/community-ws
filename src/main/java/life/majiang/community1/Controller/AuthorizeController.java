package life.majiang.community1.Controller;

import life.majiang.community1.dto.AccessTokenDTO;
import life.majiang.community1.dto.GithubUser;
import life.majiang.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //这是一个模拟登录的过程，实际上，我们在第一次访问github时除了code,其他参数传过去了（index.html)
        //code应该是github为了确认时返回的code（返回页面在GitHub中定义了）
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("de84b852c893ffa9cec0");
        accessTokenDTO.setClient_secret("37d6043057f1fa12a2f5a481fe6ee08f4ec3b380");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(access_token);
        System.out.println(githubUser.getName());
        return "Index";
    }
}
