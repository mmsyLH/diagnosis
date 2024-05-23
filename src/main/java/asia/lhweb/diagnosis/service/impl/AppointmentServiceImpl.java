package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.*;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.domain.Diagnosis;
import asia.lhweb.diagnosis.model.domain.TransactionUser;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.dto.WorkScheduleDTO;
import asia.lhweb.diagnosis.model.vo.AppointmentVO;
import asia.lhweb.diagnosis.model.vo.CounselorAppointmentStatisticsVO;
import asia.lhweb.diagnosis.service.AppointmentService;
import asia.lhweb.diagnosis.utils.data.DataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TransactionUserMapper transactionUserMapper;
    @Resource
    private TransactionCounselorMapper transactionCounselorMapper;
    @Resource
    private CounselorMapper counselorMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private DiagnosisMapper diagnosisMapper;
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
     * 删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @Override
    public BaseResponse<Boolean> delete(Integer id) {
        if (appointmentMapper.deleteByPrimaryKey(Long.valueOf(id)) <= 0) {
        throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(true,"删除成功");
    }

    /**
     * 更新约会
     *
     * @param appointment 任命
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateAppointment(Appointment appointment) {
        if (appointment.getId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (appointmentMapper.updateByPrimaryKeySelective(appointment) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    /**
     * 通过id获取预约详细信息
     *
     * @param id id
     * @return {@link BaseResponse}<{@link AppointmentVO}>
     */
    @Override
    public BaseResponse<AppointmentVO> getAppointmentInfoById(Integer id) {
        AppointmentVO appointmentVO = appointmentMapper.getAppointmentInfoById(id);
        if (appointmentVO == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //查询用户自己的评论
        Integer counselorId = appointmentVO.getCounselorId();
        Integer userId = appointmentVO.getUserId();
        Integer appointmentId = appointmentVO.getId();
        appointmentVO.setCommentList(commentMapper.selectListByCounselorIdAndUserIdAndAppointmentId(counselorId,userId,appointmentId));

        //查询咨询师的诊断结果
        Diagnosis diagnosis=diagnosisMapper.selectByUserIdAndAppointmentId(userId,appointmentId);
        if (diagnosis!=null){
            appointmentVO.setDiagnosisResults(diagnosis.getDiagnosisResults());
            appointmentVO.setDiagnosisDescription(diagnosis.getDiagnosisDescription());
            appointmentVO.setDiagnosisDate(diagnosis.getCreateTime());
        }

        return ResultUtils.success(appointmentVO);
    }

    /**
     * 获得指定时间范围内每个咨询师的预约量统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return {@link CounselorAppointmentStatisticsVO} 包含每个咨询师在指定时间范围内的预约量统计
     */
    public CounselorAppointmentStatisticsVO getAppointmentStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        if (begin.isAfter(end)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "开始日期不能大于结束日期");
        }

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        String dateStr = dateList.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(","));

        Map<Integer, Integer> counselorAppointmentMap = new HashMap<>(); // 咨询师ID -> 预约量

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN).minusNanos(1);

            List<Map<String, Object>> dailyAppointments = appointmentMapper.countAppointmentsByCounselorAndDateRange(beginTime, endTime);

            for (Map<String, Object> entry : dailyAppointments) {
                Integer counselorId = (Integer) entry.get("counselor_id");
                Long count = (Long) entry.get("count");

                // 获取咨询师姓名
                String counselorName = getCounselorNameById(counselorId);

                counselorAppointmentMap.put(counselorId, counselorAppointmentMap.getOrDefault(counselorId, 0) + count.intValue());
            }
        }

        List<Appointment> appointments = new ArrayList<>();
        StringBuilder appointmentListBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : counselorAppointmentMap.entrySet()) {
            Integer counselorId = entry.getKey();
            Integer appointmentCount = entry.getValue();

            // 获取咨询师姓名
            String counselorName = getCounselorNameById(counselorId);

            // 构建 Appointment 对象
            Appointment appointment = Appointment.builder()
                    .counselorName(counselorName)
                    .appointmentCount(appointmentCount)
                    .build();
            appointments.add(appointment);

            // 将咨询量拼接成字符串
            appointmentListBuilder.append(appointmentCount).append(",");
        }

        // 删除最后一个逗号
        if (appointmentListBuilder.length() > 0) {
            appointmentListBuilder.deleteCharAt(appointmentListBuilder.length() - 1);
        }

        // 3. 封装统计结果为 CounselorAppointmentStatisticsVO 对象返回
        return CounselorAppointmentStatisticsVO.builder()
                .appointmentList(appointmentListBuilder.toString())
                .appointments(appointments)
                .build();
    }

    /**
     * 通过id获取辅导员姓名
     *
     * @param counselorId 辅导员id
     * @return {@link String}
     */
    private String getCounselorNameById(Integer counselorId) {
        return counselorMapper.selectByPrimaryKey(Long.valueOf(counselorId)).getCounselorName();
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
    @Transactional(rollbackFor = Exception.class)
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
        if (appointment.getPrice()<0&&appointment.getPrice()!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"金额有误");
        }
        //用户余额扣钱
        if (appointment.getPrice()>0){
            Integer userId = appointment.getUserId();
            if (sysUserMapper.deductBalance(userId,appointment.getPrice())<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"余额不足");
            }
        }

        //用户添加流水记录
        TransactionUser transactionUser = new TransactionUser();
        transactionUser.setTransactionId(DataUtils.getId());
        transactionUser.setUserId(appointment.getUserId());
        //根据预约师id查询管理员id
        transactionUser.setAdminId(counselorMapper.selectByPrimaryKey(Long.valueOf(appointment.getCounselorId())).getAdminId());
        // transactionUser.setAdminId(appointment.getCounselorId());
        transactionUser.setTransactionType(2);
        transactionUser.setTransactionStatus(1);
        transactionUser.setPayChannel(3);
        transactionUser.setTransactionInfo("预约扣费");
        transactionUser.setMoney(appointment.getPrice());
        if (transactionUserMapper.insertSelective(transactionUser)<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"流水记录添加失败");
        }

        // //添加咨询师流水记录
        // TransactionCounselor transactionUser1 = new TransactionCounselor();
        // transactionUser1.setTransactionId(DataUtils.getId());
        // transactionUser1.setCounselorId(appointment.getCounselorId());
        // transactionUser1.setTransactionType(1);
        // transactionUser1.setUserId(appointment.getUserId());
        // transactionUser1.setTransactionStatus(1);
        // transactionUser1.setPayChannel(3);
        // transactionUser1.setTransactionInfo("预约收入");
        // transactionUser1.setMoney(appointment.getPrice());
        // if (transactionCounselorMapper.insertSelective(transactionUser1)<=0){
        //     throw new BusinessException(ErrorCode.PARAMS_ERROR,"流水记录添加失败");
        // }

        //修改排班状态为已预约
        WorkSchedule workSchedule = new WorkSchedule();
        // workSchedule.setId(appointment.getId());
        workSchedule.setCounselorId(appointment.getCounselorId());
        workSchedule.setDate(appointment.getAppointmentDate());
        workSchedule.setTimeSlot(appointment.getAppointmentTime());
        workSchedule.setStatus(2);
        if (workScheduleMapper.updateStatus(workSchedule)<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"排班状态修改失败");
        }

        //修改预约表
        appointment.setStatus(1);
        return appointmentMapper.insertSelective(appointment)>0;
    }
}
