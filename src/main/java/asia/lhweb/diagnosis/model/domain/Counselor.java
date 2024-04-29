package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 咨询师表
 * @TableName counselor
 */
@Data
public class Counselor implements Serializable {
    /**
     * 咨询师表id
     */
    private Integer id;

    /**
     * 咨询师姓名
     */
    private String counselorName;

    /**
     * 管理员id
     */
    private Integer adminId;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 学历
     */
    private String educationlv;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 专业背景
     */
    private String background;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}