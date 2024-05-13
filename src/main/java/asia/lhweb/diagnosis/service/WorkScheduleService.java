package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.vo.WorkScheduleVO;

import java.util.HashMap;
import java.util.List;

/**
* @author Administrator
* @description 针对表【work_schedule(排班表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface WorkScheduleService{

    /**
     * 通过辅导员id获取时间表
     *
     * @param counselorId 辅导员id
     * @return {@link BaseResponse}<{@link HashMap}<{@link String}, {@link List}<{@link String}>>>
     */
    BaseResponse<HashMap<String,List<WorkScheduleVO>>> getScheduleByCounselorId(Integer counselorId);

    /**
     * 添加日程安排
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    BaseResponse<Boolean> addSchedule(WorkSchedule workSchedule);

    /**
     * 删除计划
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    BaseResponse<Boolean> deleteSchedule(WorkSchedule workSchedule);

    /**
     * 获取预约列表
     *
     * @param appointment 任命
     * @return {@link BaseResponse}<{@link List}<{@link Appointment}>>
     */

}
