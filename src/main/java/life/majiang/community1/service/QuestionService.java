package life.majiang.community1.service;

import life.majiang.community1.dto.QuestionDTO;
import life.majiang.community1.mapper.QuestionMapper;
import life.majiang.community1.mapper.UserMapper;
import life.majiang.community1.model.Question;
import life.majiang.community1.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list(Integer page, Integer size){

        Integer offset = size *(page-1);

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
