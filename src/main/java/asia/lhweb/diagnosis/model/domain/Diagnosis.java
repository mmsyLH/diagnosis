package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 诊断表
 * @TableName diagnosis
 */
@Data
public class Diagnosis implements Serializable {
    /**
     * 诊断表id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 预约Id
     */
    private Integer appointmentId;

    /**
     * 诊断结果
     */
    private String diagnosisResults;

    /**
     * 诊断详细描述
     */
    private String diagnosisDescription;

    /**
     * 诊断时间
     */
    private Date createTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}