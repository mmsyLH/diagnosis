package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysAdmin;

/**
* @author Administrator
* @description 针对表【sys_admin(系统后台)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysAdmin
*/
public interface SysAdminMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysAdmin record);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAdmin record);

    int updateByPrimaryKey(SysAdmin record);

}
