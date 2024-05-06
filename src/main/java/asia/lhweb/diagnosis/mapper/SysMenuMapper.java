package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysMenu;
import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(系统菜单表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysMenu
*/
public interface SysMenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenuLeftVO> selectMenuTreeByAdminId(Integer adminId);
}
