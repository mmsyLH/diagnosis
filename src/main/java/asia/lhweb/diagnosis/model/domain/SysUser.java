package asia.lhweb.diagnosis.model.domain;

import asia.lhweb.diagnosis.annotation.DeleteMarker;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表
 * @TableName sys_user
 */
@Data
public class SysUser implements Serializable {
    /**
     * 系统用户表id
     */
    private Integer id;

    /**
     * 系统用户账户
     */
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
    private String createTime;

    /**
     * 
     */
    private String updateTime;

    /**
     * 逻辑删除 0不删除 1删除
     */
    @DeleteMarker
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}