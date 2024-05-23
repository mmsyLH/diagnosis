package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysLogMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysLog;
import asia.lhweb.diagnosis.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【sys_log(系统日志表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class SysLogServiceImpl
implements SysLogService{
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {

        int i = sysLogMapper.insertSelective(sysLog);

    }

    /**
     * 日志页面
     *
     * @param sysLog 系统日志
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysLog}>>
     */
    @Override
    public BaseResponse<PageResult<SysLog>> logPage(SysLog sysLog) {
        int pageNo = sysLog.getPageNo();
        int pageSize = sysLog.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<SysLog> sysLogPage = sysLogMapper.selectAllIf(sysLog);
        if (sysLogPage == null || sysLogPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<SysLog> sysLogPageResult = new PageResult<>();
        sysLogPageResult.setPageNo(pageNo);
        sysLogPageResult.setPageSize(pageSize);
        sysLogPageResult.setTotalRow((int) sysLogPage.getTotal());
        sysLogPageResult.setItems(sysLogPage.getResult());
        sysLogPageResult.setPageTotalCount(sysLogPage.getPages());
        return ResultUtils.success(sysLogPageResult);
    }
}
