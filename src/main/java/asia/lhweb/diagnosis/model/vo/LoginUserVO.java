package asia.lhweb.diagnosis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 * @TableName sys_user
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * 系统用户账户
     */
    @ApiModelProperty(value = "系统用户账户")
    private String userAccount;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 0男 1女
     */
    @ApiModelProperty(value = "0男 1女")
    private Integer sex;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址")
    private String address;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private Double money;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    

    private static final long serialVersionUID = 1L;
}