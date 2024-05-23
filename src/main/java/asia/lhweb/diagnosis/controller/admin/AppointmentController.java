package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.enums.OperatorType;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.vo.CounselorAppointmentStatisticsVO;
import asia.lhweb.diagnosis.model.vo.SysUserStatisticsVO;
import asia.lhweb.diagnosis.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/11
 */
@Api("预约相关接口")
@RestController
@RequestMapping("/admin/appointment")
public class AppointmentController {
    @Resource
    private AppointmentService appointmentService;
    /**
     * 获取预约列表
     *
     * @param appointment 任命
     * @return {@link BaseResponse}<{@link List}<{@link Appointment}>>
     */
    @GetMapping("/page")
    @ApiOperation("获取咨询师预约列表")
    public BaseResponse<PageResult<Appointment>> getAppointmentList(Appointment appointment) {
        return appointmentService.getAppointmentList(appointment);
    }


    @GetMapping("/delete")
    @ApiOperation("删除")
    @Log(businessType = "删除", operatorType = OperatorType.ADMIN)
    public BaseResponse<Boolean> delete(Integer id) {
        if (id == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return appointmentService.delete(id);
    }

    @GetMapping("/update")
    @ApiOperation("更新")
    @Log(businessType = "更新", operatorType = OperatorType.ADMIN)
    public BaseResponse<Boolean> update(Appointment appointment) {
        if (appointment == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(appointmentService.updateAppointment(appointment));
    }

    /**
     * 获得用户统计报告
     * 通过注解描述日期格式
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link BaseResponse}<{@link SysUserStatisticsVO}>
     */
    @GetMapping("/getAppointmentStatistics")
    @ApiOperation("新增咨询量统计")
    public BaseResponse<CounselorAppointmentStatisticsVO> getAppointmentStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        CounselorAppointmentStatisticsVO appointmentStatistics = appointmentService.getAppointmentStatistics(begin, end);
        return ResultUtils.success(appointmentStatistics);
    }
}
