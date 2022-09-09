package life.majiang.community.service.impl;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PaginationDTO list(Integer page, Integer size) {
        int totalCount = questionMapper.total();
        int totalPages;
        if(totalCount%size==0){
            totalPages=totalCount/size;
        }else{
            totalPages=totalCount/size+1;
        }
        // 防止页码错误，超出页数范围
        if(page<=0){
            page=1;
        }
        if(page>totalPages){
            page=totalPages;
        }
        int offset = size*(page-1);
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setOtherAttributes(totalPages,page);
        return paginationDTO ;
    }
}
