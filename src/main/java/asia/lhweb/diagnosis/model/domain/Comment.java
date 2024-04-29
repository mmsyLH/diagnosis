package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论表
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 评论表id
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 咨询师id
     */
    private Integer counselorId;

    /**
     * 评论的内容
     */
    private String commentContent;

    /**
     * 预约id
     */
    private Integer appointmentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}