package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.WorkScheduleMapper;
import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import asia.lhweb.diagnosis.model.vo.WorkScheduleVO;
import asia.lhweb.diagnosis.service.WorkScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author Administrator
* @description 针对表【work_schedule(排班表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class WorkScheduleServiceImpl
implements WorkScheduleService{
    @Resource
    private WorkScheduleMapper workScheduleMapper;


    /**
     * 通过辅导员id获取时间表
     *
     * @param counselorId 辅导员id
     * @return {@link BaseResponse}<{@link HashMap}<{@link String}, {@link List}<{@link WorkScheduleVO}>>>
     */
    @Override
    public BaseResponse<HashMap<String, List<WorkScheduleVO>>> getScheduleByCounselorId(Integer counselorId) {
        //查询该咨询师的全部排班
        List<WorkSchedule> workScheduleList = workScheduleMapper.selectListByCounselorId(counselorId);
        if (workScheduleList.isEmpty()){
            throw new BusinessException(ErrorCode.NULL_ERROR,"暂时没有排班");
        }
        // 创建HashMap存储结果
        HashMap<String, List<WorkScheduleVO>> res = new HashMap<>();

        // 遍历排班列表
        for (WorkSchedule workSchedule : workScheduleList) {
            String date = workSchedule.getDate();
            String timeSlot = workSchedule.getTimeSlot();

            // 如果日期已经存在于HashMap中，则将时间段追加到已有值的后面
            if (res.containsKey(date)) {
                List<WorkScheduleVO> existingTimeSlots = res.get(date);
                existingTimeSlots.add(WorkScheduleVO.builder().timeSlot(timeSlot).status(workSchedule.getStatus()).build());
            } else {
                // 如果日期不存在于HashMap中，则新建一个List存储时间段并放入HashMap中
                List<WorkScheduleVO> timeSlots = new ArrayList<>();
                timeSlots.add(WorkScheduleVO.builder().timeSlot(timeSlot).status(workSchedule.getStatus()).build());
                res.put(date, timeSlots);
            }
        }
        return ResultUtils.success(res);
    }

    /**
     * 添加日程安排
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Override
    public BaseResponse<Boolean> addSchedule(WorkSchedule workSchedule) {
        //判断是否存在
        WorkSchedule resultSchedule = workScheduleMapper.selectByWorkScheduleSelective(workSchedule);
        if (resultSchedule!= null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该排班已存在");
        }
        int i = workScheduleMapper.insertSelective(workSchedule);
        if (i <= 0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 删除计划
     *
     * @param workSchedule 工作安排
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Override
    public BaseResponse<Boolean> deleteSchedule(WorkSchedule workSchedule) {
        //判断是否存在
        WorkSchedule resultSchedule = workScheduleMapper.selectByWorkScheduleSelective(workSchedule);
        if (resultSchedule== null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"要删除的已经存在");
        }
        int i = workScheduleMapper.deleteByPrimaryKey(Long.valueOf(resultSchedule.getId()));
        if (i <= 0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtils.success(true);
    }



}
