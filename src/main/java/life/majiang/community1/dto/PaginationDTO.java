package life.majiang.community1.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    //是否有向前按钮
    private boolean showPrevious;
    //是否有第一页按钮
    private boolean showFirstPage;
    //是否有向前按钮
    private boolean showNext;
    //是否有尾页按钮
    private boolean showEndPage;
    //当前页
    private Integer page;
    //一共多少页
    private Integer totalPage;
    //
    private List<Integer> pages = new ArrayList<>();

    /**
     *设定展示规则
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {

        totalPage = (totalCount%size == 0)? totalCount/size : totalCount/size+1;

        if(page < 1){page = 1;}
        if(page > totalPage){page = totalPage;}
        this.page = page;

        //展示哪些页,往前展示三页，往后展示3页
        pages.add(page);
        for(int i = 1; i <= 3; i++){
            if(page - i > 0){
                pages.add(0,page - i); //从前边插入
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //是否展示上一页
        showPrevious = page != 1;

        //是否展示下一页
        showNext = page != totalPage;

        //是否展示第一页
        showFirstPage = !pages.contains(1);

        //是否展示尾页
        showEndPage = !pages.contains(totalPage);

    }
}
