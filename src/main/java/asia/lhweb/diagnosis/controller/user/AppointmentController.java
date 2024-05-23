package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.vo.AppointmentVO;
import asia.lhweb.diagnosis.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/11
 */
@Api("预约相关接口")
@RestController("userAppointmentController")
@RequestMapping("/user/appointment")
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
    //getAppointmentInfoById
    @GetMapping("/getAppointmentInfoById")
    @ApiOperation("获取预约信息")
    public BaseResponse<AppointmentVO> getAppointmentInfoById(Integer id) {
        return appointmentService.getAppointmentInfoById(id);
    }
}
