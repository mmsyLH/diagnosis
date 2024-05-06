package asia.lhweb.diagnosis.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表
 * @TableName sys_user
 */
@Data
public class LoginAdminDTO implements Serializable {
    /**
     * 系统用户账户
     */
    @ApiModelProperty(value = "系统管理员账户",example = "admin",required = true)
    private String sysAccount;

    /**
     * 系统用户密码
     */
    @ApiModelProperty(value = "系统管理员密码",example = "123456",required = true)
    private String sysPassword;

    @ApiModelProperty(value = "验证码",example = "asax",required = true)
    private String code;


}