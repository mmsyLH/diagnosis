package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.AppointmentMapper;
import asia.lhweb.diagnosis.mapper.CommentMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.Comment;
import asia.lhweb.diagnosis.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class CommentServiceImpl
implements CommentService{
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private AppointmentMapper appointmentMapper;
    /**
     * 按辅导员编号页面
     *
     * @param counselorId 辅导员id
     * @param pageNo      页面没有
     * @param pageSize    页面大小
     * @return {@link BaseResponse}<{@link PageResult}<{@link Comment}>>
     */
    @Override
    public BaseResponse<PageResult<Comment>> pageByCounselorId(Integer counselorId, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (pageSize == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (counselorId == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Comment comment = new Comment();
        comment.setCounselorId(counselorId);
        comment.setPageNo(pageNo);
        comment.setPageSize(pageSize);
        Page<Comment> commentPage = commentMapper.selectAllIf(comment);
        if (commentPage == null || commentPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<Comment> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) commentPage.getTotal());
        pageResult.setItems(commentPage.getResult());
        pageResult.setPageTotalCount(commentPage.getPages());
        return ResultUtils.success(pageResult);
    }

    /**
     * 添加评论
     *
     * @param comment 评论
     * @return {@link Boolean}
     */
    @Override
    public Boolean addComment(Comment comment) {
        if (comment == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (commentMapper.insertSelective(comment) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //修改预约表的状态
        Appointment appointment = appointmentMapper.selectByPrimaryKey(Long.valueOf(comment.getAppointmentId()));
        appointment.setStatus(4);
        if (appointmentMapper.updateByPrimaryKeySelective(appointment) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }
}
