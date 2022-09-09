package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);
}
