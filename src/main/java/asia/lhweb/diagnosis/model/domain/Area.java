package asia.lhweb.diagnosis.model.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 领域表
 * @TableName area
 */
@Data
public class Area implements Serializable {
    /**
     * 领域表Id
     */
    private Integer id;

    /**
     * 领域名称
     */
    private String areaName;

    private static final long serialVersionUID = 1L;
}