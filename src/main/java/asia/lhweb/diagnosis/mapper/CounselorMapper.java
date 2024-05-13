package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.annotation.MyEntity;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import com.github.pagehelper.Page;

import java.util.List;

/**
* @author Administrator
* @description 针对表【counselor(咨询师表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Counselor
*/
@MyEntity(Counselor.class)
public interface CounselorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Counselor record);

    int insertSelective(Counselor record);

    Counselor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Counselor record);

    int updateByPrimaryKey(Counselor record);

    Page<Counselor> selectAllIf(CounselorDTO counselorDTO);

    List<Counselor> getCounselorListByAreaId(Integer areaId);
}
