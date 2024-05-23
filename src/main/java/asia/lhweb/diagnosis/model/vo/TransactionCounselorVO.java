package asia.lhweb.diagnosis.model.vo;

import asia.lhweb.diagnosis.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单流水表
 * @TableName transaction_counselor
 */
@Data
public class TransactionCounselorVO extends PageRequest implements Serializable {
    /**
     * 账单流水Id
     */
    private Integer id;
    /**
     * 管理钱
     */
    private Double adminMoney;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 流水号
     */
    private String transactionId;

    /**
     * 咨询师Id
     */
    private Integer counselorId;

    /**
     * 流水类型 1充值 2消费
     */
    private Integer transactionType;

    /**
     * 交易金额
     */
    private Double money;

    /**
     * 交易状态 1 成功 2失败 3进行中
     */
    private Integer transactionStatus;

    /**
     * 交易方式 1支付宝 2微信
     */
    private Integer payChannel;

    /**
     * 交易附加信息
     */
    private String transactionInfo;

    /**
     * 交易时间
     */
    private Date createTime;

    /**
     * 0未删除 1已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}