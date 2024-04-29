package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 系统角色菜单关联表
 * @TableName sys_role_menu
 */
@Data
public class SysRoleMenu implements Serializable {
    /**
     * 系统角色菜单关联表id
     */
    private Integer id;

    /**
     * 角色Id
     */
    private Integer roleId;

    /**
     * 菜单Id
     */
    private Integer menuId;

    private static final long serialVersionUID = 1L;
}