package asia.lhweb.diagnosis.model.vo;

import asia.lhweb.diagnosis.model.domain.Comment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 预约表
 * @TableName appointment
 */
@Data
public class AppointmentVO implements Serializable {
    /**
     * 预约表id
     */
    private Integer id;
    /**
     * 诊断结果
     */
    private String diagnosisResults;
    /**
     * 诊断描述
     */
    private String diagnosisDescription;

    /**
     * 诊断日期
     */
    private String diagnosisDate;

    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 用户名字
     */
    private String userName;

    /**
     * 咨询师id
     */
    private Integer counselorId;
    /**
     * 咨询师名字
     */
    private String counselorName;

    /**
     * 预约领域id
     */
    private Integer areaId;
    /**
     * 预约领域名称
     */
    private String areaName;

    /**
     * 预约时间例如 2024:04:28
     */
    private String appointmentDate;
    //start
    private String startTime;
    //end
    private String endTime;
    /**
     * 预约时间段
     */
    private String appointmentTime;

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
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 评论列表
     */
    List<Comment> commentList;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}