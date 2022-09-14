package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);

    PaginationDTO listByUserAccountId(Integer accountId,Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void insertOrUpdate(Question question);
}
