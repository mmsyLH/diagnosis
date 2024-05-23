package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.TransactionUser;

/**
* @author Administrator
* @description 针对表【transaction_user(账单流水表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface TransactionUserService  {

    /**
     * 事务用户页面
     *
     * @param transactionUser 交易用户
     * @return {@link BaseResponse}<{@link PageResult}<{@link TransactionUser}>>
     */
    BaseResponse<PageResult<TransactionUser>> transactionUserPage(TransactionUser transactionUser);
}
