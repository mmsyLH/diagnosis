package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.enums.OperatorType;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.domain.Diagnosis;
import asia.lhweb.diagnosis.service.DiagnosisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 诊断控制器
 *
 * @author 罗汉
 * @date 2024/05/22
 */
@Api("诊断相关接口")
@RestController
@RequestMapping("/admin/diagnosis")
public class DiagnosisController {
    @Resource
    private DiagnosisService diagnosisService;
    //add
    @ApiOperation("添加诊断记录")
    @Log(businessType = "添加", operatorType = OperatorType.ADMIN )
    @PostMapping("/add")
    public BaseResponse addDiagnosis(@RequestBody Diagnosis diagnosis) {
        if (diagnosis == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return diagnosisService.addDiagnosis(diagnosis);
    }
}
