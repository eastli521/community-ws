package life.majiang.community1.dto;

import life.majiang.community1.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewcount;
    private Integer commentcount;
    private Integer likecount;
    private User user;
}
