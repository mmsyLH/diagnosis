package asia.lhweb.diagnosis.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表
 * @TableName sys_user
 */
@Data
public class LoginUserDTO implements Serializable {
    /**
     * 系统用户账户
     */
    @ApiModelProperty(value = "系统用户账户",example = "admin",required = true)
    private String userAccount;

    /**
     * 系统用户密码
     */
    @ApiModelProperty(value = "系统用户密码",example = "123456",required = true)
    private String userPassword;

    @ApiModelProperty(value = "验证码",example = "asax",required = true)
    private String code;


}