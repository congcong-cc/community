package life.majiang.community.service.impl;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CodeEnums;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtraMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.*;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtraMapper questionExtraMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public ResultDTO insert(Comment comment) {
        ResultDTO resultDTO = new ResultDTO();
        Integer type = comment.getType();
        Long parentId;
        if(CommentTypeEnum.QUESTION.getType().equals(type)){
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(comment.getParentId());
            List<Question> questionList = questionMapper.selectByExample(example);
            if(questionList.size()<=0){
                resultDTO.setCode(CodeEnums.QUESTION_IS_NOT_EXIST.getCode());
                resultDTO.setMessage(CodeEnums.QUESTION_IS_NOT_EXIST.getMessage());
                return resultDTO;
            }
            parentId = comment.getParentId();
        }else{
            CommentExample example = new CommentExample();
            example.createCriteria().andIdEqualTo(comment.getParentId());
            List<Comment> commentList = commentMapper.selectByExample(example);
            if(commentList.size()<=0){
                resultDTO.setCode(CodeEnums.COMMENT_IS_NOT_EXIST.getCode());
                resultDTO.setMessage(CodeEnums.COMMENT_IS_NOT_EXIST.getMessage());
                return resultDTO;
            }
            while(!CommentTypeEnum.QUESTION.getType().equals(commentList.get(0).getType())){
                CommentExample example1 = new CommentExample();
                example1.createCriteria().andIdEqualTo(commentList.get(0).getParentId());
                commentList = commentMapper.selectByExample(example1);
            }
            parentId = commentList.get(0).getParentId();
        }
        commit(comment,parentId);
        resultDTO.setCode(CodeEnums.OK.getCode());
        resultDTO.setMessage(CodeEnums.OK.getMessage());
        return resultDTO;
    }

    @Override
    public List<CommentDTO> getByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()<=0){
            return new ArrayList<>();
        }

        Set<Integer> collect = comments.stream().map(m -> m.getCommentator()).collect(Collectors.toSet());
        List<String> usersAccountId = new ArrayList<>();
        for (Integer integer : collect) {
            usersAccountId.add(String.valueOf(integer));
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(usersAccountId);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));
        List<CommentDTO> commentDTOList = new ArrayList<>();


        return ;
    }

    @Transactional
    public void commit(Comment comment,Long parentId){
        commentMapper.insert(comment);
        questionExtraMapper.incCommentCount(parentId);
    }
}
