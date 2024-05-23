package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.TransactionUser;
import com.github.pagehelper.Page;

/**
* @author Administrator
* @description 针对表【transaction_user(账单流水表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.TransactionUser
*/
public interface TransactionUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TransactionUser record);

    int insertSelective(TransactionUser record);

    TransactionUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionUser record);

    int updateByPrimaryKey(TransactionUser record);

    Page<TransactionUser> selectAllIf(TransactionUser transactionUser);
}
