package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Counselor;

/**
* @author Administrator
* @description 针对表【counselor(咨询师表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Counselor
*/
public interface CounselorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Counselor record);

    int insertSelective(Counselor record);

    Counselor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Counselor record);

    int updateByPrimaryKey(Counselor record);

}
