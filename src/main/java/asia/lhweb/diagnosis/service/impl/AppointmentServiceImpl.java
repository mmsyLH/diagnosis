package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.AppointmentMapper;
import asia.lhweb.diagnosis.mapper.WorkScheduleMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.dto.WorkScheduleDTO;
import asia.lhweb.diagnosis.service.AppointmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment(预约表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class AppointmentServiceImpl
implements AppointmentService{
    @Resource
    private WorkScheduleMapper workScheduleMapper;
    @Resource
    private AppointmentMapper appointmentMapper;
    /**
     * 获取预约列表
     *
     * @param appointment 任命
     * @return {@link BaseResponse}<{@link List}<{@link Appointment}>>
     */
    @Override
    public BaseResponse<PageResult<Appointment>> getAppointmentList(Appointment appointment) {
        int pageNo = appointment.getPageNo();
        int pageSize = appointment.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<Appointment> appointmentPage = appointmentMapper.selectAppointmentList(appointment);
        if (appointmentPage == null || appointmentPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<Appointment> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) appointmentPage.getTotal());
        pageResult.setItems(appointmentPage.getResult());
        pageResult.setPageTotalCount(appointmentPage.getPages());
        return ResultUtils.success(pageResult);
    }

    /**
     * 根据咨询师ID和时间获取排班表。
     *
     * @param workScheduleDTO 工作时间表
     * @return {@link BaseResponse}< {@link HashMap}<{@link Date}, {@link String}>>
     */
    @Override
    public BaseResponse< HashMap<String, List<String>>> getScheduleByCounselorIdAndTime(WorkScheduleDTO workScheduleDTO) {
        Integer counselorId = workScheduleDTO.getCounselorId();
        LocalDate begin = workScheduleDTO.getBegin();//2024-05-10
        LocalDate end = workScheduleDTO.getEnd();//2024-05-12
        //查询该咨询师的全部排班
        List<WorkSchedule> workScheduleList =workScheduleMapper.selectAllByCounselorId(counselorId,begin,end);
        if (workScheduleList.isEmpty()){
            throw new BusinessException(ErrorCode.NULL_ERROR,"该咨询师暂时没有排班");
        }
        // 创建HashMap存储结果
        HashMap<String, List<String>> res = new HashMap<>();

        // 遍历排班列表
        for (WorkSchedule workSchedule : workScheduleList) {
            String date = workSchedule.getDate();
            String timeSlot = workSchedule.getTimeSlot();

            // 如果日期已经存在于HashMap中，则将时间段追加到已有值的后面
            if (res.containsKey(date)) {
                List<String> existingTimeSlots = res.get(date);
                existingTimeSlots.add(timeSlot);
            } else {
                // 如果日期不存在于HashMap中，则新建一个List存储时间段并放入HashMap中
                List<String> timeSlots = new ArrayList<>();
                timeSlots.add(timeSlot);
                res.put(date, timeSlots);
            }
        }

        return ResultUtils.success(res);
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        if (!StringUtils.hasLength(appointment.getAppointmentTime())||!StringUtils.hasLength(appointment.getAppointmentDate())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"预约日期不能为空");
        }
        if (appointment.getUserId()==null||appointment.getCounselorId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户id或咨询师id不能为空");
        }
        if (appointment.getStatus()==null){
            appointment.setStatus(0);
        }
        if (!StringUtils.hasLength(appointment.getAppointmentDescription())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"预约描述不能为空");
        }
        if (appointment.getPrice()<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"金额有误");
        }

        return appointmentMapper.insertSelective(appointment)>0;
    }
}
