package asia.lhweb.diagnosis.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 题库表
 * @TableName question_bank
 */
@Data
public class QuestionBankDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;

    private String areaName;

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
     * 问题
     */
    private String questions;

    /**
     * 结果
     */
    private String results;

}
