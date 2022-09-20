package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CodeEnums;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request){
        ResultDTO resultDTO = new ResultDTO();
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            resultDTO.setCode(CodeEnums.USER_NOT_LOGIN.getCode());
            resultDTO.setMessage("用户未登录");
            return resultDTO;
        }
        if(commentDTO.getContent()==null|| StringUtils.isBlank(commentDTO.getContent())){
            resultDTO.setMessage(CodeEnums.COMMENT_CONTENT_IS_NULL.getMessage());
            resultDTO.setCode(CodeEnums.COMMENT_CONTENT_IS_NULL.getCode());
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
