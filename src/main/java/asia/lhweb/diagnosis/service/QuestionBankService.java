package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.QuestionBank;
import asia.lhweb.diagnosis.model.domain.QuestionBankReport;
import asia.lhweb.diagnosis.model.dto.QuestionBankDTO;
import asia.lhweb.diagnosis.model.dto.QuestionBankReportDTO;
import asia.lhweb.diagnosis.model.dto.QuestionTestDTO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【question_bank(题库表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface QuestionBankService {

    /**
     * 获取问题列表
     *
     * @return {@link BaseResponse}<{@link List}<{@link QuestionBank}>>
     */
    BaseResponse<List<QuestionBank>> getQuestionList();

    /**
     * 页面
     *
     * @param questionBank 问题银行
     * @return {@link BaseResponse}<{@link PageResult}<{@link QuestionBank}>>
     */
    BaseResponse<PageResult<QuestionBankDTO>> page(QuestionBank questionBank);

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    BaseResponse<Boolean> deleteById(Integer id);

    /**
     * 添加
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    BaseResponse<Boolean> add(QuestionBankDTO questionBankDTO);

    /**
     * 更新
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    BaseResponse<Boolean> update(QuestionBankDTO questionBankDTO);

    /**
     * 提交测试
     *
     * @param questionTestDTO 问题测试
     * @return {@link BaseResponse}
     */
    BaseResponse submitTest(QuestionTestDTO questionTestDTO);

    BaseResponse<PageResult<QuestionBankReportDTO>> questionReportPage(QuestionBankReport questionBankReport);
}
