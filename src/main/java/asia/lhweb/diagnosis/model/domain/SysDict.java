package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典参数表
 * @TableName sys_dict
 */
@Data
public class SysDict implements Serializable {
    /**
     * 字典参数表的id
     */
    private Integer id;

    /**
     * 字典参数名
     */
    private String dictName;

    /**
     * 字典参数数据
     */
    private String dictData;

    private static final long serialVersionUID = 1L;
}