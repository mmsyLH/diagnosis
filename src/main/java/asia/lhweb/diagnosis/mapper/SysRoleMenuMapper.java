package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysRoleMenu;

/**
* @author Administrator
* @description 针对表【sys_role_menu(系统角色菜单关联表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysRoleMenu
*/
public interface SysRoleMenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);

}
