package asia.lhweb.diagnosis.model.domain;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志表
 * @TableName sys_log
 */
@Data
public class SysLog extends PageRequest implements Serializable {
    /**
     * 日志id
     */
    private Integer id;

    /**
     * 管理员id
     */
    private Integer adminId;

    /**
     * 操作内容
     */
    private String logContent;

    /**
     * 操作结果
     */
    private String logResult;

    /**
     * 入参字符串
     */
    private String inputParams;

    /**
     * 出参 json字符串
     */
    private String outputParams;

    /**
     * 日志发生事件，用时间戳便于排序
     */
    private Date createTime;

    /**
     * 0未删除 1删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}