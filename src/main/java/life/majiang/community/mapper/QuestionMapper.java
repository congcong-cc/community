package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (title,description,tag,creator,gmt_created,gmt_modified) VALUES(#{title},#{description},#{tag},#{creator},now(),now())")
    void insert(Question question);
}
