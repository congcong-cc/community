package life.majiang.community.service.impl;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionExtraMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import life.majiang.community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class QuestionServiceImpl implements QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionExtraMapper questionExtraMapper;
    @Override
    public PaginationDTO list(Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().getAllCriteria();
        int totalCount = (int)questionMapper.countByExample(example);
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
        if(page>totalPages&&totalPages>0){
            page=totalPages;
        }
        int offset = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().getAllCriteria();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            UserExample example1 = new UserExample();
            example1.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
            List<User> users = userMapper.selectByExample(example1);
            User user = users.get(0);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setOtherAttributes(totalPages,page);
        return paginationDTO ;
    }

    @Override
    public PaginationDTO listByUserAccountId(Integer accountId, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(accountId);
        int totalCount = (int)questionMapper.countByExample(example);
        int totalPages;
        if(totalCount%size==0&&totalCount!=0){
            totalPages=totalCount/size;
        }else{
            totalPages=totalCount/size+1;
        }
        // 防止页码错误，超出页数范围
        if(page>totalPages){
            page=totalPages;
        }
        if(page<=0){
            page=1;
        }
        int offset = size*(page-1);
        QuestionExample example2 = new QuestionExample();
        example2.createCriteria().andCreatorEqualTo(accountId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example2,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            UserExample example1 = new UserExample();
            example1.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
            List<User> users = userMapper.selectByExample(example1);
            User user = users.get(0);
            questionDTO.setUser(user);
            questionDTO.setCreator(String.valueOf(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setOtherAttributes(totalPages,page);
        return paginationDTO ;
    }

    @Override
    public QuestionDTO getById(Integer id) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        List<Question> questionList = questionMapper.selectByExample(example);
        Question question = questionList.get(0);
        QuestionDTO questionDTO = new QuestionDTO();
        UserExample example1 = new UserExample();
        example1.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
        List<User> users = userMapper.selectByExample(example1);
        User user = users.get(0);
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setCreator(String.valueOf(question.getCreator()));
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void insertOrUpdate(Question question) {
        if(question.getId()==null){
            question.setInterest(0);
            question.setCommentCount(0);
            question.setRedHeart(0);
            question.setViewCount(0);
            questionMapper.insert(question);
        }else{
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExample(question, example);
        }
    }

    @Override
    public void incViewCount(Integer id) {
        questionExtraMapper.incViewCount(id);
    }
}
