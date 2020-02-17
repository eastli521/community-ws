package life.majiang.community1.Controller;

import life.majiang.community1.dto.PaginationDTO;
import life.majiang.community1.dto.QuestionDTO;
import life.majiang.community1.mapper.QuestionMapper;
import life.majiang.community1.model.Question;
import life.majiang.community1.model.User;
import life.majiang.community1.mapper.UserMapper;
import life.majiang.community1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 这个函数模拟的是服务端处理cookie的过程
     * 包括根据cookie查找session，把session信息返还用户
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size", defaultValue = "4") Integer size){
        //之后客户端每次访问服务器都会带着自己被颁发的cookie值
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        if(cookies!=null && cookies.length!=0){
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
            //从数据库查看列表信息
            PaginationDTO pagination = questionService.list(page, size);
            model.addAttribute("pagination",pagination);
            return "Index";
        }
        //从数据库查看列表信息
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination",pagination);
        return "Index";
    }
}
