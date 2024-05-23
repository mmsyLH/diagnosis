package asia.lhweb.diagnosis.model.domain;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

/**
 * @author :罗汉
 * @date : 2024/5/17
 */
@Data
public class SysAdminUser extends PageRequest {
    /**
     * 管理员表Id
     */
    private Integer id;

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
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 账户
     */
    private String account;
    /**
     * 学校
     */
    private String school;
    /**
     * 教育lv
     */
    private String educationLv;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 背景
     */
    private String background;
}
