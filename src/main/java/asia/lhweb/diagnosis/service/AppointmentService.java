package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.dto.WorkScheduleDTO;

import java.util.HashMap;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment(预约表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface AppointmentService{

    /**
     * 根据辅导员身份和时间获取时间表
     *
     * @param workScheduleDTO 工作时间表
     * @return {@link BaseResponse}<{@link List}<{@link WorkSchedule}>>
     */
    BaseResponse<HashMap<String,  List<String>>> getScheduleByCounselorIdAndTime(WorkScheduleDTO workScheduleDTO);

    /**
     * 添加预约
     *
     * @param appointment 任命
     * @return boolean
     */
    boolean addAppointment(Appointment appointment);

    /**
     * 获取预约列表
     *
     * @param appointment 任命
     * @return {@link BaseResponse}<{@link PageResult}<{@link Appointment}>>
     */
    BaseResponse<PageResult<Appointment>> getAppointmentList(Appointment appointment);
}
