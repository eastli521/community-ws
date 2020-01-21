package life.majiang.community1.mapper;

import life.majiang.community1.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tags})")
    void create(Question question);
}
