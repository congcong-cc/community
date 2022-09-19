package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CodeEnums;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        ResultDTO resultDTO = new ResultDTO();
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            resultDTO.setCode(CodeEnums.OK.getCode());
            resultDTO.setMessage("用户未登录");
            return resultDTO;
        }
        Comment comment = new Comment();
        comment.setCommentator(Integer.valueOf(user.getAccountId()));
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0L);
        comment.setType(commentDTO.getType());
        comment.setGmtCreated(new Date());
        comment.setGmtModified(new Date());
        comment.setParentId(commentDTO.getParentId());
        resultDTO = commentService.insert(comment);
        return resultDTO;
    }
}
