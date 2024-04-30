package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.QuestionBank;

/**
* @author Administrator
* @description 针对表【question_bank(题库表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.QuestionBank
*/
public interface QuestionBankMapper {

    int deleteByPrimaryKey(Long id);

    int insert(QuestionBank record);

    int insertSelective(QuestionBank record);

    QuestionBank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionBank record);

    int updateByPrimaryKey(QuestionBank record);

}
