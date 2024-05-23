package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysLog;

/**
* @author Administrator
* @description 针对表【sys_log(系统日志表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface SysLogService {
    /**
     * 保存
     *
     * @param sysLog 系统日志
     */
    void save(SysLog sysLog);

    /**
     * 日志页面
     *
     * @param sysLog 系统日志
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysLog}>>
     */
    BaseResponse<PageResult<SysLog>> logPage(SysLog sysLog);
}
