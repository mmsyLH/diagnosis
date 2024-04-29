package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 排班表
 * @TableName work_schedule
 */
@Data
public class WorkSchedule implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 咨询师id
     */
    private Integer counselorId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 排班的时间段字符串。例如'11:00 - 12:00'
     */
    private Date timeSlot;

    /**
     * 2预约 1空闲
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}