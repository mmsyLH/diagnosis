package asia.lhweb.diagnosis.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统后台
 * @TableName sys_admin
 */
@Data
public class LoginAdminVO implements Serializable {
    /**
     * 管理员表Id
     */
    private Integer id;
    /**
     * 系统管理员账户
     */
    private String sysAccount;
    private Double money;

    /**
     * 顾问名字
     */
    private String counselorName;

    /**
     * 系统管理员密码
     */
    private String sysPassword;

    /**
     * 辅导员id
     */
    private Integer counselorID;

    /**
     * 管理员姓名
     */
    private String adminName;

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
    private String createTime;
    /**
     * 由后台管理员分配的权限
     */
    private Integer adminRoleId;
    /**
     * 令牌
     */
    private String token;

}