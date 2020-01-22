package life.majiang.community1.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer viewcount;
    private Integer commentcount;
    private Integer likecount;
}
