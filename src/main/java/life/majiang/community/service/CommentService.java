package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    ResultDTO insert(Comment comment);

    List<CommentDTO> getByQuestionId(Long id);
}
