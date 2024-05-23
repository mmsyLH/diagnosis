package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.TransactionUserMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.TransactionUser;
import asia.lhweb.diagnosis.service.TransactionUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【transaction_user(账单流水表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class TransactionUserServiceImpl
implements TransactionUserService{
    @Resource
    private TransactionUserMapper transactionUserMapper;

    /**
     * 流水用户页面
     *
     * @param transactionUser 交易用户
     * @return {@link BaseResponse}<{@link PageResult}<{@link TransactionUser}>>
     */
    @Override
    public BaseResponse<PageResult<TransactionUser>> transactionUserPage(TransactionUser transactionUser) {
        int pageNo = transactionUser.getPageNo();
        int pageSize = transactionUser.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<TransactionUser> transactionUserPage = transactionUserMapper.selectAllIf(transactionUser);
        if (transactionUserPage == null || transactionUserPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<TransactionUser> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) transactionUserPage.getTotal());
        pageResult.setItems(transactionUserPage.getResult());
        pageResult.setPageTotalCount(transactionUserPage.getPages());
        return ResultUtils.success(pageResult);
    }
}
