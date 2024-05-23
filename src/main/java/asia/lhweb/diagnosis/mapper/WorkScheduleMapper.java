package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.WorkSchedule;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
* @author Administrator
* @description 针对表【work_schedule(排班表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.WorkSchedule
*/
public interface WorkScheduleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WorkSchedule record);

    int insertSelective(WorkSchedule record);

    WorkSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkSchedule record);

    int updateByPrimaryKey(WorkSchedule record);

    List<WorkSchedule> selectAllByCounselorId(@Param("counselorId") Integer counselorId, @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    List<WorkSchedule> selectListByCounselorId(Integer counselorId);

    WorkSchedule selectByWorkScheduleSelective(WorkSchedule workSchedule);

    Integer updateBySelective(WorkSchedule workSchedule);

    Integer updateStatus(WorkSchedule workSchedule);
}
