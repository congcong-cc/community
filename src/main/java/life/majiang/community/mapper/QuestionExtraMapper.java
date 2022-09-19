package life.majiang.community.mapper;

public interface QuestionExtraMapper {
    void incViewCount(Long id);

    void incCommentCount(Long id);
}
