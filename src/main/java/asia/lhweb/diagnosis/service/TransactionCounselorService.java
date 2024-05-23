package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.TransactionCounselor;
import asia.lhweb.diagnosis.model.vo.TransactionCounselorVO;

/**
* @author Administrator
* @description 针对表【transaction_counselor(账单流水表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface TransactionCounselorService {

    /**
     * 页面
     *
     * @param transactionCounselor 事务顾问
     * @return {@link BaseResponse}
     */
    PageResult<TransactionCounselorVO> page(TransactionCounselor transactionCounselor);
}
