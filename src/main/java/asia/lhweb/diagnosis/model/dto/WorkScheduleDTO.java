package asia.lhweb.diagnosis.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 排班表
 * @TableName work_schedule
 */
@Data
public class WorkScheduleDTO implements Serializable {

    /**
     * 咨询师id
     */
    private Integer counselorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}