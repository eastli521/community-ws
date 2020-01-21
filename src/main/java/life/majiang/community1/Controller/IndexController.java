package life.majiang.community1.Controller;

import life.majiang.community1.model.User;
import life.majiang.community1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 这个函数模拟的是服务端处理cookie的过程
     * 包括根据cookie查找session，把session信息返还用户
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request){
        //之后客户端每次访问服务器都会带着自己被颁发的cookie值
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return "Index";
        else{
            for(Cookie cookie:cookies){
                if("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    //从数据库中通过token找到相关数值返回用户信息（这个token相当于sessionID）
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        //通过cookie传的token找到服务端存储的session给用户
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
            return "Index";
        }

    }
}
