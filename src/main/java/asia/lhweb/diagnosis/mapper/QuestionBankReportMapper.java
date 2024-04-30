package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.QuestionBankReport;

/**
* @author Administrator
* @description 针对表【question_bank_report(题库报告表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.QuestionBankReport
*/
public interface QuestionBankReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(QuestionBankReport record);

    int insertSelective(QuestionBankReport record);

    QuestionBankReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionBankReport record);

    int updateByPrimaryKey(QuestionBankReport record);

}
