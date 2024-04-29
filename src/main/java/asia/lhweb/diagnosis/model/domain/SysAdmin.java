package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统后台
 * @TableName sys_admin
 */
@Data
public class SysAdmin implements Serializable {
    /**
     * 管理员表Id
     */
    private Integer sysAdminId;

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

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}