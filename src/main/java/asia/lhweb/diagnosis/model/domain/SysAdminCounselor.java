package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 系统管理员和咨询师对应表
 * @TableName sys_admin_counselor
 */
@Data
public class SysAdminCounselor implements Serializable {
    /**
     * 系统管理员和咨询师对应表的id
     */
    private Integer id;

    /**
     * 咨询师对应Id
     */
    private Integer counselorId;

    /**
     * 领域Id
     */
    private Integer areaId;

    /**
     * 某个咨询师在某个领域的价格
     */
    private Double price;

    private static final long serialVersionUID = 1L;
}