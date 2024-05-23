package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Diagnosis;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【diagnosis(诊断表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Diagnosis
*/
public interface DiagnosisMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Diagnosis record);

    int insertSelective(Diagnosis record);

    Diagnosis selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Diagnosis record);

    int updateByPrimaryKey(Diagnosis record);

    Diagnosis selectByUserIdAndAppointmentId(@Param("userId") Integer userId, @Param("appointmentId") Integer appointmentId);
}
