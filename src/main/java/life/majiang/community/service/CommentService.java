package life.majiang.community.service;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    ResultDTO insert(Comment comment);
}
