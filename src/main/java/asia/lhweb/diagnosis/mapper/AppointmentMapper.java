package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Appointment;
import com.github.pagehelper.Page;

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
}
