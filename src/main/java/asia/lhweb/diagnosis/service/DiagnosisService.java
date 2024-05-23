package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.domain.Diagnosis;

/**
* @author Administrator
* @description 针对表【diagnosis(诊断表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface DiagnosisService {

    /**
     * 增加诊断
     *
     * @param diagnosis 诊断
     * @return {@link BaseResponse}
     */
    BaseResponse addDiagnosis(Diagnosis diagnosis);
}
