package life.majiang.community1.Controller;

import life.majiang.community1.mapper.QuestionMapper;
import life.majiang.community1.mapper.UserMapper;
import life.majiang.community1.model.Question;
import life.majiang.community1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){

        if(title==null||title.equals("")){
            model.addAttribute("error","invalid title");
            return "publish";
        }
        if(description==null||description.equals("")){
            model.addAttribute("error","invalid description");
            return "publish";
        }
        if(tag==null||tag.equals("")){
            model.addAttribute("error","invalid tag");
            return "publish";
        }

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        User user = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                //从数据库中通过token找到相关数值返回用户信息（这个token相当于sessionID）
                user = userMapper.findByToken(token);
                if(user != null){
                    //通过cookie传的token找到服务端存储的session给用户
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
            if(user==null){
                model.addAttribute("error","the user did not sign in");
                return "publish";
            }
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
