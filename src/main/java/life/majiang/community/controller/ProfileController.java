package life.majiang.community.controller;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model
                          ) {
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("section", "个人中心");
        PaginationDTO paginationDTO = null;
        if("questions".equals(action)){
            model.addAttribute("sectionName", "我的问题");
            model.addAttribute("action","questions");
            paginationDTO = questionService.listByUserAccountId(Integer.valueOf(user.getAccountId()), page, size);
        }else if("replies".equals(action)){
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("action","replies");
        }else if("redHeart".equals(action)){
            model.addAttribute("sectionName", "点赞问题");
            model.addAttribute("action","redHeart");
        }
        if(paginationDTO!=null){
            model.addAttribute("paginationDTO",paginationDTO);
        }else{
            model.addAttribute("paginationDTO",new PaginationDTO());
        }

        return "profile";
    }
}
