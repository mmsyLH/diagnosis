package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题库报告表
 * @TableName question_bank_report
 */
@Data
public class QuestionBankReport implements Serializable {
    /**
     * 题库报告表id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 领域id
     */
    private Integer questionBankId;

    /**
     * 做题实际结果
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0未删除 1删除
     */
    private Integer isDetele;

    private static final long serialVersionUID = 1L;
}