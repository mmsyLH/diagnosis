package asia.lhweb.diagnosis.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统后台
 * @TableName sys_admin
 */
@Data
public class LoginAdminVO implements Serializable {

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
     * 令牌
     */
    private String token;

}