package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Comment;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    Page<Comment> selectAllIf(Comment comment);

    List<Comment> selectListByCounselorId(Integer counselorId);

    List<Comment> selectListByCounselorIdAndUserId(@Param("counselorId") Integer counselorId, @Param("userId") Integer userId);

    List<Comment> getCommentsByCounselorId(@Param("counselorId") Integer counselorId);

    List<Comment> selectListByCounselorIdAndUserIdAndAppointmentId(@Param("counselorId") Integer counselorId, @Param("userId") Integer userId, @Param("appointmentId") Integer appointmentId);
}
