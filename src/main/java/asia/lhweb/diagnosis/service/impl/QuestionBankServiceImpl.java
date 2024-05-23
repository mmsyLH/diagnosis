package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.QuestionBankMapper;
import asia.lhweb.diagnosis.mapper.QuestionBankReportMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.QuestionBank;
import asia.lhweb.diagnosis.model.domain.QuestionBankReport;
import asia.lhweb.diagnosis.model.dto.QuestionBankDTO;
import asia.lhweb.diagnosis.model.dto.QuestionBankReportDTO;
import asia.lhweb.diagnosis.model.dto.QuestionTestDTO;
import asia.lhweb.diagnosis.service.QuestionBankService;
import asia.lhweb.diagnosis.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author Administrator
* @description 针对表【question_bank(题库表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class QuestionBankServiceImpl
implements QuestionBankService{
    @Resource
    private QuestionBankMapper questionBankMapper;
    @Resource
    private QuestionBankReportMapper questionBankReportMapper;

    /**
     * 获取问题列表
     *
     * @return {@link BaseResponse}<{@link List}<{@link QuestionBank}>>
     */
    @Override
    public BaseResponse<List<QuestionBank>> getQuestionList() {
       List<QuestionBank> questionBankList= questionBankMapper.getQuestionList();
       if (questionBankList.isEmpty()){
           throw new BusinessException(ErrorCode.NULL_ERROR);
       }
        return ResultUtils.success(questionBankList);
    }

    @Override
    public BaseResponse<PageResult<QuestionBankDTO>> page(QuestionBank questionBank) {
        int pageNo = questionBank.getPageNo();
        int pageSize = questionBank.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<QuestionBank> questionBankPage = questionBankMapper.selectAllIf(questionBank);
        if (questionBankPage == null || questionBankPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        QuestionBankDTO questionBankDTO;
        List<QuestionBankDTO> questionBankDTOList = new ArrayList<>();
        List<QuestionBank> result = questionBankPage.getResult();
        for (QuestionBank questionBank1 : result) {
            questionBankDTO = new QuestionBankDTO();
            questionBankDTO.setId(questionBank1.getId());
            questionBankDTO.setAreaId(questionBank1.getAreaId());
            questionBankDTO.setTitle(questionBank1.getTitle());
            questionBankDTO.setQuestionInfo(questionBank1.getQuestionInfo());
            questionBankDTO.setQuestions(questionBank1.getQuestion());
            questionBankDTO.setResults(questionBank1.getResult());
            questionBankDTO.setAreaName(questionBankMapper.getAreaName(questionBank1.getAreaId()));
            questionBankDTOList.add(questionBankDTO);
        }

        PageResult<QuestionBankDTO> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) questionBankPage.getTotal());
        pageResult.setItems(questionBankDTOList);
        pageResult.setPageTotalCount(questionBankPage.getPages());
        return ResultUtils.success(pageResult);
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Override
    public BaseResponse<Boolean> deleteById(Integer id) {
        int i = questionBankMapper.deleteByPrimaryKey(Long.valueOf(id));
        if (i <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 添加
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Override
    public BaseResponse<Boolean> add(QuestionBankDTO questionBankDTO) {
        if (questionBankDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBank questionBank = new QuestionBank();
        questionBank.setAreaId(questionBankDTO.getAreaId());
        questionBank.setQuestionInfo(questionBankDTO.getQuestionInfo());
        questionBank.setTitle(questionBankDTO.getTitle());
        questionBank.setQuestion(questionBankDTO.getQuestions());
        questionBank.setResult(questionBankDTO.getResults());

        int i = questionBankMapper.insertSelective(questionBank);
        if (i <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 更新
     *
     * @param questionBankDTO 题库
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Override
    public BaseResponse<Boolean> update(QuestionBankDTO questionBankDTO) {
        if (questionBankDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (questionBankDTO.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBank questionBank = new QuestionBank();
        questionBank.setId(questionBankDTO.getId());
        questionBank.setAreaId(questionBankDTO.getAreaId());
        questionBank.setQuestionInfo(questionBankDTO.getQuestionInfo());
        questionBank.setTitle(questionBankDTO.getTitle());
        questionBank.setQuestion(questionBankDTO.getQuestions());
        questionBank.setResult(questionBankDTO.getResults());
        int i = questionBankMapper.updateByPrimaryKeySelective(questionBank);
        if (i <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }

        return ResultUtils.success(true);
    }

    /**
     * 提交测试
     *
     * @param questionTestDTO 问题测试
     * @return {@link BaseResponse}
     */
    @Override
    public BaseResponse submitTest(QuestionTestDTO questionTestDTO) {
        if (questionTestDTO == null || questionTestDTO.getId() == null || questionTestDTO.getId() <= 0 || questionTestDTO.getQuestionsJsonStr() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer questionBankId = questionTestDTO.getId();
        QuestionBank questionBank = questionBankMapper.selectByPrimaryKey(Long.valueOf(questionBankId));
        if (questionBank == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 构建请求体的JSON字符串
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");


        // 正确创建JSONArray对象再添加元素
        JSONArray messagesArray = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content",
                "你是一个心理咨询领域的专家。现在需要你对一份在线测试的题做得分计算，以及诊断。最终给出得分和200字左右的建议。给出的结果请用json格式（得分，建议）返回。只返回json格式数据，其余任何信息都不需要返回。在线测试题库和用户提交的测试如下：" +
                        JSON.toJSONString(questionBank) + "\n" + questionTestDTO.getQuestionsJsonStr());
        messagesArray.add(userMessage);
        requestBody.put("messages", messagesArray);

        // 调用工具类发送POST请求
        String aiResponse=null;
        try {
            aiResponse = HttpClientUtil.doPost4JsonWithAuth("https://api.chatanywhere.tech/v1/chat/completions",
                    requestBody.toJSONString(), "sk-upzJYlQZ73kmHZSMjKvf54a5QSqtBnE0sUqPWBEOCkkS2qdi");
            System.err.println(aiResponse);
            // 解析aiResponse为JSONObject
            JSONObject jsonResponse = JSON.parseObject(aiResponse);

            // 确保返回结构符合预期，然后提取choices中的第一个元素
            if (jsonResponse.containsKey("choices") && jsonResponse.getJSONArray("choices").size() > 0) {
                JSONObject choice = jsonResponse.getJSONArray("choices").getJSONObject(0);
                if (choice.containsKey("message") && choice.getJSONObject("message").containsKey("content")) {
                    // 提取content中的JSON字符串并进一步解析
                    JSONObject contentJson = JSON.parseObject(choice.getJSONObject("message").getString("content"));

                    // 提取score和advice
                    Integer score = contentJson.getInteger("score");
                    String advice = contentJson.getString("advice");

                    // 将解析出的建议内容存储到QuestionBankReport对象中，而不是原始的aiResponse
                    QuestionBankReport questionBankReport = new QuestionBankReport();
                    questionBankReport.setResult(advice); // 直接存储解析出的建议文本
                    questionBankReport.setScore(score);
                    questionBankReport.setQuestionBankId(questionBankId);
                    questionBankReport.setUserId(questionTestDTO.getUserId());

                    int i = questionBankReportMapper.insertSelective(questionBankReport);
                    if (i <= 0){
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"提交测试失败");
                    }
                } else {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "解析AI响应内容失败");
                }
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI响应结构不符合预期");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "调用AI接口失败");
        }
        return ResultUtils.success(true,"提交测试成功");
    }

    /**
     * 问题报告页
     *
     * @param questionBankReport 题库报告
     * @return {@link BaseResponse}<{@link PageResult}<{@link QuestionBankReportDTO}>>
     */
    @Override
    public BaseResponse<PageResult<QuestionBankReportDTO>> questionReportPage(QuestionBankReport questionBankReport) {
        int pageNo = questionBankReport.getPageNo();
        int pageSize = questionBankReport.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<QuestionBankReport> questionBankReportPage = questionBankReportMapper.selectAllIf(questionBankReport);
        if (questionBankReportPage == null || questionBankReportPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        QuestionBankReportDTO questionBankReportDTO;
        List<QuestionBankReportDTO> questionBankReportDTOList = new ArrayList<>();
        List<QuestionBankReport> result = questionBankReportPage.getResult();
        for (QuestionBankReport questionBankReport1 : result) {
            questionBankReportDTO = new QuestionBankReportDTO();
            questionBankReportDTO.setId(questionBankReport1.getId());
            Integer questionBankId = questionBankReport1.getQuestionBankId();
            QuestionBank questionBank = questionBankMapper.selectByPrimaryKey(Long.valueOf(questionBankId));
            questionBankReportDTO.setQuestionBankId(questionBankReport1.getQuestionBankId());
            questionBankReportDTO.setAreaName(questionBankMapper.getAreaName(questionBank.getAreaId()));
            questionBankReportDTO.setTitle(questionBank.getTitle());
            questionBankReportDTO.setResult(questionBankReport1.getResult());
            questionBankReportDTO.setScore(questionBankReport1.getScore());

            questionBankReportDTO.setUserId(questionBankReport1.getUserId());
            questionBankReportDTO.setCreateTime(questionBankReport1.getCreateTime());
            questionBankReportDTOList.add(questionBankReportDTO);
        }

        PageResult<QuestionBankReportDTO> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setItems(questionBankReportDTOList);
        pageResult.setPageTotalCount(questionBankReportPage.getPages());
        pageResult.setTotalRow((int)questionBankReportPage.getTotal());
        return ResultUtils.success(pageResult);
    }
}
