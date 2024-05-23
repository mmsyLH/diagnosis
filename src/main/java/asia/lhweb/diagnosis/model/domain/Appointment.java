package asia.lhweb.diagnosis.model.domain;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约表
 * @TableName appointment
 */
@Data
@Builder
public class Appointment extends PageRequest implements Serializable {

    /**
     * 预约表id
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 任命计数
     */
    private Integer appointmentCount;
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
    private Date updateTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    public Appointment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Integer appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentReply() {
        return appointmentReply;
    }

    public void setAppointmentReply(String appointmentReply) {
        this.appointmentReply = appointmentReply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Appointment(Integer id, Integer userId, Integer appointmentCount, String userName, Integer counselorId, String counselorName, Integer areaId, String areaName, String appointmentDate, String startTime, String endTime, String appointmentTime, String appointmentDescription, String appointmentReply, Integer status, Double price, String createTime, Date updateTime, Integer isDelete) {
        this.id = id;
        this.userId = userId;
        this.appointmentCount = appointmentCount;
        this.userName = userName;
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.areaId = areaId;
        this.areaName = areaName;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentTime = appointmentTime;
        this.appointmentDescription = appointmentDescription;
        this.appointmentReply = appointmentReply;
        this.status = status;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    private static final long serialVersionUID = 1L;
}