package asia.lhweb.diagnosis.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 排班表
 * @TableName work_schedule
 */
@Data
@Builder
public class WorkScheduleVO implements Serializable {

    /**
     * 排班的时间段字符串。例如'11:00 - 12:00'
     */
    private String timeSlot;

    /**
     * 2预约 1空闲
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}