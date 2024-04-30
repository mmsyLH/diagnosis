package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysDict;

/**
* @author Administrator
* @description 针对表【sys_dict(字典参数表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysDict
*/
public interface SysDictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

}
