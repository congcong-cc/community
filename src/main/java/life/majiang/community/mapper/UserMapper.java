package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (account_id,name,token,avatar_url,gmt_created,gmt_modified) VALUES(#{accountId},#{name},#{token},#{avatarUrl},#{gmtCreated},#{gmtModified});")
    void save(User user);

    @Select("Select * FROM user WHERE token = #{token}")
    User findByToken(String token);

    @Select("Select * FROM user WHERE id = #{id}")
    User findById(Integer id);
}
