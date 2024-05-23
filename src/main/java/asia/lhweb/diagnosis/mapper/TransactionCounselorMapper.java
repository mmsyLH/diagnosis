package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.TransactionCounselor;
import asia.lhweb.diagnosis.model.vo.TransactionCounselorVO;
import com.github.pagehelper.Page;

/**
* @author Administrator
* @description 针对表【transaction_counselor(账单流水表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.TransactionCounselor
*/
public interface TransactionCounselorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TransactionCounselor record);

    int insertSelective(TransactionCounselor record);

    TransactionCounselor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionCounselor record);

    int updateByPrimaryKey(TransactionCounselor record);

    Page<TransactionCounselorVO> selectAll(TransactionCounselor transactionCounselor);
}
