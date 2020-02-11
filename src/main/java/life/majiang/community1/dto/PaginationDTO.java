package life.majiang.community1.dto;
import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> question;
    //是否有向前按钮
    private boolean showPrevious;
    //是否有向前按钮
    private boolean showFirstPage;
    //是否有向前按钮
    private boolean showNext;
    //是否有向前按钮
    private boolean showEndPage;

    private Integer page;
}
