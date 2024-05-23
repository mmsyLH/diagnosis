package asia.lhweb.diagnosis.model.vo;


import asia.lhweb.diagnosis.model.domain.Appointment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 咨询师预约统计数字
 *
 * @author 罗汉
 * @date 2024/05/10
 */
@Data
@Builder
public class CounselorAppointmentStatisticsVO {
    private String appointmentList;
    private List<Appointment> appointments;

}