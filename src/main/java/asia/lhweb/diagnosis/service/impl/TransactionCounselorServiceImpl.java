package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.TransactionCounselorMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.domain.TransactionCounselor;
import asia.lhweb.diagnosis.model.vo.TransactionCounselorVO;
import asia.lhweb.diagnosis.service.TransactionCounselorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【transaction_counselor(账单流水表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class TransactionCounselorServiceImpl
implements TransactionCounselorService{

    @Resource
    private TransactionCounselorMapper transactionCounselorMapper;

    /**
     * 页面
     *
     * @param transactionCounselor 事务顾问
     * @return {@link PageResult}<{@link TransactionCounselorVO}>
     */
    @Override
    public PageResult<TransactionCounselorVO> page(TransactionCounselor transactionCounselor) {
        int pageNo = transactionCounselor.getPageNo();
        int pageSize = transactionCounselor.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<TransactionCounselorVO> transactionCounselorVOPage = transactionCounselorMapper.selectAll(transactionCounselor);
        if (transactionCounselorVOPage == null || transactionCounselorVOPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<TransactionCounselorVO> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) transactionCounselorVOPage.getTotal());
        pageResult.setItems(transactionCounselorVOPage.getResult());
        pageResult.setPageTotalCount(transactionCounselorVOPage.getPages());
        return pageResult;
    }
}
