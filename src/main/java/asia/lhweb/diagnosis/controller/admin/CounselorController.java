package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import asia.lhweb.diagnosis.model.vo.CounselorAppointmentStatisticsVO;
import asia.lhweb.diagnosis.model.vo.SysUserStatisticsVO;
import asia.lhweb.diagnosis.model.vo.WorkScheduleVO;
import asia.lhweb.diagnosis.service.AppointmentService;
import asia.lhweb.diagnosis.service.CounselorService;
import asia.lhweb.diagnosis.service.WorkScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/6
 */
@Api("咨询师相关接口")
@RestController
@RequestMapping("/admin/counselor")
public class CounselorController {
    @Resource
    private CounselorService counselorService;
    @Resource
    private WorkScheduleService workScheduleService;
    @Resource
    private AppointmentService appointmentService;

    /**
     * 获取分页列表
     *
     * @param counselorDTO 辅导员dto
     * @return {@link BaseResponse}<{@link PageResult}<{@link Counselor}>>
     */
    @GetMapping("/page")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<Counselor>> page(CounselorDTO counselorDTO) {
        PageResult<Counselor> sysAdminPageResult = counselorService.page(counselorDTO);
        return ResultUtils.success(sysAdminPageResult);
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping ("/delete")
    @ApiOperation("删除")
    public BaseResponse<Boolean> deleteById(@RequestParam("id") Integer id) {
        if (id==null||id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "要删除的id有误");
        }
        boolean res = counselorService.deleteById(id);
        return ResultUtils.success(res,"删除成功");
    }

    /**
     * 获取指定时间预约量来源于哪些咨询师
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link BaseResponse}<{@link SysUserStatisticsVO}>
     */
    @GetMapping("/counselorStatistics")
    @ApiOperation("用户统计")
    public BaseResponse<CounselorAppointmentStatisticsVO> getCountCAStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        CounselorAppointmentStatisticsVO counselorAndAppointments = counselorService.getCountCAStatistics(begin, end);
        return ResultUtils.success(counselorAndAppointments);
    }

    /**
     * 根据辅导员身份和时间获取时间表
     * 根据辅导员身份和日期获取工作时间表
     *
     * @param counselorId 辅导员id
     * @return {@link BaseResponse}<{@link HashMap}<{@link String}, {@link List}<{@link String}>>>
     */
    @GetMapping("/getScheduleByCounselorId")
    @ApiOperation("获取咨询师排班")
    public BaseResponse<HashMap<String, List<WorkScheduleVO>>> getScheduleByCounselorIdAndTime(Integer counselorId) {
        return workScheduleService.getScheduleByCounselorId(counselorId);
    }

    /**
     * 添加日程安排
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping("/addSchedule")
    @ApiOperation("添加排班")
    public BaseResponse<Boolean> addSchedule(WorkSchedule workSchedule) {
        if (workSchedule == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return workScheduleService.addSchedule(workSchedule);
    }

    /**
     * 删除
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping("/deleteSchedule")
    @ApiOperation("删除排班")
    public BaseResponse<Boolean> deleteSchedule(WorkSchedule workSchedule) {
        if (workSchedule == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return workScheduleService.deleteSchedule(workSchedule);
    }


}
