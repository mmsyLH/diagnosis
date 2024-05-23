package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Appointment;
import asia.lhweb.diagnosis.model.vo.AppointmentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【appointment(预约表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Appointment
*/
public interface AppointmentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Appointment record);

    int insertSelective(Appointment record);

    Appointment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Appointment record);

    int updateByPrimaryKey(Appointment record);

    Page<Appointment> selectAppointmentList(Appointment appointment);

    AppointmentVO getAppointmentInfoById(@Param("id") Integer id);

    List<Map<String, Object>> countAppointmentsByCounselorAndDateRange(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
