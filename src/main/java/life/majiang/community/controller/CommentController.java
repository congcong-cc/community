package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.model.Comment;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setCommentator(1);
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0L);
        comment.setType(commentDTO.getType());
        comment.setGmtCreated(new Date());
        comment.setGmtModified(new Date());
        comment.setParentId(commentDTO.getParentId());
        commentService.insert(comment);
        return null;
    }
}
