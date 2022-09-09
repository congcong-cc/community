package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> data;
    private List<Integer> pages;
    private Integer page;
    private boolean showPrePage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showLastPage;
    private Integer totalPages;

    public void setOtherAttributes(int totalPages, Integer page) {
        this.page=page;
        this.totalPages=totalPages;
        pages = new ArrayList<>();
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPages){
                pages.add(page+i);
            }
        }
        this.pages=pages;
        // 是否展示上一页
        if(page==1){
            showPrePage=false;
        }else{
            showPrePage=true;
        }
        // 是否展示下一页
        if(page==totalPages){
            showNextPage=false;
        }else{
            showNextPage=true;
        }
        // 是否展示首页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        // 是否展示尾页
        if(pages.contains(totalPages)){
            showLastPage=false;
        }else{
            showLastPage=true;
        }

    }
}
