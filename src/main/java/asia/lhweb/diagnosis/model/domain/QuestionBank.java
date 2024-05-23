package asia.lhweb.diagnosis.model.domain;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 题库表
 * @TableName question_bank
 */
@Data
public class QuestionBank extends PageRequest implements Serializable {
    /**
     * 题库表id
     */
    private Integer id;

    /**
     * 领域id
     */
    private Integer areaId;

    /**
     * 标题名称
     */
    private String title;

    /**
     * 题库描述
     */
    private String questionInfo;

    /**
     * 题目json字符串
     */
    private String question;

    /**
     * 结果json字符串
     */
    private String result;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}