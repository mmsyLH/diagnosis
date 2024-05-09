package asia.lhweb.diagnosis.model.dto;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统后台
 * @TableName sys_admin
 */
@Data
public class SysAdminDTO  extends PageRequest implements Serializable{

    /**
     * 系统管理员账户
     */
    private String sysAccount;

    /**
     * 系统管理员密码
     */
    private String sysPassword;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 由后台管理员分配的权限
     */
    private Integer adminRoleId;

    /**
     * 0 禁用 1启用
     */
    private Integer status;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}