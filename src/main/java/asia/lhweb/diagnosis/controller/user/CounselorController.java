package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.Area;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.dto.WorkScheduleDTO;
import asia.lhweb.diagnosis.service.AppointmentService;
import asia.lhweb.diagnosis.service.AreaService;
import asia.lhweb.diagnosis.service.CounselorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/10
 */
@Api("咨询相关接口")
@RestController("userCounselorController")
@RequestMapping("/user/counselor")
public class CounselorController {
    @Resource
    private CounselorService counselorService;

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private AreaService areaService;

    /**
     * 获取咨询师领域列表
     *
     * @return {@link BaseResponse}<{@link List}<{@link Area}>>
     */
    @GetMapping("/getAreaList")
    @ApiOperation("获取咨询师领域列表")
    public BaseResponse<List<Area>> getAreaList() {
        return areaService.getAreaList();
    }


    /**
     * 获取某个领域下的咨询师列表
     *
     * @return {@link BaseResponse}<{@link List}<{@link Area}>>
     */
    @GetMapping("/getCounselorListByAreaId")
    @ApiOperation("获取咨询师领域列表")
    public BaseResponse<List<Counselor>> getCounselorListByAreaId(Integer areaId) {
        if (areaId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return counselorService.getCounselorListByAreaId(areaId);
    }

    /**
     * 根据辅导员身份和日期获取工作时间表
     *
     * @param workScheduleDTO 工作时间表
     * @return {@link BaseResponse}<{@link List}<{@link WorkSchedule}>>
     */
    @GetMapping("/getScheduleByCounselorIdAndTime")
    @ApiOperation("获取咨询师排班")
    public BaseResponse<HashMap<String, List<String>>> getScheduleByCounselorIdAndTime(WorkScheduleDTO workScheduleDTO) {
        return appointmentService.getScheduleByCounselorIdAndTime(workScheduleDTO);
    }

    /**
     * 添加预约
     *
     * @param appointment 任命
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/addAppointment")
    @ApiOperation("添加预约")
    public BaseResponse<Boolean> addAppointment(@RequestBody Appointment appointment) {
        return ResultUtils.success(appointmentService.addAppointment(appointment));
    }
}
