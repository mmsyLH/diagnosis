package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Comment;

/**
* @author Administrator
* @description 针对表【comment(评论表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Comment
*/
public interface CommentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

}
