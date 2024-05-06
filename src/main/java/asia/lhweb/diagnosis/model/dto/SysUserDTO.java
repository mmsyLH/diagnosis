package asia.lhweb.diagnosis.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 * @TableName sys_user
 */
@Data
public class SysUserDTO implements Serializable {
    /**
     * 系统用户表id
     */
    @ApiModelProperty(value = "系统用户表id",example = "1")
    private Integer id;

    /**
     * 系统用户账户
     */
    @ApiModelProperty(value = "系统用户账户",example = "admin",required = true)
    private String userAccount;

    /**
     * 系统用户密码
     */
    private String userPassword;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 0男 1女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址")
    private String address;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 0启用 1禁用
     */
    private Integer status;

    /**
     * 余额
     */
    private Double money;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;



    private static final long serialVersionUID = 1L;
}