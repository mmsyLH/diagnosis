package asia.lhweb.diagnosis.model.dto;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 题库报告表
 * @TableName question_bank_report
 */
@Data
public class QuestionBankReportDTO extends PageRequest implements Serializable {
    /**
     * 题库报告表id
     */
    private Integer id;

    /**
     * 领域名称
     */
    private String areaName;

    private String areaId;

    private String title;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分数
     */
    private Integer score;

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
    private String createTime;

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}