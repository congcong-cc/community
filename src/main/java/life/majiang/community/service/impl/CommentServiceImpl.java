package life.majiang.community.service.impl;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CodeEnums;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtraMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtraMapper questionExtraMapper;
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

    @Transactional
    public void commit(Comment comment,Long parentId){
        commentMapper.insert(comment);
        questionExtraMapper.incCommentCount(parentId);
    }
}
