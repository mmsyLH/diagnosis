package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 系统角色表
 * @TableName sys_role
 */
@Data
public class SysRole implements Serializable {
    /**
     * 系统角色id
     */
    private Integer sysRoleId;

    /**
     * 系统角色表id
     */
    private Integer id;

    /**
     * 系统角色名
     */
    private String sysRoleName;

    private static final long serialVersionUID = 1L;
}