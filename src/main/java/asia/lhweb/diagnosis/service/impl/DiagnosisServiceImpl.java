package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.*;
import asia.lhweb.diagnosis.model.domain.*;
import asia.lhweb.diagnosis.service.DiagnosisService;
import asia.lhweb.diagnosis.utils.data.DataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @description 针对表【diagnosis(诊断表)】的数据库操作Service实现
 * @createDate 2024-04-28 20:11:50
 */
@Service
public class DiagnosisServiceImpl
        implements DiagnosisService {
    @Resource
    private DiagnosisMapper diagnosisMapper;
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private CounselorMapper counselorMapper;

    @Resource
    private TransactionCounselorMapper transactionCounselorMapper;

    @Resource
    private AppointmentMapper appointmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addDiagnosis(Diagnosis diagnosis) {
        Double price = diagnosis.getPrice();
        Integer counselorId = diagnosis.getCounselorId();
        Integer userId = diagnosis.getUserId();
        Integer appointmentId = diagnosis.getAppointmentId();
        String diagnosisDescription = diagnosis.getDiagnosisDescription();
        String diagnosisResults = diagnosis.getDiagnosisResults();
        // 1 添加诊断记录
        Diagnosis saveDiagnosis = new Diagnosis();
        saveDiagnosis.setUserId(userId);
        saveDiagnosis.setAppointmentId(appointmentId);
        saveDiagnosis.setDiagnosisDescription(diagnosisDescription);
        saveDiagnosis.setDiagnosisResults(diagnosisResults);
        if (diagnosisMapper.insertSelective(saveDiagnosis) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加诊断记录失败");
        }
        // 2 根据咨询师id查询管理员id
        Counselor counselor = counselorMapper.selectByPrimaryKey(Long.valueOf(counselorId));
        Integer adminId = counselor.getAdminId();

        // 3 修改咨询师余额
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(Long.valueOf(adminId));
        sysAdmin.setMoney(sysAdmin.getMoney() + price);
        if (sysAdminMapper.addBalance(sysAdmin) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加咨询师余额失败");
        }
        // 4 修改预约状态
        Integer appointmentStatus = diagnosis.getAppointmentStatus();
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(appointmentStatus);
        if (appointmentMapper.updateByPrimaryKeySelective(appointment) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改预约状态失败");
        }
        // 5 添加流水
        TransactionCounselor transactionCounselor = new TransactionCounselor();
        transactionCounselor.setTransactionId(DataUtils.getId());
        transactionCounselor.setCounselorId(counselorId);
        transactionCounselor.setMoney(price);
        transactionCounselor.setTransactionStatus(1);
        transactionCounselor.setPayChannel(3);
        transactionCounselor.setUserId(userId);
        transactionCounselor.setTransactionInfo("完成用户诊断的资金到账");
        transactionCounselor.setTransactionType(1);
        if (transactionCounselorMapper.insertSelective(transactionCounselor) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加流水失败");
        }

        return ResultUtils.success("诊断成功");
    }
}
