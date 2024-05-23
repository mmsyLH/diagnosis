package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysLog;
import asia.lhweb.diagnosis.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志控制器
 *
 * @author 罗汉
 * @date 2024/05/16
 */
@RestController
@RequestMapping("/admin/log")
@Api("日志相关接口")
public class LogController {
    @Resource
    private SysLogService sysLogService;

    @GetMapping("/logPage")
    @ApiOperation("日志分页")
    public BaseResponse<PageResult<SysLog>> logPage(SysLog sysLog) {
        return sysLogService.logPage(sysLog);
    }

}
