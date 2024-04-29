package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预约表
 * @TableName appointment
 */
@Data
public class Appointment implements Serializable {
    /**
     * 预约表id
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
     * 预约领域id
     */
    private Integer areaId;

    /**
     * 预约时间例如 2024:04:28
     */
    private Date appointmentDate;

    /**
     * 预约时间段
     */
    private Date appointmentTime;

    /**
     * 预约的问题描述
     */
    private String appointmentDescription;

    /**
     * 预约回复
     */
    private String appointmentReply;

    /**
     * 1已申请 2预约成功 3预约失败
     */
    private Integer status;

    /**
     * 预约价格
     */
    private Double price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}