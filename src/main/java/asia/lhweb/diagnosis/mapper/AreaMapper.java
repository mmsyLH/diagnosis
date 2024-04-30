package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.Area;

/**
* @author Administrator
* @description 针对表【area(领域表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.Area
*/
public interface AreaMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

}
