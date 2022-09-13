package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);

    PaginationDTO listByUserId(Integer userId,Integer page, Integer size);

    QuestionDTO getById(Integer id);
}
