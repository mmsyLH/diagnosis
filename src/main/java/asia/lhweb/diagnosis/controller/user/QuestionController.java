package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.QuestionBank;
import asia.lhweb.diagnosis.model.domain.QuestionBankReport;
import asia.lhweb.diagnosis.model.dto.QuestionBankDTO;
import asia.lhweb.diagnosis.model.dto.QuestionBankReportDTO;
import asia.lhweb.diagnosis.model.dto.QuestionTestDTO;
import asia.lhweb.diagnosis.service.QuestionBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/13
 */
@Api("问题相关接口")
@RestController("userQuestionController")
@RequestMapping("/user/question")
public class QuestionController {
    @Resource
    private QuestionBankService questionBankService;

    /**
     * 获取问题列表
     *
     * @return {@link BaseResponse}<{@link List}<{@link QuestionBank}>>
     */
    @GetMapping ("/getQuestionList")
    @ApiOperation("获取问题列表")
    public BaseResponse<List<QuestionBank>> getQuestionList() {
        return questionBankService.getQuestionList();
    }

    /**
     * 分页获取
     *
     * @param questionBank 问题银行
     * @return {@link BaseResponse}<{@link PageResult}<{@link QuestionBank}>>
     */
    @GetMapping("/page")
    @ApiOperation("分页获取")
    public BaseResponse<PageResult<QuestionBankDTO>> page(QuestionBank questionBank) {
        return questionBankService.page(questionBank);
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping("/delete")
    @ApiOperation("按id删除")
    public BaseResponse<Boolean> deleteById(Integer id) {
        return questionBankService.deleteById(id);
    }

    /**
     * 添加
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/add")
    @ApiOperation("添加")
    public BaseResponse<Boolean> add(@RequestBody QuestionBankDTO questionBankDTO) {
        return questionBankService.add(questionBankDTO);
    }

    /**
     * 更新
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public BaseResponse<Boolean> update(@RequestBody QuestionBankDTO questionBankDTO) {
        return questionBankService.update(questionBankDTO);
    }

    /**
     * 提交测试
     *
     * @param questionTestDTO 问题测试
     * @return {@link BaseResponse}
     */
    @PostMapping("/submitTest")
    @ApiOperation("提交测试")
    public BaseResponse submitTest(@RequestBody QuestionTestDTO questionTestDTO) {

        return questionBankService.submitTest(questionTestDTO);
    }

    /**
     * 问题报告页
     * 根据问题报告分页列表
     *
     * @param questionBankReport 题库报告
     * @return {@link BaseResponse}<{@link PageResult}<{@link QuestionBankReport}>>
     */
    @GetMapping("/questionReportPage")
    @ApiOperation("获取问题报告分页列表")
    public BaseResponse<PageResult<QuestionBankReportDTO>> questionReportPage(QuestionBankReport questionBankReport) {
        return questionBankService.questionReportPage(questionBankReport);
    }
}
