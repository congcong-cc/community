package life.majiang.community.service;

import life.majiang.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    void insert(Comment comment);
}
