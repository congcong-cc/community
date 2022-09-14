package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (title,description,tag,creator,gmt_created,gmt_modified) VALUES(#{title},#{description},#{tag},#{creator},now(),now())")
    void insert(Question question);

    @Select("SELECT * FROM question LIMIT #{offset},#{size}")
    List<Question> list(int offset, Integer size);

    @Select("SELECT * FROM question WHERE creator = #{accountId} LIMIT #{offset},#{size}")
    List<Question> listByUserAccountId(Integer accountId,int offset, Integer size);

    @Select("SELECT count(1) FROM question")
    int total();

    @Select("SELECT count(1) FROM question WHERE creator = #{accountId}")
    int totalByUserAccountId(Integer accountId);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question getById(Integer id);

    @Update("UPDATE question SET title=#{title},description=#{description},tag=#{tag},gmt_modified=now() WHERE id = #{id}")
    void update(Question question);
}
