package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Comment;
import asia.lhweb.diagnosis.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论控制器
 *
 * @author 罗汉
 * @date 2024/05/15
 */
@Api("咨询师评论")
@RequestMapping("/user/comment")
@RestController("userCommentController")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 通过辅导员id获取评论
     *
     * @param comment 评论
     * @return {@link BaseResponse}<{@link PageResult}<{@link Comment}>>
     */
    @ApiOperation("根据咨询师id获取评论")
    @GetMapping("/getCommentByCounselorId")
    public BaseResponse<PageResult<Comment>> getCommentByCounselorId(Comment comment) {
        Integer counselorId = comment.getCounselorId();
        Integer pageNo = comment.getPageNo();
        Integer pageSize = comment.getPageSize();
        if (counselorId == null) {
           throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return commentService.pageByCounselorId(counselorId,pageNo,pageSize);
    }

    @ApiOperation("添加评论")
    @PostMapping("/addComment")
    public BaseResponse addComment(@RequestBody Comment comment) {
        Integer userId = comment.getUserId();
        Integer counselorId = comment.getCounselorId();
        String commentContent = comment.getCommentContent();
        if (userId == null || counselorId == null || commentContent == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean aBoolean = commentService.addComment(comment);
        return ResultUtils.success(aBoolean);
    }
}
