package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Comment;

/**
* @author Administrator
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface CommentService{

    /**
     * 按辅导员编号页面
     *
     * @param counselorId 辅导员id
     * @param pageNo      页面没有
     * @param pageSize    页面大小
     * @return {@link BaseResponse}<{@link PageResult}<{@link Comment}>>
     */
    BaseResponse<PageResult<Comment>> pageByCounselorId(Integer counselorId, Integer pageNo, Integer pageSize);

    /**
     * 添加评论
     *
     * @param comment 评论
     * @return {@link BaseResponse}<{@link Integer}>
     */
    Boolean addComment(Comment comment);
}
